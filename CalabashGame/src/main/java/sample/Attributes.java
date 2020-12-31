package sample;

import java.util.ArrayList;
import javafx.scene.image.Image;

public final class Attributes{
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
    public static final String localServerIP="127.0.0.1";//本机测试时服务器ip地址
    public static final String remoteServerIP="172.24.52.200";//联机测试时服务器ip地址（nju）

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
    public static final int CALABASH1_DEAD = 16;
    public static final int CALABASH2_DEAD = 17;
    public static final int CALABASH3_DEAD = 18;
    public static final int CALABASH4_DEAD = 19;
    public static final int CALABASH5_DEAD = 20;
    public static final int CALABASH6_DEAD = 21;
    public static final int CALABASH7_DEAD = 22;
    public static final int GRANDPA_DEAD = 23;
    public static final int PANGOLIN_DEAD = 24;
    public static final int SNAKE_DEAD = 25;
    public static final int SCORPION_DEAD = 26;
    public static final int MINION_DEAD = 27;

    //
    public static void init(){
        //添加角色图片
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
        images.add(new Image("brother7.PNG"));
        images.add(new Image("grandpa.PNG"));
        images.add(new Image("pangolin.jpeg"));
        images.add(new Image("snake.PNG"));
        images.add(new Image("scorpion.PNG"));
        images.add(new Image("minion.PNG"));
        //添加角色死亡图片

        images.add(new Image("brother1_dead.png"));
        images.add(new Image("brother2_dead.png"));
        images.add(new Image("brother3_dead.png"));
        images.add(new Image("brother4_dead.png"));
        images.add(new Image("brother5_dead.png"));
        images.add(new Image("brother6_dead.png"));
        images.add(new Image("brother7_dead.png"));
        images.add(new Image("grandpa_dead.png"));
        images.add(new Image("pangolin_dead.jpg"));
        images.add(new Image("snake_dead.png"));
        images.add(new Image("scorpion_dead.png"));
        images.add(new Image("minion_dead.png"));
    }
}