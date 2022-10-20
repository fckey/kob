package com.kob.backend.utils;

import java.util.Random;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 存放的是地图的逻辑
 * @date 2022/10/20 08:42:03
 */
public class Game {
    /**
     * 地图总的行数
     */
    final private Integer rows;
    /**
     * 地图总的列数
     */
    final private Integer cols;
    /**
     * 内部墙的数量
     */
    final private Integer inner_walls_count;
    /**
     * 哪些是有墙的
     */
    final private int[][] g;

    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public Game(Integer rows, Integer cols, Integer inner_walls_count){
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        g = new int[rows][cols];
    }

    /**
     * @author fckey
     * @date 2022/10/20 8:48
     * @description 获得地图
     * @return int[][]
     */
    public int[][] getG(){
        return g;
    }

    /**
     * @author fckey
     * @param sx 开始x坐标
     * @param sy 开始y坐标
     * @param tx 结束x坐标
     * @param ty 结束y坐标
     * @date 2022/10/20 9:04
     * @description 判断是否是连通的，两个地图之间
     * @return boolean
     */
    private boolean checkConnectivity(int sx, int sy, int tx, int ty){
        if(sx == tx && sy == ty){
            return true;
        }
        g[sx][sy] = 1;

        for(int i = 0; i < 4; i++){
            int x = sx + dx[i], y = sy + dy[i];
            if(x >= 0 && x < this.rows && y >= 0 && y < this.cols && g[x][y] == 0){
                if(checkConnectivity(x, y ,tx, ty)){
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }
        g[sx][sy] = 0;
        return false;
    }

    /**
     * @author fckey
     * @date 2022/10/20 8:51
     * @description 画地图的操作
     * @return boolean
     */
    public boolean draw(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j ++){
                g[i][j] = 0;
            }
        }
        for(int r = 0; r < this.rows; r++){
            g[r][0] = g[r][this.cols - 1] = 1;
        }
        for(int c = 0; c < this.cols; c++){
            g[0][c] = g[this.rows - 1][c] = 1;
        }
        // 随机地图
        Random random = new Random();
        for(int i = 0; i < this.inner_walls_count / 2; i ++){
            for(int j = 0; j < 1000; j ++){
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);
                // 判断是否是有重复的生成逻辑
                if(g[r][c] == 1 || g[this.rows - 1 - r][this.cols - 1 - c] == 1){
                    continue;
                }
                if(r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2){
                    continue;
                }

                g[r][c]= g[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }
        // 判断生成的是否是具有连通性的
        return checkConnectivity(this.rows - 2, 1, 1, this.cols - 2);
    }
    /**
     * @author fckey
     * @date 2022/10/20 9:05
     * @description 创建地图
     * @return void
     */
    public void creatMap(){
        for(int i = 0; i < 1000; i++){
            if(draw()){
                break;
            }
        }
    }
}
