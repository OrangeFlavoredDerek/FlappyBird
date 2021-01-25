package component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import util.Constant;
import util.GameUtil;

/**
 * 前景层， 目前管理云朵的生成逻辑并绘制容器中的云朵
 * 
 */

public class GameForeground {
    private final List<Cloud> clouds;//云朵的容器
    private final BufferedImage[] clouImages;//图片资源
    private long time;//控制云的逻辑运算周期
    public static final int cloudInterval = 100;//云朵刷新的逻辑运算的周期

    public GameForeground() {
        clouds = new ArrayList<>();//云朵的容器
        //读入图片资源
        clouImages = new BufferedImage[Constant.cloudImageCount];
        for (int i = 0; i < Constant.cloudImageCount; i++) {
            clouImages[i] = GameUtil.loadBufferedImage(Constant.cloudsImagePath[i]);
        }
        time = System.currentTimeMillis();//获取当前时间，用于控制云的逻辑运算周期
    }

    //绘制方法
    public void draw(Graphics g, Bird bird) {
        cloudBornLogic();
        for (Cloud cloud : clouds) {
            cloud.draw(g, bird);
        }
    }

    //云朵的控制
    private void cloudBornLogic() {
        //每100ms运算一次
        if (System.currentTimeMillis() - time > cloudInterval) {
            time = System.currentTimeMillis();//重置time
            //如果屏幕的云朵数量小于允许的最大数量，则根据概率随机添加云朵
            if (clouds.size() < Constant.maxCloudCount) {//size()返回此List中的元素个数
                try {
                    if (GameUtil.isInProbability(Constant.cloudBornRate, 100)) {//根据给定的概率添加云朵
                        int index = GameUtil.getRandomNumber(0, Constant.cloudImageCount);//随机选取云朵图片

                        //云朵刷新坐标
                        int x = Constant.frameWidth;//从屏幕左侧开始刷新
                        //y坐标随机在上1/3屏选取
                        int y = GameUtil.getRandomNumber(Constant.topBarHeight, Constant.frameHeight/3);

                        //向容器中添加云朵
                        Cloud cloud = new Cloud(clouImages[index], x, y);
                        clouds.add(cloud);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }//添加云朵

                //若云朵飞出屏幕则从容器中移除
                for (int i = 0; i < clouds.size(); i++) {
                    //遍历容器中的云朵
                    Cloud tempCloud = clouds.get(i);
                    if (tempCloud.isOutFrame()) {
                        clouds.remove(i);
                        i--;
                    }
                }
            }
        }
    }
}
