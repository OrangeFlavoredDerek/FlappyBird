package component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import util.Constant;

/**
 * 游戏计时器，使用静态内部类实现了单例模式
 * 
 */

public class ScoreCounter {
    private static class ScoreCounterHolder {
        private static final ScoreCounter scoreCounter = new ScoreCounter();
    }

    public static ScoreCounter getInstance() {
        return ScoreCounterHolder.scoreCounter;
    }

    private long score = 0;//分数
    private long bestScore;//最高分

    private ScoreCounter() {
        bestScore = -1;
        try {
            loadBestScore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //装载最高纪录
    /**
     * 此处用到了对文件操作
     * @throws Exception
     */
    private void loadBestScore() throws Exception {
        File file = new File(Constant.scoreFilePath);
        if (file.exists()) {
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            bestScore = dis.readLong();
            dis.close();
        }
    }

    //保存分数
    public void saveScore() {
        bestScore = Math.max(bestScore, getCurrentScore());
        try {
            File file = new File(Constant.scoreFilePath);
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
            dos.writeLong(bestScore);//写入文件
            dos.close();//关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void score(Bird bird) {
        score += 1;
    }

    public long getBestScore() {
        return bestScore;
    }

    public long getCurrentScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }
}
