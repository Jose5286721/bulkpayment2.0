package py.com.global.spm.GUISERVICE.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static py.com.global.spm.GUISERVICE.report.PdfBuilder.LOCAL_DATE_FORMAT;


class PaginatorBuilder extends PdfPageEventHelper {

    private Font font;
    private PdfTemplate t;
    private Image total;
    private Integer width;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT);

    public PaginatorBuilder(Integer width) {
        this.width = width;
    }
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
            font =  new Font(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED), 10);
        } catch (DocumentException | IOException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        try {
            table.setWidths(new int[]{24, 24, 2});
            table.setTotalWidth(this.width - 20);
            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            table.addCell(new Phrase("Fecha Generación: "+LocalDate.now().format(formatter), font));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Phrase(String.format("Pagina %d de", writer.getPageNumber()), font));
            PdfPCell cell = new PdfPCell(total);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            table.writeSelectedRows(0, -1, 36, 30, canvas);
            canvas.endMarkedContentSequence();
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(t, Element.ALIGN_LEFT,
            new Phrase(String.valueOf(writer.getPageNumber()), font),
            2, 4, 0);
    }
}
