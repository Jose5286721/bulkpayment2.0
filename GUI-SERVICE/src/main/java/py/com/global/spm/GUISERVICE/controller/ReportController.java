package py.com.global.spm.GUISERVICE.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.*;
import py.com.global.spm.GUISERVICE.enums.EstadosMts;
import py.com.global.spm.domain.entity.Logmts;
import py.com.global.spm.GUISERVICE.report.ExcelBuilder;
import py.com.global.spm.GUISERVICE.report.PdfBuilder;
import py.com.global.spm.GUISERVICE.services.LogMtsService;
import py.com.global.spm.GUISERVICE.services.SystemParameterService;
import py.com.global.spm.GUISERVICE.services.UtilService;
import py.com.global.spm.GUISERVICE.utils.CSVUtils;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(tags = "Reportes")
@RestController
@RequestMapping(value = "/api/reports")
public class ReportController extends BaseController<Logmts, Long> {

    private static final Logger logger = LoggerFactory
            .getLogger(ReportController.class);

    @Autowired
    PdfBuilder pdfBuilder;

    @Autowired
    ExcelBuilder excelBuilder;

    @Autowired
    LogMtsService logMtsService;

    @Autowired
    UtilService utilService;

    @Autowired
    SystemParameterService systemParameterService;

    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/smsLogMessage/download/xlsx", produces = "application/vnd.ms-excel")
    public void downloadSmsLogMessageXlsx(HttpServletResponse response, @RequestBody CBSmsLogMessage cbSmsLogMessage,
                                       @RequestParam(value = "direction", required = false) String direction,
                                       @RequestParam(value = "properties",required = false) String properties) {
        try {
            excelBuilder.generateSmsLogMessage(cbSmsLogMessage, direction, properties, response);
        } catch (Exception e) {
            logger.warn("Error al generar reporte de SmsLogMessage a excel", e);
        }

    }

    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/logMessage/download/xlsx", produces = "application/vnd.ms-excel")
    public void downloadLogMessageXlsx(HttpServletResponse response, @RequestBody CBLogMessage cbLogMessage,
                                       @RequestParam(value = "direction", required = false) String direction,
                                       @RequestParam(value = "properties",required = false) String properties) {
        try {
            excelBuilder.generateLogMessage(cbLogMessage, direction, properties, response);
        } catch (Exception e) {
            logger.warn("Error al generar reporte de LogMessage a excel", e);
        }

    }

    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/logDraftOp/download/xlsx", produces = "application/vnd.ms-excel")
    public void downloadLogDraftOpXlsx(HttpServletResponse response, @RequestBody CBLogdraftOp cbLogdraftOp,
                                       @RequestParam(value = "direction", required = false) String direction,
                                       @RequestParam(value = "properties",required = false) String properties) {
        try {
            excelBuilder.generateLogDraftOp(cbLogdraftOp, direction, properties, response);
        } catch (Exception e) {
            logger.warn("Error al generar reporte de LogDraftOP a excel", e);
        }

    }

    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/logPayment/download/xlsx", produces = "application/vnd.ms-excel")
    public void downloadLogPaymentXlsx(HttpServletResponse response, @RequestBody CBLogPayment cbLogPayment,
                                       @RequestParam(value = "direction", required = false) String direction,
                                       @RequestParam(value = "properties",required = false) String properties) {
        try {
            excelBuilder.generateLogPayment(cbLogPayment, direction, properties, response);
        } catch (Exception e) {
            logger.warn("Error al generar reporte de LogDraftOP a excel", e);
        }

    }
    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/logMts/download/pdf", produces = MediaType.APPLICATION_PDF_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadPdf(@RequestBody CBReport CBConsulta,
                                                           @ApiParam(required = true, allowableValues = "ASC, DESC",
                                                                   value = "Los valores del mismo pueden ser " + "ASC" + " para ascendente y " + "DESC" + " para descendente.")
                                                           @RequestParam("direction") String direction,
                                                           @ApiParam(required = true, allowableValues = "  ",
                                                                   value = "Seleccionar las columnas por la/s que se desa realizar el ordenamiento.")
                                                           @RequestParam("properties") String properties) {
        try {
            ByteArrayInputStream bis = pdfBuilder.generateListLogMtsPDF(CBConsulta, direction, properties);
            HttpHeaders headers = new HttpHeaders();
            String fileName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_LOG_MTS_REPORT_FILENAME);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+fileName+".pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            logger.warn("Error al generar reporte de Logmts a PDF", e);
            return null;
        }

    }

    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/logMts/download/xlsx", produces = "application/vnd.ms-excel")
    public void downloadXlsx(HttpServletResponse response, @RequestBody CBReport CBConsulta,
                             @ApiParam(required = true, allowableValues = "ASC, DESC",
                                     value = "Los valores del mismo pueden ser " + "ASC" + " para ascendente y " + "DESC" + " para descendente.")
                             @RequestParam("direction") String direction,
                             @ApiParam(required = true,
                                     value = "Seleccionar las columnas por la/s que se desa realizar el ordenamiento. ")
                             @RequestParam("properties") String properties) {
        try {
            excelBuilder.generateListLogMtsExcel(CBConsulta, direction, properties, response);
        } catch (Exception e) {
            logger.warn("Error al generar reporte de Logmts a excel", e);
        }

    }

    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/logAudit/download/pdf", produces = MediaType.APPLICATION_PDF_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadLogAuditPdf(@RequestBody CBAudit cbAudit,
                             @ApiParam(required = true, allowableValues = "ASC, DESC",
                                     value = "Los valores del mismo pueden ser " + "ASC" + " para ascendente y " + "DESC" + " para descendente.")
                             @RequestParam(value = "direction",required = false) String direction,
                             @ApiParam(required = true,
                                     value = "Seleccionar las columnas por la/s que se desa realizar el ordenamiento. ")
                             @RequestParam(value = "properties",required = false) String properties) {
        try {
            ByteArrayInputStream bis = pdfBuilder.generateListAuditPdf(cbAudit, direction, properties);

            HttpHeaders headers = new HttpHeaders();
            String fileName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_LOG_AUDIT_REPORT_FILENAME);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+fileName+".pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            logger.warn("Error al generar reporte de Logmts a PDF", e);
            return null;
        }

    }
    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/logAudit/download/xlsx", produces = "application/vnd.ms-excel")
    public void downloadLogAuditXlsx(HttpServletResponse response, @RequestBody CBAudit cbAudit,
                                                                   @ApiParam(required = true, allowableValues = "ASC, DESC",
                                                                           value = "Los valores del mismo pueden ser " + "ASC" + " para ascendente y " + "DESC" + " para descendente.")
                                                                   @RequestParam(value = "direction",required = false) String direction,
                                                                   @ApiParam(required = true,
                                                                           value = "Seleccionar las columnas por la/s que se desa realizar el ordenamiento. ")
                                                                   @RequestParam(value = "properties",required = false) String properties) {
        try {
            excelBuilder.generateListAuditExcel(cbAudit, direction, properties, response);
        } catch (Exception e) {
            logger.warn("Error al generar reporte de Logmts a excel", e);
        }

    }

    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/logSession/download/xlsx", produces = "application/vnd.ms-excel")
    public void downloadLogSessionXlsx(HttpServletResponse response, @RequestBody CBLogSession cbLogSession,
                                     @ApiParam(required = true, allowableValues = "ASC, DESC",
                                             value = "Los valores del mismo pueden ser " + "ASC" + " para ascendente y " + "DESC" + " para descendente.")
                                     @RequestParam("direction") String direction,
                                     @ApiParam(required = true,
                                             value = "Seleccionar las columnas por la/s que se desa realizar el ordenamiento. ")
                                     @RequestParam("properties") String properties) {
        try {
            excelBuilder.generateListSessionExcel(cbLogSession, direction, properties, response);
        } catch (Exception e) {
            logger.warn("Error al generar reporte de Logmts a excel", e);
        }

    }

    @PreAuthorize("hasRole('ROLE_GENERAR_REPORTE')")
    @PostMapping(value = "/logMts/download/csv", produces = MediaType.TEXT_PLAIN_VALUE)
    public void generarReporteDescargable(HttpServletResponse response, @RequestBody CBReport CBConsulta, @RequestParam("direction") String direction
            , @RequestParam("properties") String properties) {
        try {
            response.setContentType("text/plain; charset=utf-8");
            String reportName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_LOG_MTS_REPORT_FILENAME);
            response.setHeader("Content-disposition", "attachment;filename=" + reportName+".csv");
            logger.info("Iniciando Generación de Reportes Logmts por CSV");
            List<Logmts> logmtsList = logMtsService.filterLogMts(CBConsulta, direction, properties);

            List<String> columnasList = new ArrayList<>();
            columnasList.add("Año-Mes");
            columnasList.add("Fecha-Hora");
            columnasList.add("Id-OrdenPago");
            columnasList.add("Empresa");
            columnasList.add("IdTrx");
            columnasList.add("Estado");
            columnasList.add("Cuenta-Destino");
            columnasList.add("Nro-Cédula");
            columnasList.add("Nombre");
            columnasList.add("Apellido");
            columnasList.add("Monto");
            PrintWriter printWriter = response.getWriter();

            CSVUtils.writeLine(printWriter, columnasList, ';');
            for (Logmts logmts : logmtsList) {
                CSVUtils.writeLine(response.getWriter(), Arrays.asList(
                        utilService.getYearMonthFromDate(logmts.getUpdatedateTim()),  //Año-Mes
                        UtilService.getDate(logmts.getUpdatedateTim()), //Fecha-Hora
                        String.valueOf(logmts.getPaymentorderdetail().getPaymentorder().getIdpaymentorderPk()), //ID Orden Pago
                        logmts.getCompany().getCompanynameChr(),
                        String.valueOf(logmts.getIdtrxmtsChr()),
                        EstadosMts.enumOfCode(logmts.getStateChr()).getDescription(),
                        logmts.getPaymentorderdetail().getBeneficiary().getPhonenumberChr(),
                        logmts.getPaymentorderdetail().getBeneficiary().getSubscriberciChr(),
                        logmts.getPaymentorderdetail().getBeneficiary().getBeneficiarynameChr(),
                        logmts.getPaymentorderdetail().getBeneficiary().getBeneficiarylastnameChr(),
                        logmts.getAmountChr()
                ), ';');
            }
            logger.info("Finalización de Generación de Reportes por CSV");
        } catch (Exception e) {
            logger.warn("Error al generar reporte Logmts a CSV", e);
        }
    }

    @GetMapping(value = "/paymentVoucher/download/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> paymentVoucher(@RequestParam(value = "paymentOrderId", required = false) Long paymentOrderId,
                                 @RequestParam(value = "beneficiaryCI", required = false) String ci,
                                                              @RequestParam(value = "phonenumberChr", required = false) String phonenumberChr,
                                 @RequestParam(value= "sinceDate", required = false) String sinceDate,
                                 @RequestParam(value = "toDate", required = false) String toDate,
                                 @RequestParam(value = "companyId", required = false) Long companyId,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "linesPerPage", required = false) Integer linesPerPage,
                                 @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                 @RequestParam(value = "orderBy", defaultValue = "paymentorder.updatedateTim") String columnName) {
        try {
            ByteArrayInputStream bis = pdfBuilder.generatePaymentVoucher(paymentOrderId, ci, phonenumberChr ,companyId, sinceDate, toDate, page,
                    linesPerPage, columnName, direction);
            HttpHeaders headers = new HttpHeaders();
            String fileName = utilService.getFileName(SPM_GUI_Constants.SYSTEM_PARAMETERS_PAYMENT_VOUCHER_REPORT_FILENAME);
            headers.add("Content-Disposition", "inline; filename="+fileName+".pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));

        }catch (Exception e) {
            logger.warn("Error al generar comprobante de pago en formato PDF", e);
            return null;

        }

    }
}
