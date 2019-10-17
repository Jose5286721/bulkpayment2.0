package py.com.global.spm.GUISERVICE.report;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.*;
import py.com.global.spm.GUISERVICE.dto.Log.LogMessageDTO;
import py.com.global.spm.GUISERVICE.dto.Log.LogdraftOpDTO;
import py.com.global.spm.GUISERVICE.dto.Log.SmsLogMessageDto;
import py.com.global.spm.GUISERVICE.enums.EstadosMts;
import py.com.global.spm.GUISERVICE.enums.EstadosPayment;
import py.com.global.spm.GUISERVICE.services.*;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;
import py.com.global.spm.domain.entity.Logaudit;
import py.com.global.spm.domain.entity.Logmts;
import py.com.global.spm.domain.entity.Logpayment;
import py.com.global.spm.domain.entity.Logsession;


import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static py.com.global.spm.GUISERVICE.report.PdfBuilder.LOCAL_DATE_FORMAT;

@Service
public class ExcelBuilder {

    private static final Logger logger = LoggerFactory
            .getLogger(ExcelBuilder.class);


    @Autowired
    private LogMtsService logMtsService;

    @Autowired
    private LogAuditService logAuditService;

    @Autowired
    private LogDraftOpService logDraftOpService;

    @Autowired
    private LogSessionService logSessionService;

    @Autowired
    private LogPaymentService logPaymentService;

    @Autowired
    private LogMessageService logMessageService;

    @Autowired
    private SmsLogMessageService smsLogMessageService;
    @Autowired
    private UtilService utilService;

    private final String secretPassword = "SisTeMaDePaG00S";

    public void generateSmsLogMessage(CBSmsLogMessage searchCriteria,
                                   String direction, String properties, HttpServletResponse response) {

        logger.info("Iniciando generación de excel: {}",searchCriteria);
        response.setContentType("application/vnd.ms-excel");
        String reportName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_SMS_LOG_MESSAGE_REPORT_FILENAME);
        response.setHeader("Content-disposition", "attachment;filename=" + reportName+".xlsx");

        try (
                Workbook workbook = new XSSFWorkbook()
        ){
            if(searchCriteria == null ){
                return ;
            }
            logger.debug("Inciando Filtro");
            List<SmsLogMessageDto> smsLogMessageDtos = smsLogMessageService.filterLogMessageReport(searchCriteria, direction, properties);
            logger.debug("Filtro de SmsLogMessage Culminado, Creando archivo");
            String[] columnas = {"Nro Origen", "Nro Destino","Mensaje","Estado","FechaHora","Usuario", "Empresa"};
            Sheet sheet = workbook.createSheet("Acreditación-Masiva-Registro de Notificaciones");
            sheet.protectSheet(secretPassword);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowNum = 1;
            for (SmsLogMessageDto logMessageDTO: smsLogMessageDtos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(logMessageDTO.getSourcenumberChr());
                row.createCell(1).setCellValue(logMessageDTO.getDestinynumberChr());
                row.createCell(2).setCellValue(logMessageDTO.getMessageChr());
                row.createCell(3).setCellValue(logMessageDTO.getStateChr());
                row.createCell(4).setCellValue(UtilService.getDate(logMessageDTO.getCreationdateTim()));
                row.createCell(5).setCellValue(logMessageDTO.getUsernameChr());
                row.createCell(6).setCellValue(logMessageDTO.getCompanynameChr());
                setLockedStyle(workbook,row);
            }
            for(int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
            workbook.close();
            logger.info("Generacion de reporte excel culminado.");

        } catch (Exception e) {
            logger.error("generateSmsLogMessageExcel: ",e);
        }

    }
    public void generateLogMessage(CBLogMessage searchCriteria,
                                   String direction, String properties, HttpServletResponse response) {

        logger.info("Iniciando generación de excel: {}", searchCriteria);
        response.setContentType("application/vnd.ms-excel");
        String reportName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_LOG_MESSAGE_REPORT_FILENAME);
        response.setHeader("Content-disposition", "attachment;filename=" + reportName+".xlsx");

