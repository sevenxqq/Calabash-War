'''
基类：生物类
    属性：
        基本属性：阵营，姓名，是否治愈者，移速，是否存活，资源图片
        空间属性：
        战斗属性：最大血量，当前血量，最大魔法，当前魔法,普通攻击，技能攻击，技能攻击消耗魔法值,治愈术，治愈消耗魔法值
    构造器：

    方法：
        战斗模块：
            获取属性；get_xxx()
            设置初始位置；
            移动；
            计算释放攻击/技能后属性改变
            计算受伤后属性改变
            计算治愈后属性改变
        控制模块：
            运行；
            指令解析；



'''


package com.XJMandZQ;

public class Creature implements Runnable{

//属性-------------------------------------------//
    //--------------基本属性----------------
    enum Camp{ CALABASH,MONSTER }
    Camp camp;
    String cname;
    boolean healer;
    int speed;
    boolean alive;
    String rscname;
    //--------------空间属性--------------

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


//方法------------------------------------------//
        //-----------------战斗模块-------------
        public void initpos(){
            
        }

        public void move(){

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