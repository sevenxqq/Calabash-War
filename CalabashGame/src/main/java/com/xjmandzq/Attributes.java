package com.xjmandzq;

import java.util.ArrayList;
import javafx.scene.image.Image;

public final class Attributes{
    public static final String serverIP = "127.0.0.1"; //服务器ip:,127.0.0.1; 172.24.52.200
    //参数
    public static ArrayList<Image> images = new ArrayList<Image>();
    public static final int height=744;//地图高度（像素级）
    public static final int width=1542;//地图宽度（像素级）
    public static final int gridNumX=9;//横向格子数量
    public static final int gridNumY=5;//纵向格子数量
    public static final int gridWidth=100;//格子宽度（像素级）
    public static final int gridHeight=120;//格子高度（像素级）
    public static final int mapLeft=310;//地图左边界（像素级）
    public static final int mapTop=110;//地图上边界（像素级）
    public static final int mapRight=1110;//地图右边界（像素级）
    public static final int mapBottom=590;//地图下边界（像素级）
    public static final int rolesNum=9;//角色数量
    public static final int hpoffset = 18;//角色id到血条的偏移
    public static final int deadoffset = 18;
    
    //图片编号
    public static final int MAP = 0;
    public static final int START = 1;
    public static final int STARTICON = 2;
    public static final int EXITICON = 3;
    public static final int CALABASH1 = 4;
    public static final int CALABASH2 = 5;
    public static final int CALABASH3 = 6;
    public static final int CALABASH4 = 7;
    public static final int CALABASH5 = 8;
    public static final int CALABASH6 = 9;
    public static final int CALABASH7 = 10;
    public static final int GRANDPA = 11;
    public static final int PANGOLIN = 12;
    public static final int SNAKE = 13;
    public static final int SCORPION = 14;
    public static final int MINION = 15;
    //
    public static final int DEADBRO1 = 16;
    public static final int DEADBRO2 = 17;
    public static final int DEADBRO3 = 18;
    public static final int DEADBRO4 = 19;
    public static final int DEADBRO5 = 20;
    public static final int DEADBRO6 = 21;
    public static final int DEADBRO7 = 22;
    public static final int DEADGRANDPA = 23;
    public static final int DEADPANGOLIN = 24;
    public static final int DEADSNAKE = 25;
    public static final int DEADSCORPION = 26;
    public static final int DEADMINION = 27;

  
    //
    public static void init(){
        //添加图片
        images.add(new Image("map.jpg"));
        images.add(new Image("start.jpg"));
        images.add(new Image("start_game.png"));
        images.add(new Image("exit.png"));
        images.add(new Image("brother1.PNG"));
        images.add(new Image("brother2.PNG"));
        images.add(new Image("brother3.PNG"));
        images.add(new Image("brother4.PNG"));
        images.add(new Image("brother5.PNG"));
        images.add(new Image("brother6.PNG"));
        images.add(new Image("brother7.PNG"));//10
        images.add(new Image("grandpa.PNG"));
        images.add(new Image("pangolin.jpeg"));
        images.add(new Image("snake.PNG"));
        images.add(new Image("scorpion.PNG"));
        images.add(new Image("minion.PNG"));//15
        images.add(new Image("deadbro1.png"));
        images.add(new Image("deadbro2.png"));
        images.add(new Image("deadbro3.png"));
        images.add(new Image("deadbro4.png"));
        images.add(new Image("deadbro5.png"));
        images.add(new Image("deadbro6.png"));
        images.add(new Image("deadbro7.png"));
        images.add(new Image("deadgrandpa.png"));
        images.add(new Image("deadpangolin.jpeg"));
        images.add(new Image("deadsnake.png"));
        images.add(new Image("deadscorpion.png"));
        images.add(new Image("deadminion.png"));
       
    }
}