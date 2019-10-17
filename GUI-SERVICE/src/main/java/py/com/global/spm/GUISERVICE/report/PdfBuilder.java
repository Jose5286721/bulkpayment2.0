package py.com.global.spm.GUISERVICE.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBAudit;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBReport;
import py.com.global.spm.GUISERVICE.dto.Report.PaymentVoucherBodyDto;
import py.com.global.spm.GUISERVICE.dto.Report.PaymentVoucherResDto;
import py.com.global.spm.GUISERVICE.enums.EstadosMts;
import py.com.global.spm.GUISERVICE.services.*;
import py.com.global.spm.GUISERVICE.utils.GeneralHelper;
import py.com.global.spm.GUISERVICE.utils.ListHelper;
import py.com.global.spm.domain.entity.Logaudit;
import py.com.global.spm.domain.entity.Logmts;


import javax.security.auth.callback.CallbackHandler;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Alfredo Barrios on 16/01/18
 * @project Based on Superate
 */
@Service
public class PdfBuilder {

    private static final Logger logger = LoggerFactory
            .getLogger(PdfBuilder.class);

    @Autowired
    GeneralHelper generalHelper;
    @Autowired
    CompanyService companyService;
    @Autowired
    SuperCompanyService superCompanyService;

    @Autowired
    LogAuditService logAuditService;

    @Autowired
    LogMtsService logMtsService;

    @Autowired
    UtilService utilService;

    @Autowired
    PaymentVoucherService paymentVoucherService;

