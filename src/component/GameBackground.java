package component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Constant;
import util.GameUtil;

/**
 * 游戏背景类，实现游戏背景的绘制
 * 
 */

public class GameBackground {
    private static final BufferedImage backgroundImage;//背景图片

    private final int speed;//背景层的速度
    private int layerX;//背景层的坐标

    public static final int groundHeight;

    static {
        backgroundImage = GameUtil.loadBufferedImage(Constant.backgroundImagePath);
        assert backgroundImage != null;
        groundHeight = backgroundImage.getHeight();
    }

    //在构造器中初始化
    public GameBackground() {
        this.speed = Constant.gameSpeed;
        this.layerX = 0;
    }

    //绘制方法
    public void draw(Graphics g, Bird bird) {
        //绘制背景颜色
        g.setColor(Constant.backgroundColor);
        g.fillRect(0, 0, Constant.frameWidth, Constant.frameHeight);

        //获取背景图片的尺寸
        int imgWidth = backgroundImage.getWidth();
        int imgHeight = backgroundImage.getHeight();

        int count = Constant.frameWidth / imgWidth + 2;//根据窗口宽度得到图片的绘制次数
        for (int i = 0; i < count; i++) {
            g.drawImage(backgroundImage, imgWidth * i - layerX, Constant.frameHeight - imgHeight, null);
        }

        if (bird.isDead()) {//小鸟死亡则不再绘制
            return;
        }
        movement();
    }

    //背景层的运动逻辑
    private void movement() {
        layerX += speed;
        if (layerX > backgroundImage.getWidth()) {
            layerX = 0;
        }
    }
}
