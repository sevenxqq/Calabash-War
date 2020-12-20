/*
基类：生物类
    属性：
        基本属性：阵营，姓名，ID,从属的对局，是否治愈者，移速，是否存活，资源图片
        空间属性：当前位置，目的位置，操作性，指令，可见性
        战斗属性：最大血量，当前血量，最大魔法，当前魔法,普通攻击，技能攻击，技能攻击消耗魔法值,治愈术，治愈消耗魔法值
    构造器：
        构造器1：初始化基本属性和战斗属性
        构造器2：temp id name battle
    方法：
        战斗模块：
            设置初始战斗属性；（好像有些多余，暂时留着）
            获取属性；get_xxx()；（暂时没写，有需要再加）
            设置初始位置；（入场动作）
            移动到目的位置；
            计算释放攻击/技能后属性改变
            计算受伤后属性改变
            计算治愈后属性改变
        控制模块：
            运行；
            指令解析；


*/


package com.xjmandzq;
import java.util.concurrent.atomic.*;



public class Creature implements Runnable{

//属性-------------------------------------------//
    //--------------基本属性----------------
    Camp camp;
    String cname;
    int id;
    Battle battle;
    boolean healer;
    int speed = 1;
    boolean alive = true;
    String rscname;
    //--------------空间属性--------------
    public AtomicInteger curX=new AtomicInteger(0);
    public AtomicInteger curY=new AtomicInteger(0);
    protected int dstX; //目的位置好像也没用到
    protected int dstY;
    public AtomicBoolean avaliable=new AtomicBoolean(true);
    public AtomicInteger cmd=new AtomicInteger(0);
    public AtomicBoolean visible=new AtomicBoolean(false);
    //---------------战斗属性-------------
    final int maxHP = 100;
    int HP;
    final int maxMP = 100;
    int MP;
    int gnrAtk;
    int mgcAtk;
    int mgcCost;
    int healing;
    int healCost;

//构造器-----------------------------------------//
    public Creature(Camp itscamp,String itsname,boolean ishealer,int itsspeed,boolean isalive,String itsrsc,
    int itsmaxHP,int itsHP,int itsmaxMP,int itsMP,int itsgnrAtk,int itsmgcAtk,int itsmgcCost,int itshealing,int itshealCost
    ){
        this.camp = itscamp;
        this.cname = itsname;
        this.healer = ishealer;
        this.speed = itsspeed;
        this.alive = isalive;
        this.rscname = itsrsc;
        this.maxHP = itsmaxHP;
        this.HP = itsHP;
        this.maxMP = itsmaxMP;
        this.MP = itsMP;
        this.gnrAtk = itsgnrAtk;
        this.mgcAtk = itsmgcAtk;
        this.mgcCost = itsmgcCost;
        this.healing = itshealing;
        this.healCost = itshealCost;
    }
  
    public Creature(int id,String name,Battle battle){
        this.id=id;
        this.cname=name;
        this.battle=battle;
    }

//方法------------------------------------------//
        //-----------------战斗模块-------------
        public void initatbt(int itsmaxHP,int itsHP,int itsmaxMP,int itsMP,int itsgnrAtk,int itsmgcAtk,int itsmgcCost,int itshealing,int itshealCost){
            this.maxHP = itsmaxHP;
            this.HP = itsHP;
            this.maxMP = itsmaxMP;
            this.MP = itsMP;
            this.gnrAtk = itsgnrAtk;
            this.mgcAtk = itsmgcAtk;
            this.mgcCost = itsmgcCost;
            this.healing = itshealing;
            this.healCost = itshealCost;

        }
        public void initpos(int srcx,int srxy,int dstx,int dsty){//暂时不用管
        //    进场时，人物图片在方格外，然后移动到初始队列位置
        //    可以实现一个移动效果
        }

        public void moveToDst(int x,int y){
            this.dstX = x;
            this.dstY = y;
        }
        public void move(Direction dir){//朝着指定方向移动一步
            switch(dir){
                case UP: move(curX.get(),curY.get()-1);break;
                case DOWN:move(curX.get(),curY.get()+1);break;
                case LEFT:move(curX.get()-1,curY.get());break;
                case RIGHT:move(curX.get()+1,curY.get());break;
                default:;
            }
        }
        public void move(int x,int y){//移动到[x,y]
            //TODO:加上只能移到相邻格子的限制？
            if (x>=0 && x<Attributes.gridNumX && y>=0 && y<Attributes.gridNumY
                    && !battle.isOccupied(x,y)){
                //移动条件：不超边界且无其他角色占用
                //TODO:对地图的访问控制
                battle.map[Attributes.gridNumX*curY.get()+curX.get()]=-1;//空出原位置
                battle.map[Attributes.gridNumX*y+x]=id;//标记新位置
                curX.set(x);
                curY.set(y);
            }
        }
        public void useGnrAtk(){
            
        }
        public void useMgcAtk(){

        }
        public void useHealing(){

        }
        //parm:
        public void beenAtked(int lost){
            this.HP-=lost;
            if (HP<=0)
                this.alive = false;
        }
        public void beenHealed(int inc){
            this.HP = Math.min(HP + inc,maxHP);
        }
        //------------------控制模块-------------
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cmdHandler();       
            }
        }
        protected void cmdHandler() {
            
        }


    

}