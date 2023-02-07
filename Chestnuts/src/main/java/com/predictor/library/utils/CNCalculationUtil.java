package com.predictor.library.utils;
/**
 * 主要功能： 提供点计算，各种计算算法等
 */
public class CNCalculationUtil {

    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    public static int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 两点间的距离
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double distance(double x1,double y1,double x2,double y2)
    {
        return Math.sqrt(Math.abs(x1-x2)*Math.abs(x1-x2)+Math.abs(y1-y2)*Math.abs(y1-y2));
    }

    /**
     * 计算点a(x,y)的角度
     * @param x
     * @param y
     * @return
     */
    public static double pointTotoDegrees(double x,double y)
    {
        return Math.toDegrees(Math.atan2(x,y));
    }


    /**
     * 点在圆内
     * @param sx
     * @param sy
     * @param r
     * @param x
     * @param y
     * @return
     */
    public static boolean checkInRound(float sx, float sy, float r, float x, float y)
    {
        return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
    }
}
