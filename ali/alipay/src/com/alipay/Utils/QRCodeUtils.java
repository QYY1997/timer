package com.alipay.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
 
import javax.imageio.ImageIO;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
 
/**
 * ClassName: QRCodeUtils
 * Function: <p> QRcode utils  </p>
 * date: 2018/1/13 13:21
 *
 * @author wangdongdong
 * @version 1.0.0
 * @since JDK 1.8
 */
public class QRCodeUtils {
 
    private static Logger logger = LoggerFactory.getLogger(QRCodeUtils.class);
 
    public static void generate(String content, String pathname) {
 
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
 
            Map hints = Maps.newHashMap();
 
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
 
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
 
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", new File(pathname));
            logger.info("二维码生成成功");
        } catch (Exception e) {
            logger.error("二维码生成出错", e.getMessage());
        }
    }
    
 
    public static void generate(String content, OutputStream stream) {
 
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
 
            Map hints = Maps.newHashMap();
 
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
 
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
 
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", stream);
        } catch (Exception e) {
            logger.error("二维码生成出错", e.getMessage());
        }
    }
}
 
class MatrixToImageWriter {
 
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
 
    private MatrixToImageWriter() {
    }
 
 
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
 
 
    static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }
 
 
    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
}