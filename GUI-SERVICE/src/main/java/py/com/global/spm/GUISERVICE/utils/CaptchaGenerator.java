package py.com.global.spm.GUISERVICE.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

public class CaptchaGenerator {

    private CaptchaGenerator(){

    }
    private static final Random rnd = new SecureRandom();

    public static  String generateCaptchaText(int captchaLength) {
        // Characters to show in captcha A-Z & 1-9.
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        StringBuilder captchaStrBuffer = new StringBuilder();

        // build a random captchaLength chars slat
        while (captchaStrBuffer.length() < captchaLength) {
            int index = (int)(rnd.nextFloat() * saltChars.length());
            captchaStrBuffer.append(saltChars.substring(index, index + 1));
        }

        return captchaStrBuffer.toString();
    }
    public static BufferedImage generateImage(String textCode,int width,
                                              int height, int interLine,
                                              Color backColor) {

        BufferedImage bim = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bim.getGraphics();

        g.setColor(backColor == null ? getRandomColor() : backColor);
        g.fillRect(0, 0, width, height);

        if (interLine > 0) {

            int x = 0, y = 0, x1 = width, y1 = 0;
            for (int i = 0; i < interLine; i++) {
                g.setColor( getRandomColor());
                y = rnd.nextInt(height);
                y1 = rnd.nextInt(height);

                g.drawLine(x, y, x1, y1);
            }
        }

        int fsize = (int)(height*0.8);
        int fx = (int)(width/(textCode.length()*2));
        int fy = fsize;

        g.setFont(new Font("Default", Font.PLAIN, fsize));

        for (int i = 0; i < textCode.length(); i++) {
            g.setColor(getRandomColor());
            g.drawString(textCode.charAt(i) + "", fx, fy);
            fx += fsize * 0.9;
        }

        g.dispose();

        return bim;
    }

    private static Color getRandomColor() {
        return new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
    }
}
