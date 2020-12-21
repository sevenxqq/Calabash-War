package com.xjmandzq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Battle{
    int[] map=new int[Attributes.gridNumX*Attributes.gridNumY];//记录地图中每格的角色id
    ArrayList<Creature> roles = new ArrayList<>();//存储游戏角色
    ArrayList<Info> hpbars =  new ArrayList<>();//角色对应的血条,存储顺序和角色一样
    //Map<String,Integer> roleId=new HashMap<>();
    int[] startPos={9,10,11,20,27,28,29,19,18,17,35,15,16,24,25,26,33,34};
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
                new Creature(0,"calabash1",this),
                new Creature(1,"calabash2",this),
                new Creature(2,"calabash3",this),
                new Creature(3,"calabash4",this),
                new Creature(4,"calabash5",this),
                new Creature(5,"calabash6",this),
                new Creature(6,"calabash7",this),
                new Creature(7,"grandpa",this),
                new Creature(8,"scorpion",this),
                new Creature(9,"snake",this),
                new Creature(10,"pangolin",this),
                new Creature(11,"minion",this),
                new Creature(12,"minion",this),
                new Creature(13,"minion",this),
                new Creature(14,"minion",this),
                new Creature(15,"minion",this),
                new Creature(16,"minion",this),
                new Creature(17,"minion",this)
        );
        for(int i=0;i<rolesList.size();i++){
            rolesList.get(i).curX.set(startPos[i]%9);
            rolesList.get(i).curY.set(startPos[i]/9);
        }
        roles.addAll(rolesList);
        //
        //roleId.put("bro1",0);
        //roleId.put("bro1",0);
        ///////////////////////
       
        //////////////////////////
    }
    /*
    public Creature getRole(int x,int y){
        return roles.get(map[x*Attributes.gridNumX+y]);
    }
    public int getRoleId(int x,int y){
        return map[x*Attributes.gridNumX+y];
    }
     */
    public boolean isOccupied(int x,int y){//判断地图[x,y]位置是否被角色占用
        return map[y*Attributes.gridNumX+x]!=-1;
    }
}