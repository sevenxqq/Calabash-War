package sample;

import java.util.*;

public class Battle{
    int[] map=new int[Attributes.gridNumX*Attributes.gridNumY];//记录地图中每格的角色id
    ArrayList<Creature> roles=new ArrayList<>();//存储游戏角色
    //Map<String,Integer> roleId=new HashMap<>();
    int[] startPos={9,10,11,20,27,28,29,19,18,17,35,15,16,24,25,26,33,34};

    Battle(){
        //游戏开始时角色在地图上的排列情况
        for(int i=0;i<map.length;i++){
            map[i]=0;
        }
        for(int i=0;i<startPos.length;i++){
            map[startPos[i]]=i;
        }
        //添加角色
        List<Creature> rolesList = Arrays.asList(
                new Creature("calabash1"),
                new Creature("calabash2"),
                new Creature("calabash3"),
                new Creature("calabash4"),
                new Creature("calabash5"),
                new Creature("calabash6"),
                new Creature("calabash7"),
                new Creature("grandpa"),
                new Creature("pangolin"),
                new Creature("scorpion"),
                new Creature("snake"),
                new Creature("minion"),
                new Creature("minion"),
                new Creature("minion"),
                new Creature("minion"),
                new Creature("minion"),
                new Creature("minion"),
                new Creature("minion")
        );
        roles.addAll(rolesList);
        //
        //roleId.put("bro1",0);
        //roleId.put("bro1",0);
    }
    public Creature getRole(int x,int y){
        return roles.get(map[x*Attributes.gridNumX+y]);
    }
    public int getRoleId(int x,int y){
        return map[x*Attributes.gridNumX+y];
    }

}