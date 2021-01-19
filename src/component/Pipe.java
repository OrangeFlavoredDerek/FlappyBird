package component;

import java.awt.*;
import java.awt.image.BufferedImage;
import util.Constant;
import util.GameUtil;

/**
 * 水管类,实现水管的绘制与运动逻辑
 * 
 */

public class Pipe {
    static BufferedImage[] images;//水管图片，static保证图片只加载一次

    static {//静态代码块，类加载的时候，初始化图片
        final int pipeIamgeCount = 3;
        images = new BufferedImage[pipeIamgeCount];
        for (int i = 0; i < pipeIamgeCount; i++) {
            images[i] = GameUtil.loadBufferedImage(Constant.pipeImagePath[i]);
        }
    }

    //所有水管的宽高
    public static final int pipeWidth = images[0].getWidth();
    public static final int pipeHeight = images[0].getHeight();
    public static final int pipeHeadWidth = images[1].getWidth();
    public static final int pipeHeadHeight = images[1].getHeight();

    int x, y;//水管坐标，相对于元素层
    int width,height;//水管的宽高

    boolean visible;//水管可见状态，true为可见，false表示可归还至对象池
    
    //水管的类型
    int type;
    public static final int typeTopNormal = 0;
    public static final int typeTopHead = 1;
    public static final int typeBottomNormal = 2;
    public static final int typeBottomHard = 3;
    public static final int typeHoverNormal = 4;
    public static final int typeHoverHard = 5;

    //水管的速度
    int speed;

    Rectangle pipeRectangle;//水管的碰撞矩形

    //构造器
    public Pipe() {
        this.speed = Constant.gameSpeed;
        this.width = pipeWidth;

        pipeRectangle = new Rectangle();
        pipeRectangle.width = pipeWidth;
    }

    /**
     * 设置水管参数
     *
     * @param x:x坐标
     * @param y：y坐标
     * @param height：水管高度
     * @param type：水管类型
     * @param visible：水管可见性
     */
    public void setAttribute(int x, int y, int height, int type, boolean visible) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.type = type;
        this.visible = visible;
        setRectangle(this.x, this.y, this.height);
    }

    /**
     * 设置碰撞矩形参数
     * 
     */
    public void setRectangle(int x, int y, int height) {
        pipeRectangle.x = x;
        pipeRectangle.y = y;
        pipeRectangle.height = height;
    }

    // 判断水管是否位于窗口
    public boolean isVisible() {
        return visible;
    }

    //绘制方法
    public void draw(Graphics g, Bird bird) {
        switch (type) {
            case typeTopNormal:
                 drawTopNormal(g);
                 break;
            case typeBottomNormal:
                 drawBottomNormal(g);
                 break;
            case typeHoverNormal:
                 drawHoverNormal(g);
                 break;
        }

        //鸟死后水管停止移动
        if (bird.isDead()) {
            return;
        }
        movement();
    }

    //绘制从上往下的普通水管
    private void drawTopNormal(Graphics g) {
        //拼接个数
        int count = (height - pipeHeadHeight) / pipeHeight + 1;//取整+1
        //绘制水管的主体
        for (int i = 0; i < count; i++) {
            g.drawImage(images[0], x, y + i * pipeHeight, null);
        }
        //绘制水管的顶部
        g.drawImage(images[1], x - ((pipeHeadWidth - width) >> 1), height - Constant.topPipeLengthening - pipeHeadHeight, null);
    }

    //绘制从下往上的普通水管
    private void drawBottomNormal(Graphics g) {
        //拼接个数
        int count = (height - pipeHeadHeight - Constant.goundHeight) / pipeHeight + 1;
        //绘制水管的主体
        for (int i = 0; i < count; i++) {
            g.drawImage(images[0], x, Constant.frameHeight - pipeHeight - Constant.goundHeight - i * pipeHeight, null);
        }
        //绘制水管的顶部
        g.drawImage(images[2], x - ((pipeHeadWidth - width) >> 1), Constant.frameHeight - height, null);
    }

    //绘制悬浮的普通水管
    private void drawHoverNormal(Graphics g) {
        //拼接个数
        int count = (height - 2 * pipeHeadHeight) / pipeHeight + 1;
        //绘制水管的上顶部
        g.drawImage(images[2], x -((pipeHeadWidth - width) >> 1), y, null);
        //绘制水管的主体
        for (int i = 0; i < count; i++) {
            g.drawImage(images[0], x, y + i * pipeHeight + pipeHeadHeight, null);
        }
        //绘制水管的下底部
        int y = this.y + height - pipeHeadHeight;
        g.drawImage(images[1], x - ((pipeHeadWidth - width) >> 1), y, null);
    }

    //普通水管的运动逻辑
    private void movement() {
        x -= speed;
        pipeRectangle.x -= speed;
        if (x < -1 * pipeHeadWidth) {//水管完全离开了窗口
            visible = false;
        }
    }

    /**
     * 判断当前水管是否完全出现在窗口中
     *
     * @return 若完全出现则返回true，否则返回false
     */
    public boolean isInFrame() {
        return x + width <Constant.frameWidth;
    }

    // 获取水管的x坐标
    public int getX() {
        return x;
    }

    //获取水管的碰撞矩形
    public Rectangle getPiRectangle() {
        return pipeRectangle;
    }
}
