package sample;

import java.util.ArrayList;
import javafx.scene.image.Image;

public final class Attributes{
    //参数
    public static ArrayList<Image> images = new ArrayList<Image>();
    public static final int height=744;//地图高度
    public static final int width=1542;//地图宽度
    public static final int gridWidth=100;//格子宽度
    public static final int gridHeight=120;//格子高度
    public static final int mapLeft=310;//地图左边界
    public static final int mapTop=110;//地图上边界
    public static final int mapRight=1110;//地图右边界
    public static final int mapBottom=590;//地图下边界

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
    /*
    public static final int STARTPOSX = 15;
    public static final int STARTPOSY = 16;

    public static final int INDEX_MONSTERHEAD = 17;
    public static final int INDEX_HUMANCABIN = 18;
    public static final int INDEX_MONSTERCAVE = 19;
    public static final int INDEX_PREPAREGACKGROUND = 20;
    public static final int INDEX_HUMANHEAD2 = 21;
    public static final int INDEX_MONSTERHEAD2 = 22;
    public static final int INDEX_TWOPLAYER = 23;
    public static final int INDEX_AUTOPLAY = 24;
    public static final int INDEX_TWOPLAYER2 = 25;
    public static final int INDEX_AUTOPLAY2 = 26;
    public static final int INDEX_BATTLEFIELD = 27;
    public static final int INDEX_B_ATTACK12 = 28;
    public static final int INDEX_B_ATTACK22 = 29;
    public static final int INDEX_B_ATTACK32 = 30;
     */
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
        images.add(new Image("brother7.PNG"));
        images.add(new Image("grandpa.PNG"));
        images.add(new Image("pangolin.jpeg"));
        images.add(new Image("snake.PNG"));
        images.add(new Image("scorpion.PNG"));
        images.add(new Image("minion.PNG"));
    }
}