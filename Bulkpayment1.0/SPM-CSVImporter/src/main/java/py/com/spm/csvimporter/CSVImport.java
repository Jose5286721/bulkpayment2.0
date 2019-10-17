package py.com.spm.csvimporter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import py.com.spm.csvimporter.exceptions.RowParsingFailedException;
import py.com.spm.csvimporter.exceptions.WalletDontMatchException;

/**
 * 
 * @author Lino Chamorro
 * @author christian delgado
 * 
 */
public class CSVImport {
	private InputStream fileInputStream;
	private long idcompany;
	private String wallet;
	private String separator;
	private String role;
	private String currency;
	private boolean companyReposicionGuardaSaldo;
	private List<String> phoneNumbersAppears = new ArrayList<String>();
	//Map<String, Integer> phoneNumbersAppears = new HashMap<String, Integer>();
	private static final Logger logger = Logger.getLogger(CSVImport.class);
	public static String numberRegExp, prefixFixableNumber;

	public CSVImport() {
		super();
	}

	public CSVImport(InputStream fileInputStream, long idcompany, String wallet,
			String separator) {
		super();
		this.fileInputStream = fileInputStream;
		this.idcompany = idcompany;
		this.wallet = wallet;
		this.separator = separator;
	}

	public CSVImport(InputStream fileInputStream, long idcompany, String wallet,
			String separator, String currency, String role, boolean companyReposicionGuardaSaldo) {
		super();
		this.fileInputStream = fileInputStream;
		this.idcompany = idcompany;
		this.wallet = wallet;
		this.separator = separator;
		this.currency = currency;
		this.role = role;
		this.companyReposicionGuardaSaldo = companyReposicionGuardaSaldo;
	}

	public List<CSVRecord> processImport() throws RowParsingFailedException,
			WalletDontMatchException {

		logger.debug("Running import --> idCompany=[" + idcompany + "] separator=[" + separator
                    + "]");
		List<CSVRecord> recordList = null;
		phoneNumbersAppears.clear();
		BufferedReader br = null;	
		String lineStr = "";
		String[] record;
		CSVRecord csvRecord = null;
		try {
			int rowCount = 0;
			int rowParsed = 0;
			int rowError = 0;
			recordList = new ArrayList<CSVRecord>();
			br = new BufferedReader(new InputStreamReader(fileInputStream));

			while ((lineStr = br.readLine()) != null) {
				rowCount++;
				record = lineStr.split(separator);
				csvRecord = new CSVRecord();
				boolean parse;
				if (companyReposicionGuardaSaldo)
				{
					logger.info("Utilizo parseGuardaSaldo");
					parse = csvRecord.parseGuardaSaldo(record);
				}
				else
				{
					logger.info("Utilizo parse");
					parse = csvRecord.parse(record);
				}
					
				if (parse) {
					rowParsed++;
					if(csvRecord.getPhonenumber()!=null){
						String msisdn = fixValidMsisdn(csvRecord.getPhonenumber().trim());
						csvRecord.setPhonenumber(msisdn.trim());
						if (phoneNumbersAppears.contains(csvRecord
								.getPhonenumber())) {
							throw new RowParsingFailedException(rowCount,csvRecord.getPhonenumber());
						} else {
							if (csvRecord.getCurrency() == null) {
								csvRecord.setCurrency(currency);
							}
							if (csvRecord.getWallet() != null
									&& csvRecord.getWallet().compareToIgnoreCase(
									wallet) != 0) {
								logger.debug("Billetera no coincide con el definido en workflow. Numero " + csvRecord.getPhonenumber());
								throw new WalletDontMatchException(csvRecord.getWallet());
							} else {
								csvRecord.setWallet(wallet);
							}
							if (csvRecord.getRole() == null) {
								csvRecord.setRole(role);
							}
							phoneNumbersAppears.add(csvRecord.getPhonenumber());
						}
						recordList.add(csvRecord);
					}
				} else {
					// rowError++;
					throw new RowParsingFailedException(rowCount, null);

				}
			}
			logger.debug("** Import finished **");
			logger.debug("Total record readed --> " + rowCount);
			logger.debug("Record parsed --> " + rowParsed);
			logger.debug("Record unparsed (error) --> " + rowError);
			return recordList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return recordList;
		} catch (IOException e) {
			e.printStackTrace();
			return recordList;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					return recordList;

				}
			}
		}
	}

	public void printRecords(List<CSVRecord> recordList) {
		if (recordList != null) {
			for (CSVRecord csvrecord : recordList) {
				logger.debug("Imported --> " + csvrecord.toString());
			}
		}
	}
	
	private String fixValidMsisdn(String msisdn) {
		Pattern pat = Pattern.compile(numberRegExp);
		Matcher mat = pat.matcher( msisdn );
		if (mat.matches()) {
			msisdn= prefixFixableNumber + msisdn;
			logger.debug("Phone number fixed: " + msisdn);
		}
		return msisdn;

	}

	public boolean isTextFile(String filePath)  {
		boolean isText = false;
		try
		{
		    File f = new File(filePath);
		    if(!f.exists())
		        return false;
		    FileInputStream in = new FileInputStream(f);
		    int size = in.available();
		    if(size > 1000)
		        size = 1000;
		    byte[] data = new byte[size];
		    in.read(data);
		    in.close();
		    String s = new String(data, "ISO-8859-1");
		    String s2 = s.replaceAll(
		            "[a-zA-Z0-9????\\.\\*!\"?\\$\\%&/()=\\?@~'#:,;\\"+
		            "+><\\|\\[\\]\\{\\}\\^???\\\\ \\n\\r\\t_\\-`?????"+
		            "??????????????????????????????????]", "");
		    // will delete all text signs
	
		    double d = (double)(s.length() - s2.length()) / (double)(s.length());
		    // percentage of text signs in the text
		    isText = d > 0.95;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return isText;
	}

	public String identifyFileTypeUsingMimetypesFileTypeMap(final String fileName) {
		File file = new File(fileName);
	    Tika tika = new Tika();
	    String filetype = "";
	    try {
			filetype = tika.detect(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    return filetype;
	    
	} 
	
}
