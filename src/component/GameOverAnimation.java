package component;

import util.Constant;
import util.GameUtil;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 游戏结束界面
 * 
 */

public class GameOverAnimation {
    private final BufferedImage scoreImage;
    private final BufferedImage overImage;
    private final BufferedImage againImage;

    public GameOverAnimation() {
        overImage = GameUtil.loadBufferedImage(Constant.overImagePath);
        scoreImage = GameUtil.loadBufferedImage(Constant.scoreImagePath);
        againImage = GameUtil.loadBufferedImage(Constant.againImagePath);
    }

    private static final int scoreLocate = 5;//计分牌位置补偿参数
    private int flash = 0;//图片闪烁参数

    public void draw(Graphics g, Bird bird) {
        int x = Constant.frameWidth - overImage.getWidth() >> 1;
        int y = Constant.frameHeight / 4;
        g.drawImage(overImage, x, y, null);

        //绘制计分牌
        x = Constant.frameWidth - scoreImage.getWidth() >> 1;
        y = Constant.frameHeight / 3;
        g.drawImage(scoreImage, x, y, null);

        //绘制本局分数
        g.setColor(Color.white);
        g.setFont(Constant.scoreFont);
        x = (Constant.frameWidth - scoreImage.getWidth() / 2 >> 1) + scoreLocate;//位置补偿
        y += scoreImage.getHeight() >> 1;
        String str = Long.toString(bird.getCurrentScore());
        x -= GameUtil.getStringWidth(Constant.scoreFont, str) >> 1;
        y += GameUtil.getStringWidth(Constant.scoreFont, str);
        g.drawString(str, x, y);

        //绘制最高分数
        if (bird.getBestScore() > 0) {
            str = Long.toString(bird.getBestScore());
            x = (Constant.frameWidth + scoreImage.getWidth() / 2 >> 1) -scoreLocate;//位置补偿
            x -= GameUtil.getStringWidth(Constant.scoreFont, str) >> 1;
            g.drawString(str, x, y);
        }

        //绘制继续游戏, 图像闪烁
        final int count = 30;//闪烁周期
        if (flash++ > count) {
            GameUtil.drawImage(againImage, Constant.frameWidth - againImage.getWidth() >> 1, Constant.frameHeight / 5 * 3, g);
        }
        if (flash == count * 2) {//重置闪烁参数
            flash = 0;
        }
    }
}
