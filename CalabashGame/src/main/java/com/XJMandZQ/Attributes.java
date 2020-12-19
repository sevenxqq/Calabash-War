package sample;

import java.util.ArrayList;
import javafx.scene.image.Image;

public final class Attributes{
    //
    public static ArrayList<Image> images = new ArrayList<Image>();
    public static final int height=800;
    public static final int width=1800;
    //
    public static final int MAP = 0;
    public static final int START = 1;
    public static final int STARTGAME = 2;
    public static final int EXIT = 3;
    /*
    public static final int INDEX_START = 2;
    public static final int INDEX_LOAD = 3;
    public static final int INDEX_CLOSE = 4;
    public static final int INDEX_DCLOSE = 5;
    public static final int INDEX_FLOATBAR = 6;
    public static final int INDEX_TMPSKILL = 7;
    public static final int INDEX_SAVE = 8;
    public static final int INDEX_END = 9;
    public static final int INDEX_RIP = 10;
    public static final int INDEX_B_ATTACK1 = 11;
    public static final int INDEX_B_ATTACK2 = 12;
    public static final int INDEX_B_ATTACK3 = 13;
    public static final int INDEX_HP = 14;
    public static final int INDEX_MP = 15;
    public static final int INDEX_HUMANHEAD = 16;
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
        images.add(new Image("map_gray.jpeg"));
        images.add(new Image("start.png"));
        images.add(new Image("start_game.png"));
        images.add(new Image("exit.png"));
    }
}