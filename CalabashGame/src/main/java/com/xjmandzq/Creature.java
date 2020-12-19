/*
基类：生物类
    属性：
        基本属性：阵营，姓名，是否治愈者，移速，是否存活，资源图片
        空间属性：当前位置，目的位置，操作性，指令，可见性
        战斗属性：最大血量，当前血量，最大魔法，当前魔法,普通攻击，技能攻击，技能攻击消耗魔法值,治愈术，治愈消耗魔法值
    构造器：
        构造器1：初始化基本属性和战斗属性
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
    enum Camp{ CALABASH,MONSTER }
    Camp camp;
    String cname;
    boolean healer;
    int speed = 1;
    boolean alive = true;
    String rscname;
    //--------------空间属性--------------
    public AtomicInteger curX=new AtomicInteger(0);
    public AtomicInteger curY=new AtomicInteger(0);
    protected int dstX;
    protected int dstY;
    public AtomicBoolean avaliable=new AtomicBoolean(true);
    public AtomicInteger cmd=new AtomicInteger(0);
    public AtomicBoolean visible=new AtomicBoolean(false);
    //---------------战斗属性-------------
    int maxHP;
    int HP;
    int maxMP;
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
        public void initpos(int srcx,int srxy,int dstx,int dsty){
            
        }

        public void moveToDst(){

        }
        public void useGnrAtk(){

        }
        public void useMgcAtk(){

        }
        public void useHealing(){

        }
        public void beenAtked(){

        }
        public void beenHealed(){

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