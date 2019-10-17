package py.com.global.spm.GUISERVICE.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

//TODO TRAER FUENTES, COLORES,ETC
public class ReportConstants {

    //PaymentVoucher Final Variables
    //FONTS
    static final Font VOUCHERLARGEBOLD = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);
    static final Font VOUCHERSMALLITALIC = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
    static final Font VOUCHERWHITEFONT = new Font(Font.FontFamily.COURIER, 10, Font.ITALIC, BaseColor.WHITE);

    //COLORS
    static final BaseColor VOUCHERGARNETCOLOR = new BaseColor(133, 39, 78);
    static final BaseColor VOUCHERSKINCOLOR = new BaseColor(241,194,125);
    static final BaseColor VOUCHERSKINGLIGHTCOLOR = new BaseColor(255,219,172);

    static final Integer xNormal = 1693;
}
