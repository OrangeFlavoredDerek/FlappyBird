package component;

import util.Constant;
import util.GameUtil;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 游戏启动页面
 * 
 */

public class WelcomeAnimation {
    private final BufferedImage titlImage;
    private final BufferedImage noticeImage;

    private int flashCount = 0;//图像闪烁参数

    public WelcomeAnimation() {
        titlImage = GameUtil.loadBufferedImage(Constant.titleImagePath);
        noticeImage = GameUtil.loadBufferedImage(Constant.noticeImagePath);
    }

    public void draw(Graphics g) {
        int x = (Constant.frameWidth - titlImage.getWidth()) >> 1;
        int y = Constant.frameHeight / 3;
        g.drawImage(titlImage, x, y, null);

        //使notice的图像闪烁
        final int cycle = 30;//闪烁周期
        if (flashCount++ > cycle) {
            GameUtil.drawImage(noticeImage, Constant.frameWidth - noticeImage.getWidth() >> 1, Constant.frameHeight / 5 * 3, g);
        }
        if (flashCount == cycle * 2) {
            flashCount = 0;
        }
    }
}
