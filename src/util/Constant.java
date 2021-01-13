package util;

import java.awt.Color;
import java.awt.Font;

//常量类
/*final 修饰符，用来修饰类、方法和变量，final 修饰的类不能够被继承，修饰的方法不能被继承类重新定义，修饰的变量为常量，是不可修改的。*/

public class Constant {
    //窗口尺寸
    public static final int frameWidth = 420;
    public static final int frameHeight = 640;

    //游戏标题
    public static final String gameTitle = "Flappy Bird";

    //窗口位置
    public static final int frameX = 600;
    public static final int frameY = 100;

    //图像资源位置
    public static final String backgroundImagePath = "lib/img/background.png";//游戏背景图片

    //小鸟图片
    public static final String[][] birdsImagePath = {
        { "lib/img/0.png", "lib/img/1.png", "lib/img/2.png", "lib/img/3.png",
                "lib/img/4.png", "lib/img/5.png", "lib/img/6.png", "lib/img/7.png" },
        { "lib/img/up.png", "lib/img/up.png", "lib/img/up.png", "lib/img/up.png",
                "lib/img/up.png", "lib/img/up.png", "lib/img/up.png", "lib/img/up.png" },
        { "lib/img/down_0.png", "lib/img/down_1.png", "lib/img/down_2.png",
                "lib/img/down_3.png", "lib/img/down_4.png", "lib/img/down_5.png",
                "lib/img/down_6.png", "lib/img/down_7.png" },
        { "lib/img/dead.png", "lib/img/dead.png", "lib/img/dead.png", "lib/img/dead.png",
                "lib/img/dead.png", "lib/img/dead.png", "lib/img/dead.png",
                "lib/img/dead.png", } 
    };

    //云朵图片
    public static final String[] cloudsImagePath = {"lib/img/cloud_0.png", "lib/img/cloud_1.png"};

    //水管图片
    public static final String[] pipeImagePath = {"lib/img/pipe.png", "lib/img/pipe_top.png", "lib/img/pipe_bottom.png"};

    //其他图片
    public static final String titleImagePath = "lib/img/title.png";//标题
    public static final String noticeImagePath = "lib/img/start.png";
    public static final String scoreImagePath = "lib/img/score.png";//分数
    public static final String overImagePath = "lib/img/over.png";//游戏结束
    public static final String againImagePath = "lib/img/again.png";//重新开始

    //分数文件路径
    public static final String scoreFilePath = "lib/score";

    //游戏速度
    public static final int gameSpeed = 4;

    //游戏背景色
    public static final Color backgroundColor = new Color(0x4bc4cf);

    //游戏刷新率
    public static final int FPS = 1000/30;

    //标题栏高度
    public static final int topBarHeight = 20;

    //地面高度
    public static final int goundHeight = 35;

    //上方管道加长
    public static final int topPipeLengthening = 100;

    //云朵生成概率，单位为百分比
    public static final int cloudBornRate = 6;

    //云朵图片个数
    public static final int cloudImageCount = 2;

    //云朵最大数量
    public static final int maxCloudCount = 7;

    //字体
    public static final Font currentScoreFont = new Font("华文琥珀", Font.BOLD, 32);
    public static final Font scoreFont = new Font("华文琥珀", Font.BOLD, 24);

}