package component;

import java.awt.Graphics;
import util.Constant;

/**
 * 移动水管类，继承Pipe类
 * 
 */

public class MovingPipe extends Pipe {
    private int dealtY;//移动水管坐标
    public static final int maxDelta = 50;//最大移动距离
    private int direction;
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;

    //构造器
    public MovingPipe() {
        super();
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
    public void setAttribute(int x, int y, int hieght, int type, boolean visible) {
        super.setAttribute(x, y, height, type, visible);
        dealtY = 0;
        direction = DIR_DOWN;
        if (type == typeTopHead) {
            direction = DIR_UP;
        }
    }

    //绘制方法
    public void draw(Graphics g, Bird bird) {
        switch (type) {
            case typeHoverHard:
                drawHoverHard(g);
                break;
            case typeTopHead:
                drawTopHard(g);
                break;
            case typeBottomHard:
                drawBottomHard(g);
                break;    
        }
        //鸟死后水管停止移动
        if (bird.isDead()) {
            return;
        }
        movement();
    }

    //绘制移动的悬浮水管
    private void drawHoverHard(Graphics g) {
        //拼接个数
        int count = (height - 2 * pipeHeadHeight) / pipeHeight + 1;
        //绘制水管上顶部
        g.drawImage(images[2], x - ((pipeHeadWidth - width) >> 1), y + dealtY, null);
        //绘制水管主体
        for (int i = 0; i < count; i++) {
            g.drawImage(images[0], x, y + dealtY + i * pipeHeight + pipeHeadHeight, null);
        }
        //绘制水管下底部
        int y = this.y + height - pipeHeadHeight;
        g.drawImage(images[1], x - ((pipeHeadWidth - width) >> 1), y + dealtY, null);
    }

    //绘制从上往下的移动水管
    private void drawTopHard(Graphics g) {
        //拼接个数
        int count = (height - pipeHeadHeight) / pipeHeight + 1;
        //绘制水管主体
        for (int i = 0; i < count; i++) {
            g.drawImage(images[0], x, y + dealtY + i * pipeHeight, null);
        }
        //绘制水管的顶部
        g.drawImage(images[1], x - ((pipeHeadWidth - width) >> 1), height - Constant.topPipeLengthening - pipeHeadHeight + dealtY, null);
    }

    //绘制从下往上的移动水管
    private void drawBottomHard(Graphics g) {
        //拼接个数
        int count = (height - pipeHeadHeight) / pipeHeight + 1;
        //绘制水管主体
        for (int i = 0; i < count; i++) {
            g.drawImage(images[0], x, Constant.frameHeight - pipeHeight - i * pipeHeight + dealtY, null);
        }
        //绘制水管的顶部
        g.drawImage(images[2], x - ((pipeHeadWidth - width) >> 1), Constant.frameHeight - height + dealtY, null);
    }

    /**
     * 可动水管的运动逻辑
     * 
     */
    private void movement() {
        //x坐标的运动逻辑与普通水管相同
        x -= speed;
        pipeRectangle.x -= speed;
        if (x < -1 * pipeHeadWidth) {//水管完全离开了窗口
            visible = false;
        }

        //水管上下移动逻辑
        if (direction == DIR_DOWN) {
            dealtY++;
            if (dealtY > maxDelta) {
                direction = DIR_UP;
            }
        } else {
            dealtY--;
            if (dealtY <= 0) {
                direction = DIR_DOWN;
            }
        }
        pipeRectangle.y = this.y + dealtY;
    }
}