    //Definición de fuentes.
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 26, Font.NORMAL, BaseColor.BLUE);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static final Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    public static final Integer COLUMNAS_FIJAS_REPORT = 11;


    public static final String DIRECTION_ASC = "ASC";
    public static final String ORDER_DEFAULT = "stateChr";
    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String LOCAL_DATE_FORMAT = "dd/MM/yyyy";

    private static final float left = 30;
    private static final float right = 30;
    private static final float top = 70;
    private static final float bottom = 50;
    private static final Integer widthMaxPermitido = 14300;


    public ByteArrayInputStream generateListAuditPdf(CBAudit searchCriteria,
                                                     String direction, String properties) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        logger.info("Iniciando generación de pdf Audit: {}", searchCriteria);

        try {
            if (searchCriteria == null) {
                return new ByteArrayInputStream(out.toByteArray());
            }
            logger.debug("Iniciando Filtro de Datos..");
            List<Logaudit> logaudits = logAuditService.filterLogAudit(searchCriteria, direction, properties);
            logger.debug("Filtro Completo, Inciando Generación de Archivo");
            int numColumns =6;

            Document document = new Document(PageSize.A4.rotate());
            document.setMargins(left, right, top, bottom);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new PaginatorBuilder(ReportConstants.xNormal));

            document.open();
            setBusinessProperties(document);

            PdfPTable table = new PdfPTable(new float[]{8, 8, 8, 8, 60, 8});

            table.setWidthPercentage(100);
            agregarCabecerasLogAudit(table);


            table.setHeaderRows(1);


            if (!ListHelper.hasElements(logaudits)) {
                for (int i = 0; i < numColumns; i++) table.addCell("");
                document.add(table);
                document.close();
                logger.info("Generación de pdf culminado, lista sin elementos...");
                return new ByteArrayInputStream(out.toByteArray());
            }

            //rellenamos las filas de la tabla
            for (Logaudit logaudit : logaudits) {
                table.addCell(formatoCeldaRegistro(logaudit.getUsernameChr()));
                table.addCell(formatoCeldaRegistro(logaudit.getAccesstypeChr()));
                table.addCell(formatoCeldaRegistro(logaudit.getDescriptionChr()));
                table.addCell(formatoCeldaRegistro(utilService.getDate(logaudit.getFechacreacionTim())));
                table.addCell(formatoCeldaRegistro(logaudit.getParamsChr()));
                table.addCell(formatoCeldaRegistro(logaudit.getIpChr()));


            }
            Paragraph paragraph = new Paragraph("Tigo Business-Acreditación Masiva", chapterFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            Paragraph paragraph1 = new Paragraph("Registro de Auditoría", categoryFont);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph1);
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.close();
            logger.info("Generación de pdf culminado...");

        } catch (DocumentException documentException) {
            logger.error("Se ha producido un error al generar un documento",documentException);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    public ByteArrayInputStream generateListLogMtsPDF(CBReport searchCriteria,
                                                      String direction, String properties) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        logger.info("Iniciando generación de pdf Log MTS, filtro: {}",searchCriteria);

        try {
            if (searchCriteria == null) {
                return new ByteArrayInputStream(out.toByteArray());
            }
            logger.debug("Filtrando Datos...");
            List<Logmts> logmtsList = logMtsService.filterLogMts(searchCriteria, direction, properties);
            logger.debug("Filtro Completo, Iniciando Gerneracion de Archivo");
            Integer numColumns = COLUMNAS_FIJAS_REPORT;

            Document document = new Document(PageSize.A4.rotate());
            document.setMargins(left, right, top, bottom);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new PaginatorBuilder(ReportConstants.xNormal));
            document.open();
            setBusinessProperties(document);

            PdfPTable table = new PdfPTable(numColumns);

            table.setWidthPercentage(100);
            agregarCabeceras(table);

            table.setHeaderRows(1);


            if (!ListHelper.hasElements(logmtsList)) {
                for (int i = 0; i < numColumns; i++) table.addCell("");
                document.add(table);
                document.close();
                logger.info("Generación de pdf culminado, lista sin elementos...");
                return new ByteArrayInputStream(out.toByteArray());
            }
            PdfPTable tableFirmantes = new PdfPTable(4);

            float[] columnWidths = new float[]{10f, 30f, 10f, 60f};
            agregarCabecerasFirmantes(tableFirmantes);

            tableFirmantes.getDefaultCell().setFixedHeight(45f);
            tableFirmantes.setWidths(columnWidths);
            tableFirmantes.setHeaderRows(1);

            //rellenamos las filas de la tabla
            for (Logmts logmts : logmtsList) {
                String idPaymentOrder = String.valueOf(logmts.getPaymentorderdetail().getPaymentorder().getIdpaymentorderPk());
                String empresa = logmts.getCompany().getCompanynameChr();
                String idTrx = logmts.getIdtrxmtsChr();
                table.addCell(formatoCeldaRegistro(utilService.getYearMonthFromDate(logmts.getRequestdateTim())));
                table.addCell(formatoCeldaRegistro(UtilService.getDate(logmts.getRequestdateTim())));
                table.addCell(formatoCeldaRegistro(idPaymentOrder));
                table.addCell(formatoCeldaRegistro(empresa));
                table.addCell(formatoCeldaRegistro(idTrx));
                table.addCell(formatoCeldaRegistro(EstadosMts.enumOfCode(logmts.getStateChr()).getDescription()));
                table.addCell(formatoCeldaRegistro(logmts.getPaymentorderdetail().getBeneficiary().getPhonenumberChr()));
                table.addCell(formatoCeldaRegistro(logmts.getPaymentorderdetail().getBeneficiary().getSubscriberciChr()));
                table.addCell(formatoCeldaRegistro(logmts.getPaymentorderdetail().getBeneficiary().getBeneficiarylastnameChr()));
                table.addCell(formatoCeldaRegistro(logmts.getPaymentorderdetail().getBeneficiary().getBeneficiarynameChr()));
                table.addCell(formatoCeldaRegistro(logmts.getAmountChr()));

                //Tabla Con Firmantes
                tableFirmantes.addCell(formatoCeldaRegistro(idPaymentOrder));
                tableFirmantes.addCell(formatoCeldaRegistro(empresa));
                tableFirmantes.addCell(formatoCeldaRegistro(idTrx));
                tableFirmantes.addCell(formatoCeldaRegistro(utilService.getFirmantesStr(logmts.getPaymentorderdetail().getPaymentorder().getApprovals())));


            }
            Paragraph paragraph = new Paragraph("Tigo Business-Acreditación Masiva", chapterFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            Paragraph paragraph1 = new Paragraph("Tabla 1: Firmantes por Identificador de Orden de Pago ", categoryFont);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph1);
            document.add(Chunk.NEWLINE);
            document.add(tableFirmantes);
            document.add(Chunk.NEXTPAGE);
            document.add(paragraph);
            Paragraph paragraph2 = new Paragraph("Tabla 2: Detalle de Transacciones ", categoryFont);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph2);
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.close();
            logger.info("Generación de pdf culminado...");

        } catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar un documento" + documentException);
        } finally {
            return new ByteArrayInputStream(out.toByteArray());
        }
    }


    private void agregarCabeceras(PdfPTable table) {
        table.addCell(formatoCeldaCabecera("Año-Mes"));
        table.addCell(formatoCeldaCabecera("Fecha-Hora"));
        table.addCell(formatoCeldaCabecera("Id-OrdenPago"));
        table.addCell(formatoCeldaCabecera("Empresa"));
        table.addCell(formatoCeldaCabecera("IdTrx"));
        table.addCell(formatoCeldaCabecera("Estado"));
        table.addCell(formatoCeldaCabecera("Cuenta-Destino"));
        table.addCell(formatoCeldaCabecera("Nro-Cédula"));
        table.addCell(formatoCeldaCabecera("Nombre"));
        table.addCell(formatoCeldaCabecera("Apellido"));
        table.addCell(formatoCeldaCabecera("Monto"));

    }

    private void agregarCabecerasLogAudit(PdfPTable table) {
        table.addCell(formatoCeldaCabecera("Usuario"));
        table.addCell(formatoCeldaCabecera("Tipo de Acceso"));
        table.addCell(formatoCeldaCabecera("Descripcion"));
        table.addCell(formatoCeldaCabecera("Fecha de Creacion"));
        table.addCell(formatoCeldaCabecera("Parametros"));
        table.addCell(formatoCeldaCabecera("IP"));

    }

    private void agregarCabecerasFirmantes(PdfPTable table) {
        table.addCell(formatoCeldaCabecera("Id-OrdenPago"));
        table.addCell(formatoCeldaCabecera("Empresa"));
        table.addCell(formatoCeldaCabecera("IdTrx"));
        table.addCell(formatoCeldaCabecera("Firmantes"));

    }

    private PdfPCell formatoCeldaCabecera(String phrase) {
        PdfPCell cell;
        cell = new PdfPCell(new Phrase(phrase, headFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.GRAY);
        return cell;
    }

    private PdfPCell formatoCeldaRegistro(String phrase) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase(phrase));
        cell.setMinimumHeight(45f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public ByteArrayInputStream generatePaymentVoucher(Long paymentOrderId, String ci, String phonenumberChr,Long companyId, String sinceDate, String toDate, Integer page, Integer linesPerPage,
                                       String column, String direction) {

        logger.info("Iniciando generacion de Comprobante de Pago (Payment Voucher), id paymentOrder: {}, phone: {}, " +
                "companyId: {}, NroDocumento(CI): {}", paymentOrderId,phonenumberChr,companyId,ci);
        if(linesPerPage == null) linesPerPage = Integer.MAX_VALUE;
        PaymentVoucherResDto response = paymentVoucherService.paymentVoucherBody(paymentOrderId, ci, phonenumberChr, companyId, sinceDate, toDate, page, linesPerPage, column, direction);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (response.getPaymentVoucherBody() == null || response.getPaymentVoucherBody().isEmpty()) {
            return new ByteArrayInputStream(out.toByteArray());

        }
        try{
            Document document = new Document();
            document.setPageSize(PageSize.A4);
            document.setMargins(16, 14, 42, 38);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new PaginatorBuilder(ReportConstants.xNormal));
            document.open();

            for(PaymentVoucherBodyDto body: response.getPaymentVoucherBody()){
                fillPaymentVoucherDocument(document, body);
            }
            document.close();
            logger.info("Generacion de comprobante de pago Culminada.");
        }catch (DocumentException e){
            logger.error("Se ha producido un error al generar un documento {}", this.getClass().getCanonicalName());
            logger.debug("Se ha producido un error al generar un documento", e);
        }finally {
            return new ByteArrayInputStream(out.toByteArray());

        }
    }

    private void fillPaymentVoucherDocument(Document document, PaymentVoucherBodyDto voucherBody){
        try {
            logger.debug("Creando Documento PaymentVoucher: {} ",voucherBody);
            setBusinessProperties(document);
            float[] columnWidths = new float[]{10f, 10f, 10f, 10f, 23f};
            PdfPTable headerTable = new PdfPTable(5);
            headerTable.setWidths(columnWidths);
            PdfPTable startTable = new PdfPTable(5);
            startTable.setTotalWidth(document.right()- document.left());
            PdfPTable parentTable = new PdfPTable(1);
            parentTable.getDefaultCell().setMinimumHeight(1F);
            headerTable.getDefaultCell().setMinimumHeight(1F);

            PdfPCell companyNamecell = new PdfPCell(paragraphSmall(voucherBody.getName()));
            companyNamecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            companyNamecell.setHorizontalAlignment(Element.ALIGN_CENTER);
            companyNamecell.setColspan(3);
            companyNamecell.setRowspan(3);

            headerTable.addCell(companyNamecell);

            PdfPCell garnetColorCell = new PdfPCell();
            garnetColorCell.setBackgroundColor(ReportConstants.VOUCHERGARNETCOLOR);
            garnetColorCell.setPhrase(new Phrase("Monto : ", ReportConstants.VOUCHERWHITEFONT));
            headerTable.addCell(garnetColorCell);

            PdfPCell amountValueCell =  new PdfPCell(paragraphSmall(voucherBody.getAccreditedAmount()));
            headerTable.addCell(amountValueCell);

            garnetColorCell.setPhrase(new Phrase("N Ref. Acred", ReportConstants.VOUCHERWHITEFONT));
            PdfPCell accreditedRefNumber = new PdfPCell(new Phrase(voucherBody.getAccreditedRefNumber()));
            headerTable.addCell(garnetColorCell);
            headerTable.addCell(accreditedRefNumber);
            //TODO PARAMETRIZAR formato fecha
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

            PdfPCell emittedDateValue = new PdfPCell(paragraphSmall(sdf.format(voucherBody.getEmittedDate())));
            PdfPCell emittedDate = new PdfPCell(new Phrase("Fecha Emision", ReportConstants.VOUCHERWHITEFONT));
            emittedDate.setBackgroundColor(ReportConstants.VOUCHERGARNETCOLOR);
            headerTable.addCell(emittedDate);
            headerTable.addCell(emittedDateValue);

            PdfPCell ciReceptorNumber = new PdfPCell(paragraphKeyWithProperties("Nro. CI Receptor"));
            ciReceptorNumber.setBackgroundColor(ReportConstants.VOUCHERSKINCOLOR);
            startTable.addCell(ciReceptorNumber);

            PdfPCell receptorCiValueCell = new PdfPCell(paragraphSmall(voucherBody.getReceptorCiNumber()));
            receptorCiValueCell.setColspan(2);
            startTable.addCell(receptorCiValueCell);

            PdfPCell accreditedLineCell = new PdfPCell(paragraphKeyWithProperties("Acreditado a la linea Nro: "));
            accreditedLineCell.setBackgroundColor(ReportConstants.VOUCHERSKINCOLOR);
            startTable.addCell(accreditedLineCell);
            startTable.addCell(voucherBody.getAccreditedLineChr());

            PdfPCell amountAllChr = new PdfPCell(paragraphKeyWithProperties("La suma de: "));
            amountAllChr.setBackgroundColor(ReportConstants.VOUCHERSKINCOLOR);
            startTable.addCell(amountAllChr);
            PdfPCell cell2 = new PdfPCell(paragraphSmall(voucherBody.getAmountChr()));
            cell2.setColspan(4);
            startTable.addCell(cell2);

            PdfPTable middleTable = new PdfPTable(5);
            middleTable.getDefaultCell().setMinimumHeight(1F);
            middleTable.setTotalWidth(document.right()- document.left());

            PdfPCell conceptChr = new PdfPCell(paragraphKeyWithProperties("En concepto de: "));
            conceptChr.setBackgroundColor(ReportConstants.VOUCHERSKINCOLOR);

            middleTable.addCell(conceptChr);
            middleTable.setSpacingBefore(8f);
            PdfPCell cell4 = new PdfPCell(paragraphSmall(voucherBody.getConceptPayment()));
            cell4.setColspan(4);
            middleTable.addCell(cell4);
            PdfPTable endTable = new PdfPTable(5);
            endTable.getDefaultCell().setMinimumHeight(1F);
            endTable.setTotalWidth(document.right()- document.left());
            endTable.setSpacingBefore(8f);
            PdfPCell noBorderWithColspan = new PdfPCell(new Paragraph()); //celda sin borde de 3 columnas
            noBorderWithColspan.setColspan(3);
            noBorderWithColspan.setBorderColor(new GrayColor(0.75f));
            noBorderWithColspan.setBackgroundColor(new GrayColor(0.75f));
            endTable.addCell(noBorderWithColspan);

            PdfPCell totalCell = new PdfPCell(paragraphKeyWithProperties("Total"));
            totalCell.setBackgroundColor(ReportConstants.VOUCHERSKINGLIGHTCOLOR);
            endTable.addCell(totalCell);

            endTable.addCell(voucherBody.getAccreditedAmount()); //rellenar
            endTable.addCell(noBorderWithColspan);
            PdfPCell signCell = new PdfPCell(paragraphKeyWithProperties("Firma del Receptor"));
            signCell.setBackgroundColor(ReportConstants.VOUCHERSKINGLIGHTCOLOR);
            endTable.addCell(signCell);
            endTable.addCell("");

            PdfPCell hashCell = new PdfPCell(paragraphKeyWithProperties(voucherBody.getAccreditedRefNumberHash()));
            hashCell.setColspan(5);
            endTable.addCell(hashCell);

            parentTable.addCell(headerTable);
            parentTable.addCell(startTable);
            parentTable.addCell(middleTable);
            parentTable.addCell(endTable);
            parentTable.setKeepTogether(true);
            parentTable.setSpacingBefore( 10f);
            parentTable.setSpacingAfter(12.5f);
            document.add(parentTable);
            logger.debug("Creacion de documento finalizada");
        } catch(Exception e){
            logger.error("Error al llenar documento {}",e.getClass().getCanonicalName());
            logger.debug("Error al llenar documento: ",e);
        }
    }


    private static Paragraph paragraphKeyWithProperties(String phrase){
        Paragraph paragraph = new Paragraph(phrase);
        paragraph.setFont(ReportConstants.VOUCHERLARGEBOLD);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

    private static Paragraph paragraphSmall(String phrase){
        Paragraph paragraph = new Paragraph(phrase);
        paragraph.setFont(ReportConstants.VOUCHERLARGEBOLD);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

    private void setBusinessProperties(Document document){
        document.addTitle("Acreditación-Masiva");
        document.addAuthor("Global Soluciones Inteligentes");
        document.addCreator("Global Soluciones Inteligentes");
        document.addTitle("Acreditacion Masiva");
    }
}

