package com.xjmandzq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Battle{
    boolean started=false;
    int enemyId;
    int[] map=new int[Attributes.gridNumX*Attributes.gridNumY];//记录地图中每格的角色id
    ArrayList<Creature> roles = new ArrayList<>();//存储游戏角色
    ArrayList<Info> hpbars =  new ArrayList<>();//角色对应的血条,存储顺序和角色一样
    int[] startPos={9,10,11,20,27,28,29,19,18,17,35,15,16,24,25,26,33,34};
    Camp myCamp=Camp.CALABASH;
    XMLFile gameprogress = new XMLFile();
    int selected;//被选中的角色id
    Battle(){
        //游戏开始时角色在地图上的排列情况
        for(int i=0;i<map.length;i++){
            map[i]=-1;
        }
        for(int i=0;i<startPos.length;i++){
            map[startPos[i]]=i;
        }
        //添加角色
        List<Creature> rolesList = Arrays.asList(
                new Creature(0,"calabash1",Camp.CALABASH,this),
                new Creature(1,"calabash2",Camp.CALABASH,this),
                new Creature(2,"calabash3",Camp.CALABASH,this),
                new Creature(3,"calabash4",Camp.CALABASH,this),
                new Creature(4,"calabash5",Camp.CALABASH,this),
                new Creature(5,"calabash6",Camp.CALABASH,this),
                new Creature(6,"calabash7",Camp.CALABASH,this),
                new Creature(7,"grandpa",Camp.CALABASH,this),
                new Creature(8,"scorpion",Camp.MONSTER,this),
                new Creature(9,"snake",Camp.MONSTER,this),
                new Creature(10,"pangolin",Camp.MONSTER,this),
                new Creature(11,"minion",Camp.MONSTER,this),
                new Creature(12,"minion",Camp.MONSTER,this),
                new Creature(13,"minion",Camp.MONSTER,this),
                new Creature(14,"minion",Camp.MONSTER,this),
                new Creature(15,"minion",Camp.MONSTER,this),
                new Creature(16,"minion",Camp.MONSTER,this),
                new Creature(17,"minion",Camp.MONSTER,this)
        );
        for(int i=0;i<rolesList.size();i++){
            rolesList.get(i).curX.set(startPos[i]%9);
            rolesList.get(i).curY.set(startPos[i]/9);
            Info tempbar = new Info();
            tempbar.setBar(rolesList.get(i));
            hpbars.add(tempbar);
        }
        roles.addAll(rolesList);
       
       
        //////////////////////////
    }
    

    public void setCamp(Camp c){
        myCamp=c;
    }

    public boolean isOccupied(int x,int y){//判断地图[x,y]位置是否被角色占用
        return map[y*Attributes.gridNumX+x]!=-1;
    }

    public String dir2str(Direction dir){
        switch(dir){
            case LEFT: return "LEFT";
            case RIGHT: return "RIGHT";
            case UP: return "UP";
            case DOWN: return "DOWN";
            default:return "";
        }
    }
}