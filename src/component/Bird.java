package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import util.Constant;
import util.GameUtil;

//小鸟类,实现小鸟的绘制与飞行逻辑

public class Bird {
    public static final int imageCount = 8;//图片数量
    public static final int stateCount = 4;//状态数
    private final BufferedImage[][] birdImages;//小鸟的图片数组对象
    private int x;
    private int y;//小鸟的坐标
    private int wingState;//翅膀状态

    //图片资源
    private BufferedImage image;

    //小鸟的状态
    private int state;
    public static final int birdNormal = 0;
    public static final int birdUp = 1;
    public static final int birdFall = 2;
    public static final int birdDeadFall = 3;
    public static final int birdDead = 4;
    private final Rectangle birdCollisionRect;//碰撞矩形
    public static final int rectDescale = 2; // 补偿碰撞矩形宽高的参数

    private final ScoreCounter counter; // 计分器
    private final GameOverAnimation gameOverAnimation;

    public static int birdWidth;
    public static int birdHeight;

    //在构造器中对资源初始化
    public Bird() {
        counter = ScoreCounter.getInstance();
    }

    public boolean isDead() {
        return true;
    }

    public long getCurrentScore() {

    }

    public long getBestScore() {
        
    }
}
