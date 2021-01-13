package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

//工具类,游戏中用到的工具都在此类

public class GameUtil {
    private GameUtil() { }//私有化此类，防止其他类实例化此类

    /** 
    * 装载图片的方法
    *
    * @param imagePath 图片路径
    * @return 图片资源
    */

    /*
    *使用 try 和 catch 关键字可以捕获异常。try/catch 代码块放在异常可能发生的地方。
    *try/catch代码块中的代码称为保护代码
    *Catch 语句包含要捕获异常类型的声明。当保护代码块中发生一个异常时，try 后面的 catch 块就会被检查。
    *如果发生的异常包含在 catch 块中，异常会被传递到该 catch 块，这和传递一个参数到方法是一样。
    */
    public static BufferedImage loadBufferedImage(String imagePath) {
        try {
            return ImageIO.read(new FileInputStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();//输出错误流
        }
        return null;
    }

    /** 
     * 判断任意概率的概率性事件是否发生
     *
     * @param numerator   分子，不小于0的值
     * @param denominator 分母，不小于0的值
     * @return 概率性事件发生返回true，否则返回false
     */
    public static boolean isInProbability(int numerator, int denominator) throws Exception {
        //分子分母不小于0
        if (numerator <= 0 || denominator <= 0) {
            throw new Exception("传入了非法的参数");
        }
        //分子大于分母，一定发生
        if (numerator >= denominator) {
            return true;
        }

        return getRandomNumber(1, denominator+1) <= numerator;
    }

    /**
     * 返回指定区间的一个随机数
     *
     * @param min 区间最小值，包含
     * @param max 区间最大值，不包含
     * @return 该区间的随机数
     */
    public static int getRandomNumber(int min, int max) {
        return (int)(Math.random()*(max-min)+min);
    }

    /**
     * 获得指定字符串在指定字体的宽高
     */
    public static int getStringWidth(Font font, String str) {
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affineTransform, true, true);
        return (int)(font.getStringBounds(str, frc).getHeight());
    }

    /**
     *
     * @param image:图片资源
     * @param x：x坐标
     * @param y：y坐标
     * @param g：画笔
     */
    public static void drawImage(BufferedImage image, int x, int y, Graphics g) {
        g.drawImage(image, x, y, null);
    }
}
