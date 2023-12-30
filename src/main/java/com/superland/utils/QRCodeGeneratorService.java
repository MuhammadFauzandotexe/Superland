package com.superland.utils;
import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeGeneratorService {


    public static void main(String[] args) {
        generate("ojan","Hallo from ojan");
    }
    public static void generate(String filename,String content ) {
        int width = 500;
        int height = 500;
        byte[] qrCodeBytes = generateQRCode(content, width, height);
        if (qrCodeBytes != null) {
            String fileName = "img/qrcode/"+filename+".jpg";
            File qrCodeFile = new File(fileName);
            try (FileOutputStream fos = new FileOutputStream(qrCodeFile)) {
                fos.write(qrCodeBytes);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static byte[] generateQRCode(String content, int width, int height) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "jpg", baos);
            return baos.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
