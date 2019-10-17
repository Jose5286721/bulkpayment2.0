package py.com.global.spm.GUISERVICE.csvimporter;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import io.swagger.models.auth.In;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import py.com.global.spm.GUISERVICE.csvimporter.exceptions.DataException;
import py.com.global.spm.GUISERVICE.csvimporter.exceptions.InvalidAmountException;
import py.com.global.spm.GUISERVICE.csvimporter.exceptions.InvalidColumnsException;
import py.com.global.spm.GUISERVICE.csvimporter.exceptions.WalletDontMatchException;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.services.SystemParameterService;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CsvImport implements  Serializable {
    private static final Logger logger = LogManager.getLogger(CsvImport.class);
    private static final long serialVersionUID = 2942810150810127222L;

    private final SystemParameterService systemParameterService;


    public CsvImport(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
    }

    private CSVReader initialize(InputStream fileInputStream){
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();
        return new CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                .build();
    }

    public List<CSVRecord> processRecords(InputStream fileInputStream,String wallet) throws IOException, WalletDontMatchException, DataException, InvalidColumnsException, InvalidAmountException {
        HashMap<String, Integer> dataAppears = new HashMap<>();
        CSVReader csvReader = initialize(fileInputStream);
        String currency = systemParameterService.getSystemParameterValue(
                SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                SPM_GUI_Constants.SYSTEM_PARAMETERS_IMPORTER_CURRENCY,null);
        String role = systemParameterService.getSystemParameterValue(
                SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                SPM_GUI_Constants.SYSTEM_PARAMETERS_IMPORTER_ROLE,null);



        BigDecimal minValue  = new BigDecimal(systemParameterService.getSystemParameterValue(SPM_GUI_Constants.PROCESS_TRANSFER,SPM_GUI_Constants.DRAFT_MIN_AMOUNT_VALUE,SPM_GUI_Constants.DRAFT_DEF_MIN_AMOUNT_VALUE));
        BigDecimal maxValue = new BigDecimal(systemParameterService.getSystemParameterValue(SPM_GUI_Constants.PROCESS_TRANSFER,SPM_GUI_Constants.DRAFT_MAX_AMOUNT_VALUE,SPM_GUI_Constants.DRAFT_DEF_MAX_AMOUNT_VALUE));

        String[] line;
        List<CSVRecord> recordList = new ArrayList<>();
        CSVRecord csvRecord;
        int countLines = 0;
        while ((line = csvReader.readNext()) != null ) {
            countLines++;
            csvRecord = new CSVRecord();
            csvRecord.parse(line,countLines);
            csvRecord.setPhonenumber(fixValidMsisdn(csvRecord.getPhonenumber()));
            validRecord(csvRecord, countLines,minValue,maxValue,wallet,dataAppears);
            if (csvRecord.getCurrency() == null) {
                csvRecord.setCurrency(currency);
            }
            if (csvRecord.getRole() == null) {
                csvRecord.setRole(role);
            }
            recordList.add(csvRecord);
        }
        logger.info("Validacion del archivo csv exitosa");
        return recordList;
    }

    private void validRecord(CSVRecord csvRecord, int currentLine,BigDecimal minValue, BigDecimal maxValue,String wallet, HashMap<String,Integer> dataAppears)   throws DataException, WalletDontMatchException{

        if(csvRecord.getAmount().compareTo(minValue)<0)
            throw new DataException(ErrorsDTO.CODE.AMOUNTERRORCSV.getValue(), Arrays.asList(String.valueOf(currentLine),"monto minimo",minValue.toString()));

        if(csvRecord.getAmount().compareTo( maxValue)>0)
            throw new DataException(ErrorsDTO.CODE.AMOUNTERRORCSV.getValue(), Arrays.asList(String.valueOf(currentLine),"monto maximo",maxValue.toString()));

        String phoneNumber = csvRecord.getPhonenumber();
        if(phoneNumber==null || !isValidNumber(phoneNumber)){
            throw new DataException(ErrorsDTO.CODE.PHONUMBERPARSECSV.getValue(), Arrays.asList(String.valueOf(currentLine),csvRecord.getPhonenumber()));

        }
        if((csvRecord.getEmail()!=null && !isValidEmail(csvRecord.getEmail()))){
            throw new DataException(ErrorsDTO.CODE.EMAILPARSECSV.getValue(), Arrays.asList(String.valueOf(currentLine),csvRecord.getEmail()));
        }
        if (dataAppears.containsKey(csvRecord
                .getPhonenumber())) {
            throw new DataException(ErrorsDTO.CODE.REPEATEDPHONENUMBERCSV.getValue(), Arrays.asList(String.valueOf(currentLine),csvRecord.getPhonenumber()));
        }

        if(csvRecord.getPhonenumber()==null || csvRecord.getCi().isEmpty()){
            throw new DataException(ErrorsDTO.CODE.CIPARSECSV.getValue(), Arrays.asList(String.valueOf(currentLine),csvRecord.getCi()));

        }

        if (dataAppears.containsKey(csvRecord
                .getCi())) {
            throw new DataException(ErrorsDTO.CODE.REPEATEDCICSV.getValue(), Arrays.asList(String.valueOf(currentLine),csvRecord.getCi()));
        }
        if (csvRecord.getWallet() != null
                && csvRecord.getWallet().compareToIgnoreCase(wallet) != 0) {
            logger.debug("Billetera no coincide con el definido en workflow. Numero {}" ,csvRecord.getPhonenumber());
            throw new WalletDontMatchException(csvRecord.getWallet());
        }
        dataAppears.put(csvRecord.getPhonenumber(),4);
        dataAppears.put(csvRecord.getCi(),4);

    }


    private String fixValidMsisdn(String msisdn) {
        String prefixFixableNumber = systemParameterService.getSystemParameterValue(
                SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                SPM_GUI_Constants.PREFIX_NUMBER_VALID_PAR,
                SPM_GUI_Constants.DEFAULT_PREFIX_NUMBER_VALIDATION);

        String numberRegExp  = systemParameterService.getSystemParameterValue(
                SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                SPM_GUI_Constants.REG_EXPR_FIX_NUMBER_PAR,
                SPM_GUI_Constants.DEFAULT_FIXABLE_NUMBER_REG_EXPR);
        Pattern pat = Pattern.compile(numberRegExp);
        Matcher mat = pat.matcher( msisdn );
        if (mat.matches()) {
            msisdn= prefixFixableNumber + msisdn;
            logger.debug("Phone number fixed: {}", msisdn);
        }
        return msisdn;

    }

    private boolean isValidNumber(String msisdn ) {
        String validNumberRegExp = systemParameterService.getSystemParameterValue(
                SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                SPM_GUI_Constants.ACCOUNT_REG_EXPR,
                SPM_GUI_Constants.ACCOUNT_DEF_REG_EXPR);
        Pattern pat = Pattern.compile(validNumberRegExp);
        Matcher mat = pat.matcher(msisdn);
        return mat.matches();
    }

    private boolean isValidEmail(String email) {
        Pattern pat = Pattern.compile(SPM_GUI_Constants.REG_EXPR_EMAIL);
        Matcher mat = pat.matcher(email );
        return mat.matches();
    }
}