        try (
                Workbook workbook = new XSSFWorkbook()
        ) {
            if(searchCriteria == null ){
                return ;
            }
            logger.debug("Iniciando Filtro de LogMessage");
            List<LogMessageDTO> logMessageDTOS = logMessageService.filterLogMessageReport(searchCriteria, direction, properties);
            logger.debug("Filtro de Logmessage Finalizado, Iniciando Creacion de Archivo");
            String[] columnas = {"Id Orden de Pago", "Estado", "Nro Origen", "Nro Destino","FechaHora","Correo destino", "Empresa"};
            Sheet sheet = workbook.createSheet("Acreditación-Masiva-Registro de Notificaciones");
            sheet.protectSheet(secretPassword);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowNum = 1;
            for (LogMessageDTO logMessageDTO: logMessageDTOS) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue((logMessageDTO.getIdpaymentorderPk()!=null)? logMessageDTO.getIdpaymentorderPk().toString():"");
                row.createCell(1).setCellValue(EstadosMts.enumOfCode(logMessageDTO.getStateChr()).getDescription());
                row.createCell(2).setCellValue(logMessageDTO.getOrignumberChr()!=null ? logMessageDTO.getOrignumberChr(): "");
                row.createCell(3).setCellValue(logMessageDTO.getDestnumberChr()!=null ? logMessageDTO.getDestnumberChr(): "");
                row.createCell(4).setCellValue(UtilService.getDate(logMessageDTO.getCreationDateTim()));
                row.createCell(5).setCellValue(logMessageDTO.getDestemail()!=null ? logMessageDTO.getDestemail(): "");
                row.createCell(6).setCellValue(logMessageDTO.getCompanynameChr());
                setLockedStyle(workbook,row);
            }
            for(int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
            workbook.close();
            logger.info("Generacion de reporte excel culminado.");

        } catch (Exception e) {
            logger.error("generateLogMessageExcel: ",e);
        }

    }
    private void setLockedStyle(Workbook workbook, Row row){
        CellStyle lockedCellStyle = workbook.createCellStyle();
        lockedCellStyle.setLocked(true);
        row.cellIterator().forEachRemaining( cell -> cell.setCellStyle(lockedCellStyle));
    }
    public void generateListAuditExcel(CBAudit searchCriteria,
                                       String direction, String properties, HttpServletResponse response) {

        logger.info("Iniciando generación de excel Auditoria: {}", searchCriteria);
        response.setContentType("application/vnd.ms-excel");
        String reportName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_LOG_AUDIT_REPORT_FILENAME);
        response.setHeader("Content-disposition", "attachment;filename=" + reportName+".xlsx");

        try (
                Workbook workbook = new XSSFWorkbook()
        ){
            if(searchCriteria == null ){
                return ;
            }
            logger.debug("Filtrando datos de Autditoria ");
            List<Logaudit> logaudits = logAuditService.filterLogAudit(searchCriteria, direction, properties);
            logger.debug("Filtro de Datos completo, iniciando generacion de archivo");
            String[] columnas = {"Usuario", "Tipo de Acceso", "Descripcion", "Fecha de creacion", "Parametros", "IP"};
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Acreditación-Masiva-Auditoria");
            sheet.protectSheet(secretPassword);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
            }
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat(LOCAL_DATE_FORMAT));

            int rowNum = 1;
            for (Logaudit logaudit: logaudits) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(logaudit.getUsernameChr());
                row.createCell(1).setCellValue(logaudit.getAccesstypeChr());
                row.createCell(2).setCellValue(logaudit.getDescriptionChr());
                row.createCell(3).setCellValue(UtilService.getDate(logaudit.getFechacreacionTim()));
                row.createCell(4).setCellValue(logaudit.getParamsChr());
                row.createCell(5).setCellValue(logaudit.getIpChr());
                setLockedStyle(workbook,row);
            }
            for(int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
            logger.info("Generacion de reporte excel culminado.");

        } catch (Exception e) {
            logger.error("generateListLogMtsExcel:  {}",e.getMessage());
        }

    }

    public void generateListSessionExcel(CBLogSession searchCriteria,
                                         String direction, String properties, HttpServletResponse response) {

        logger.info("Iniciando generación de excel Sessiones: {}",searchCriteria);
        response.setContentType("application/vnd.ms-excel");
        String reportName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_LOG_SESSION_REPORT_FILENAME);
        response.setHeader("Content-disposition", "attachment;filename=" + reportName+".xlsx");

        try (
                Workbook workbook = new XSSFWorkbook()
        ){
            if(searchCriteria == null ){
                return ;
            }
            logger.debug("Iniciando Filtro de LogSession...");
            List<Logsession> logsessions = logSessionService.filterLogSession(searchCriteria, direction, properties);
            logger.debug("Filtro de LogSession Culminado, generando Archivo");
            String[] columnas = {"Usuario", "Exitoso", "Fecha de creacion", "Mensaje", "IP"};
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Acreditación-Masiva-Auditoria");
            sheet.protectSheet(secretPassword);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
            }
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat(LOCAL_DATE_FORMAT));

            int rowNum = 1;
            for (Logsession logsession: logsessions) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(logsession.getUserChr());
                row.createCell(1).setCellValue(Boolean.TRUE.equals(logsession.isSuccessloginNum())? "SI":"NO");
                row.createCell(2).setCellValue(UtilService.getDate(logsession.getLogindateTim()));
                row.createCell(3).setCellValue(logsession.getMessageChr());
                row.createCell(4).setCellValue(logsession.getIpChr());
                setLockedStyle(workbook,row);
            }
            for(int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
            logger.info("Generacion de reporte excel culminado.");

        } catch (Exception e) {
            logger.error("generateListLogMtsExcel: {}  ",e.getMessage());
        }

    }

    public void generateLogDraftOp(CBLogdraftOp searchCriteria,
                                   String direction, String properties, HttpServletResponse response) {

        logger.info("Iniciando generación de excel LogDraftOp: {}",searchCriteria);
        response.setContentType("application/vnd.ms-excel");
        String reportName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_LOG_DRAFTOP_REPORT_FILENAME);
        response.setHeader("Content-disposition", "attachment;filename=" + reportName+".xlsx");

        try(
                Workbook workbook = new XSSFWorkbook()
        ) {
            if(searchCriteria == null ){
                return ;
            }
            logger.debug("Filtrando datos ...");
            List<LogdraftOpDTO> logdraftops = logDraftOpService.filterLogDraftOpReport(searchCriteria, direction, properties);
            logger.debug("Filtro de Datos culminado !");
            logger.debug("Cargando Generando Reporte...");
            String[] columnas = {"Id", "Id Borrador","Tipo Orden de Pago", "Monto", "Usuario" ,"Estado", "Empresa", "FechaHora"};
            Sheet sheet = workbook.createSheet("Acreditación-Masiva-Registro de Generación de OP");
            sheet.protectSheet(secretPassword);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowNum = 1;
            for (LogdraftOpDTO logdraftop: logdraftops) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(logdraftop.getIddrafopPk());
                row.createCell(1).setCellValue(logdraftop.getIdDraft());
                row.createCell(2).setCellValue(logdraftop.getPaymentOrderTypeName());
                row.createCell(3).setCellValue(logdraftop.getAmountChr());
                row.createCell(4).setCellValue(logdraftop.getUsernameChr());
                row.createCell(5).setCellValue(logdraftop.getEventDescriptionChr());
                row.createCell(6).setCellValue(logdraftop.getCompanyNameChr());
                row.createCell(7).setCellValue(UtilService.getDate(logdraftop.getCreationDateTim()));
                setLockedStyle(workbook,row);
            }
            for(int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
            logger.info("Generacion de reporte excel culminado.");

        } catch (Exception e) {
            logger.error("generateListLogMtsExcel: {}",e.getMessage());
        }

    }

    public void generateLogPayment(CBLogPayment searchCriteria,
                                   String direction, String properties, HttpServletResponse response) {

        logger.info("Iniciando generación de excel LogPayment: {}",searchCriteria);
        response.setContentType("application/vnd.ms-excel");
        String reportName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_LOG_PAYMENT_REPORT_FILENAME);
        response.setHeader("Content-disposition", "attachment;filename=" + reportName+".xlsx");

        try(
                Workbook workbook = new XSSFWorkbook()
                ) {
            if(searchCriteria == null ){
                return ;
            }
            logger.debug("Obteniendo datos...");
            List<Logpayment> logpayments = logPaymentService.filterLogPaymentReport(searchCriteria, direction, properties);
            logger.debug("Filtro Completo, Iniciando Carga de filas...");

            String[] columnas = {"Id", "Beneficiario", "Empresa", "FechaHora", "Estado","Monto a pagar","Monto pagado","Id Transacción Asociados"};
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Acreditación-Masiva-Registro de Pagos");
            sheet.protectSheet(secretPassword);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
            }
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat(LOCAL_DATE_FORMAT));
            dateCellStyle.setLocked(true);
            CellStyle lockedCellStyle = workbook.createCellStyle();
            lockedCellStyle.setLocked(true);

            int rowNum = 1;
            for (Logpayment logpayment: logpayments) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(logpayment.getIdpaymentNum());
                row.createCell(1).setCellValue(logpayment.getPhonenumberChr());
                row.createCell(2).setCellValue(logpayment.getCompany().getCompanynameChr());
                row.createCell(3).setCellValue(UtilService.getDate(logpayment.getCreationdateTim()));
                row.createCell(4).setCellValue(EstadosPayment.enumOfCode(logpayment.getStateChr()).getDescription());
                row.createCell(5).setCellValue(logpayment.getAmountChr());
                row.createCell(6).setCellValue(logpayment.getAmountpaidChr());
                String idsMTS = logpayment.getLogmtses().stream().map(Logmts::getIdtrxmtsChr).filter(Objects::nonNull).collect(Collectors.joining(","));
                row.createCell(7).setCellValue(idsMTS);
                setLockedStyle(workbook,row);
            }
            for(int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
            logger.info("Generacion de reporte excel culminado.");

        } catch (Exception e) {
            logger.error("generateListLogPaymentExcel:  {}", e.getMessage());
        }

    }
    public void generateListLogMtsExcel(CBReport searchCriteria,
                                        String direction, String properties, HttpServletResponse response) {

        logger.info("Iniciando generación de excel LogMts: {}", searchCriteria);
        response.setContentType("application/vnd.ms-excel");
        String reportName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_LOG_MTS_REPORT_FILENAME);
        response.setHeader("Content-disposition", "attachment;filename=" + reportName+".xlsx");

        try (
                Workbook workbook = new XSSFWorkbook()
        ) {
            if(searchCriteria == null ){
                return ;
            }
            logger.debug("Filtrando LogMts...");
            List<Logmts> logmtsList = logMtsService.filterLogMts(searchCriteria, direction, properties);
            logger.debug("Filtro Completo, iniciando generacion de Archivo");
            List<String> columnasList = Stream.of("Año-Mes","Fecha-Hora","Id-OrdenPago",
                    "Empresa","IdTrx","Estado","Cuenta-Destino","Nro-Cédula", "Nombre", "Apellido","Monto").collect(Collectors.toList());

            String[] columnas = new String[columnasList.size()];
            columnas = columnasList.toArray(columnas);

            Sheet sheet = workbook.createSheet("Acreditación-Masiva-Transacciones");
            sheet.protectSheet(secretPassword);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
                cell.getCellStyle().setLocked(!(i==7 || i==8));
            }
            int rowNum = 1;
            logger.debug("Cargando filas LogMts a archivo...");
            for (Logmts logmts: logmtsList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(utilService.getYearMonthFromDate(logmts.getUpdatedateTim()));
                row.createCell(1).setCellValue(UtilService.getDate(logmts.getUpdatedateTim()));
                row.createCell(2).setCellValue(logmts.getPaymentorderdetail().getPaymentorder().getIdpaymentorderPk());
                row.createCell(3).setCellValue(logmts.getCompany().getCompanynameChr());
                row.createCell(4).setCellValue(logmts.getIdtrxmtsChr());
                row.createCell(5).setCellValue(EstadosMts.enumOfCode(logmts.getStateChr()).getDescription());
                row.createCell(6).setCellValue(logmts.getPaymentorderdetail().getBeneficiary().getPhonenumberChr());
                row.createCell(7).setCellValue(logmts.getPaymentorderdetail().getBeneficiary().getSubscriberciChr());
                row.createCell(8).setCellValue(logmts.getPaymentorderdetail().getBeneficiary().getBeneficiarynameChr());
                row.createCell(9).setCellValue(logmts.getPaymentorderdetail().getBeneficiary().getBeneficiarylastnameChr());
                row.createCell(10).setCellValue(logmts.getAmountChr());
                setLockedStyle(workbook,row);
            }
            for(int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            //Segunda Hoja Firmantes

            Sheet sheet1=workbook.createSheet("Acreditación-Masiva-Firmantes");
            columnasList.clear();
            columnasList.add("Id-OrdenPago");
            columnasList.add("Empresa");
            columnasList.add("IdTrx");
            columnasList.add("Firmantes");
            columnas= new String[columnasList.size()];
            columnas = columnasList.toArray(columnas);
            Row headerRow1 = sheet1.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow1.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
                cell.getCellStyle().setLocked(true);
            }
            int rowNum2 = 1;
            logger.debug("Cargando filas de firman...");
            for (Logmts logmts: logmtsList) {
                Row row = sheet1.createRow(rowNum2++);
                row.createCell(0).setCellValue(logmts.getPaymentorderdetail().getPaymentorder().getIdpaymentorderPk());
                row.createCell(1).setCellValue(logmts.getCompany().getCompanynameChr());
                row.createCell(2).setCellValue(logmts.getIdtrxmtsChr());
                row.createCell(3).setCellValue((utilService.getFirmantesStr(logmts.getPaymentorderdetail().getPaymentorder().getApprovals())));
                setLockedStyle(workbook,row);

            }
            for(int i = 0; i < columnas.length; i++) {
                sheet1.autoSizeColumn(i);
            }

            logger.info("Generacion de reporte excel culminado.");
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            logger.error("generateListLogMtsExcel: {} ", e.getMessage());
        }

    }

}
