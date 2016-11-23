package com.cnh.frame.wraps;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.IOException;
import java.net.URL;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/1
 */
public class ImageWrap {


    /**
     * 读取BufferedImage
     *
     * @param imgUrl
     * @return
     * @throws Exception
     */
    public static BufferedImage readImage(String imgUrl) throws Exception {
        return ImageIO.read(new URL(imgUrl));
    }

    /**
     * 将图片缩放到固定尺寸
     *
     * @param bufferedImage
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static BufferedImage zoom(BufferedImage bufferedImage, int width, int height) throws IOException {
        int type = bufferedImage.getColorModel().getTransparency(); // 得到透明度。
        BufferedImage img;      // 空图片。
        Graphics2D graphics2d;  // 空画笔。
        (graphics2d = (img = new BufferedImage(width, height, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.drawImage(bufferedImage, 0, 0, width, height, 0, 0, bufferedImage
                .getWidth(), bufferedImage.getHeight(), null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 将图片缩放到固定比例
     *
     * @param bufferedImage
     * @param zoomPercent
     * @return
     * @throws IOException
     */
    public static BufferedImage zoom(BufferedImage bufferedImage, double zoomPercent) throws IOException {
        Double width = bufferedImage.getWidth() * zoomPercent;
        Double height = bufferedImage.getHeight() * zoomPercent;
        return zoom(bufferedImage, width.intValue(), height.intValue());
    }


    /**
     * 旋转图片，正数顺时针，负数逆时针
     * @param bufferedImage
     * @param degree
     * @param bgcolor
     * @return
     * @throws IOException
     */
    public static BufferedImage rotate(BufferedImage bufferedImage, double degree, Color bgcolor) throws IOException {
        int iw = bufferedImage.getWidth();      //原始图象的宽度
        int ih = bufferedImage.getHeight();     //原始图象的高度
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        degree = degree % 360;
        if (degree < 0)
            degree = 360 + degree;              //将角度转换到0-360度之间
        double ang = Math.toRadians(degree);    //将角度转为弧度

        /**
         *确定旋转后的图象的高度和宽度
         */

        if (degree == 180 || degree == 0 || degree == 360) {
            w = iw;
            h = ih;
        } else if (degree == 90 || degree == 270) {
            w = ih;
            h = iw;
        } else {
            //int d = iw + ih;
            //w = (int) (d * Math.abs(Math.cos(ang)));
            //h = (int) (d * Math.abs(Math.sin(ang)));
            double cosVal = Math.abs(Math.cos(ang));
            double sinVal = Math.abs(Math.sin(ang));
            w = (int) (sinVal * ih) + (int) (cosVal * iw);
            h = (int) (sinVal * iw) + (int) (cosVal * ih);
        }

        x = (w / 2) - (iw / 2);         //确定原点坐标
        y = (h / 2) - (ih / 2);
        BufferedImage rotatedImage = new BufferedImage(w, h, bufferedImage.getType());
        Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
        if (bgcolor == null) {
            rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        } else {
            gs.setColor(bgcolor);
            gs.fillRect(0, 0, w, h);    //以给定颜色绘制旋转后图片的背景
        }

        AffineTransform at = new AffineTransform();
        at.rotate(ang, w / 2, h / 2);   //旋转图象
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        op.filter(bufferedImage, rotatedImage);

        return rotatedImage;
    }

    /**
     * 添加文字水印
     *
     * @param bufferedImage
     * @param text          水印文本
     * @param fontName      字体名称
     * @param fontStyle     字体样式
     * @param fontSize      字体大小
     * @param color         字体颜色
     * @param alpha         [0.0, 1.0]
     * @param transX        水平修正值
     * @param transY        垂直修正值
     * @return
     * @throws IOException
     */
    public static BufferedImage waterText(BufferedImage bufferedImage, String text, String fontName, int fontStyle, int fontSize, Color color, float alpha, int transX, int transY) throws IOException {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage newBufferedImage = new BufferedImage(width, height, bufferedImage.getColorModel().getTransferType());
        Graphics2D graphics2D = newBufferedImage.createGraphics();
        graphics2D.drawImage(bufferedImage, 0, 0, width, height, null);
        graphics2D.setColor(color);
        graphics2D.setFont(new Font(fontName, fontStyle, fontSize));
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

        // 指定的坐标位置绘制水印文字
        graphics2D.drawString(text, (width - StringWrap.getLength(text) * fontSize) / 2 + transX, (height - fontSize) / 2 + transY);
        graphics2D.dispose();
        return newBufferedImage;
    }

    /**
     * 图片裁剪
     *
     * @param bufferedImage
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static BufferedImage cut(BufferedImage bufferedImage, int x, int y, int width, int height) throws IOException {
        ImageFilter filter = new CropImageFilter(x, y, width, height);
        Image cropped = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(bufferedImage.getSource(), filter));
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.drawImage(cropped, 0, 0, null);
        g.dispose();
        return image;
    }


    /**
     * 图片水平翻转
     *
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static BufferedImage flipHorizontal(BufferedImage bufferedImage) throws IOException {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, bufferedImage
                .getColorModel().getTransparency())).createGraphics())
                .drawImage(bufferedImage, 0, 0, w, h, w, 0, 0, h, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 图片垂直翻转
     *
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static BufferedImage flipVertical(BufferedImage bufferedImage) throws IOException {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, bufferedImage
                .getColorModel().getTransparency())).createGraphics())
                .drawImage(bufferedImage, 0, 0, w, h, 0, h, w, 0, null);
        graphics2d.dispose();
        return img;
    }

}
