package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import util.Constant;
import util.GameUtil;

/**
 * 小鸟类,实现小鸟的绘制与飞行逻辑
 * 
 */

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
        counter = ScoreCounter.getInstance();//计分器
        gameOverAnimation = new GameOverAnimation();

        //读取小鸟图片资源
        birdImages = new BufferedImage[stateCount][imageCount];
        for (int j = 0; j < stateCount; j++) {
            for (int i = 0; i < imageCount; i++) {
                birdImages[j][i] = GameUtil.loadBufferedImage(Constant.birdsImagePath[j][i]);
            }
        }

        assert birdImages[0][0] != null;
        birdWidth = birdImages[0][0].getWidth();
        birdHeight = birdImages[0][0].getHeight();

        //初始化小鸟坐标
        x = Constant.frameWidth >> 2;
        y = Constant.frameHeight >> 1;

        //初始化碰撞矩形
        int rectX = x - birdWidth / 2;
        int rectY = y - birdHeight / 2;
        birdCollisionRect = new Rectangle(rectX + rectDescale, rectY + rectDescale * 2, birdWidth - rectDescale * 3, birdWidth - rectDescale * 4);//碰撞矩形的坐标与小鸟相同
    }

    //绘制方法
    public void draw(Graphics g) {
        movement();
        int stateIndex = Math.min(state, birdDeadFall);//图片资源索引
        //小鸟中心点计算器
        int halfImgWidth = birdImages[stateIndex][0].getWidth() >> 1;
        int halfImgHeight = birdImages[stateIndex][0].getHeight() >> 1;
        if (velocity > 0) {
             image = birdImages[birdUp][0];
        }
        g.drawImage(image, x - halfImgWidth, y - halfImgHeight, null);//x坐标于窗口1/4处，y坐标位窗口中心

        if (state == birdDead) {
            gameOverAnimation.draw(g, this);
        } else if (state != birdDeadFall) {
            drawScore(g);
        }
    }

    public static final int ACC_FLAP = 14; //扑翼速度
    public static final double ACC_Y = 2; //玩家向下加速
    public static final int MAX_VEL_Y = 15; //沿Y方向的最大速度，最大下降速度
    private int velocity = 0; //鸟沿Y的速度，默认值与playerFlapped相同
    private final int bottomBoundary = Constant.frameHeight - GameBackground.groundHeight - (birdHeight / 2);

    //小鸟的飞行逻辑
    private void movement() {
        //翅膀状态，实现小鸟振翅飞行
        wingState++;
        image = birdImages[Math.min(state, birdDeadFall)][wingState / 10 % imageCount];
        if (state == birdFall || state == birdDeadFall) {
            freeFall();
            if (birdCollisionRect.y > bottomBoundary) {
                die();
            }
        }
    }

    //自由落体
    private void freeFall() {
        if (velocity < MAX_VEL_Y) {
            velocity -= ACC_Y;
        }
        y = Math.min((y - velocity), bottomBoundary);
        birdCollisionRect.y = birdCollisionRect.y - velocity;
    }

    private void die() {
        counter.saveScore();
        state = birdDead;
        // TODO: -设置游戏状态
    }

    //小鸟振翅
    public void birdFlap() {
        if (keyIsReleased()) {
            if (isDead()) {
                return;
            }
            state = birdUp;
            if (birdCollisionRect.y > Constant.topBarHeight) {
                velocity = ACC_FLAP;//每次振翅将速度改为上升速度
                wingState = 0;//重置翅膀状态
            }
            keyPressed();
        }
    }

    //小鸟下降
    public void birdFall() {
        if (isDead()) {
            return;
        }
        state = birdFall;
    }

    //小鸟坠落（已死）
    public void deadBirdFall() {
        state = birdDeadFall;
        velocity = 0;//速度设置为0，防止小鸟继续上升与水管重叠
    }

    //判断小鸟是否死亡
    public boolean isDead() {
        return state == birdDeadFall || state == birdDead;
    }

    private boolean keyFlag = true;//按键状态，true为已释放，当按住键盘时不会重复调用方法

    public void keyReleased() {
        keyFlag = true;
    }

    public void keyPressed() {
        keyFlag = false;
    }

    public boolean keyIsReleased() {
        return keyFlag;
    }

    //绘制实时分数
    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(Constant.currentScoreFont);
        String str = Long.toString(counter.getCurrentScore());
        int x = Constant.frameWidth - GameUtil.getStringWidth(Constant.currentScoreFont, str) >> 1;
        g.drawString(str, x, Constant.frameHeight / 10);
    }

    //重置小鸟
    public void reset() {
        state = birdNormal;//小鸟状态
        y = Constant.frameHeight >> 1;//小鸟坐标
        velocity = 0;

        int imageHeight = birdImages[state][0].getHeight();
        birdCollisionRect.y = y - imageHeight / 2 + rectDescale * 2;//小鸟碰撞矩形坐标

        counter.reset();//重置计分器
    }

    public long getCurrentScore() {
        return counter.getCurrentScore();
    }

    public long getBestScore() {
        return counter.getBestScore();
    }

    public int getBirdX() {
        return x;
    }

    //获取小鸟的碰撞矩形
    public Rectangle getBirdColRectangle() {
        return birdCollisionRect;
    }
}
