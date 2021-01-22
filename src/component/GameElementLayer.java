package component;

import java.awt.Graphics;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.List;
import util.Constant;
import util.GameUtil;

/**
 * 游戏元素层，目前管理水管的生成逻辑并绘制容器中的水管
 * 
 */

public class GameElementLayer {
    private final List<Pipe> pipes; //水管的容器

    //构造器
    public GameElementLayer() {
        pipes = new ArrayList<>();
    }

    //绘制方法
    public void draw(Graphics g, Bird bird) {
        //遍历水管容器，如果可见则绘制，不可见则归还
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            if (pipe.isVisible()) {
                pipe.draw(g, bird);
            } else {
                Pipe remove = pipes.remove(i);
                PipePool.giveBack(remove);
                i--;
            }
        }
        //碰撞检测
        isCollideBird(bird);
        
    }

    /**
     * 添加水管的逻辑： 当容器中添加的最后一个元素完全显示到屏幕后，添加下一对； 水管成对地相对地出现，空隙高度为窗口高度的1/6；
     * 每对水管的间隔距离为屏幕高度的1/4； 水管的高度的取值范围为窗口的[1/8~5/8]
     * 
     */
    public static final int verticalInterval = Constant.frameHeight / 5;
    public static final int horizontalInterval = Constant.frameHeight >> 2;
    public static final int minHeight = Constant.frameHeight >> 3;
    public static final int maxHeight = ((Constant.frameHeight) >> 3) * 5;

    private void pipeBornLogic(Bird bird) {
        if (bird.isDead()) {
            //鸟死后不再添加水管
            return;
        }
        if (pipes.size() == 0) {
            //若容器为空，则添加一对水管
            int topHeight = GameUtil.getRandomNumber(minHeight, maxHeight + 1);//随机生成水管高度

            Pipe top = 
        }
    }


    /**
     * 判断元素和小鸟是否发生碰撞
     *
     * @param bird 传入小鸟对象
     */
    public void isCollideBird(Bird bird) {
        //若鸟已死则不再判断
        if (bird.isDead()) {
            return;
        }
        //遍历水管容器
        for (Pipe pipe : pipes) {
            //判断碰撞矩形是否有交集
            if (pipe.getPiRectangle().intersects(bird.getBirdColRectangle())) {
                bird.deadBirdFall();
                return;
            }
        }
    }

    //重置元素层
    public void reset() {
        for (Pipe pipe : pipes) {
            PipePool.giveBack(pipe);
        }
        pipes.clear();
    }
}
