/*
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



*/


package sample;

public class Creature implements Runnable{

    //属性-------------------------------------------//
    //--------------基本属性----------------
    enum Camp{ CALABASH,MONSTER }
    Camp camp;
    int id;//角色id
    String cname;//角色名称
    Battle battle;//从属的对局
    boolean healer;
    int speed;
    boolean alive;
    String rscname;
    //--------------空间属性--------------
    int posX;
    int posY;
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
    Creature(int id,String name,Battle battle){
        this.id=id;
        this.cname=name;
        this.battle=battle;
    }

    //方法------------------------------------------//
    //-----------------战斗模块-------------
    public void initpos(){

    }

    public void move(Direction dir){//根据方向移动
        switch(dir){
            case UP: move(posX,posY-1);break;
            case DOWN:move(posX,posY+1);break;
            case LEFT:move(posX-1,posY);break;
            case RIGHT:move(posX+1,posY);break;
            default:;
        }
    }
    public void move(int x,int y){//移动到[x,y]
        //TODO:加上只能移到相邻格子的限制？
        if (x>=0 && x<Attributes.gridNumX && y>=0 && y<Attributes.gridNumY
                && !battle.isOccupied(x,y)){
            //移动条件：不超边界且无其他角色占用
            //TODO:对地图的访问控制
            battle.map[Attributes.gridNumX*posY+posX]=-1;//空出原位置
            battle.map[Attributes.gridNumX*y+x]=id;//标记新位置
            posX=x;
            posY=y;
        }
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