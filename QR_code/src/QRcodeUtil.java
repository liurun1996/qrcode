import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class QRcodeUtil {
    /**
     *
     * @param width 二维码的宽
     *
     * @param height 二维码的高
     *
     * @param content 二维码的内容
     *
     */
    public static void createQrCode(int width, int height, String content) {

        // 1、设置二维码的一些参数
        HashMap hints = new HashMap();

        // 1.1设置字符集
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        // 1.2设置容错等级；因为有了容错，在一定范围内可以把二维码p成你喜欢的样式
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        // 1.3设置外边距;(即白色区域)
        hints.put(EncodeHintType.MARGIN, 1);

        // 2、生成二维码

        try {
            // 2.1定义BitMatrix对象
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            // 2.2、设置二维码存放路径,以及二维码的名字
            Path codePath = new File("D:\\" + UUID.randomUUID() + ".png").toPath();

            // 2.3、执行生成二维码
            MatrixToImageWriter.writeToPath(bitMatrix, "png", codePath);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void createLogoQrCode(int width, int height, String content) {

        // 1、设置二维码的一些参数
        HashMap hints = new HashMap();

        // 1.1设置字符集
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        // 1.2设置容错等级；因为有了容错，在一定范围内可以把二维码p成你喜欢的样式
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        // 1.3设置外边距;(即白色区域)
        hints.put(EncodeHintType.MARGIN, 1);

        // 2、生成二维码

        try {
            // 2.1定义BitMatrix对象
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            // 2.2、设置二维码存放路径,以及二维码的名字

            // qrFile用来存放生成的二维码图片
            File qrFile = new File("d://", UUID.randomUUID() + ".jpg");

            // logoFile用来存放带有logo的二维码图片
            File logoFile = new File("d://", "aaa.jpg");

            // 2.3、执行生成二维码
            MatrixToImageWriter.writeToPath(bitMatrix, "jpg", qrFile.toPath());

            // 2.4在二维码中添加logo
            LogoConfig logoConfig = new LogoConfig();

            addLogo(qrFile, logoFile, logoConfig);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static void addLogo(File qrPic, File logoPic, LogoConfig logoConfig) {

        if (!qrPic.isFile() || !logoPic.isFile()) {
            System.out.println("file not found!");
            return;
        }

        try {

            // 1、读取二维码图片，并构建绘图对象
            BufferedImage image = ImageIO.read(qrPic);
            Graphics2D graph = image.createGraphics();

            // 2、读取logo图片
            BufferedImage logo = ImageIO.read(logoPic);

            int widthLogo = image.getWidth() / logoConfig.getLogoPart();
            int heightLogo = image.getHeight() / logoConfig.getLogoPart();

            // 3、计算图片放置的位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;

            // 4、绘制图片
            graph.drawImage(logo, x, y, widthLogo, heightLogo, null);
            graph.drawRoundRect(x, y, widthLogo, heightLogo, 10, 10);
            graph.setStroke(new BasicStroke(logoConfig.getBorder()));
            graph.setColor(logoConfig.getBorderColor());
            graph.drawRect(x, y, widthLogo, heightLogo);

            graph.dispose();

            ImageIO.write(image, "jpeg", new File("d:/newPic.jpg"));

        } catch (Exception e) {
            System.out.println(e);

        }
    }
    public static void readQrCode(String codePath) {

        try {

            MultiFormatReader formatReader = new MultiFormatReader();

            File QrCode = new File(codePath);

            BufferedImage image = ImageIO.read(QrCode);

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

            // 设置二维码的参数
            HashMap hints = new HashMap();

            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            Result result = formatReader.decode(binaryBitmap, hints);

            // 打印解析结果
            System.out.println(result.toString());

            // 打印二维码格式
            System.out.println(result.getBarcodeFormat());

            // 二维码文本内容
            System.out.println(result.getText());

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        readQrCode("C:\\Users\\admin\\Desktop\\liurun\\政德操作文档\\1593687534(1).png");
    }

}

class LogoConfig {
    // logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public static final int DEFAULT_BORDER = 1;
    // logo大小默认为照片的1/6
    public static final int DEFAULT_LOGOPART = 6;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    public LogoConfig() {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }

    public LogoConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }
}