/*
基类：生物类
    属性：
        基本属性：阵营，姓名，ID,从属的对局，是否治愈者，移速，是否存活，资源图片
        空间属性：当前位置，目的位置，操作性，指令，可见性
        战斗属性：最大血量，当前血量，最大魔法，当前魔法,普通攻击，技能攻击，技能图标,技能攻击消耗魔法值,治愈术，治愈消耗魔法值
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
      


*/

package com.xjmandzq;

import java.util.ArrayList;
import java.util.concurrent.atomic.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Creature {

    // 属性-------------------------------------------//
    // --------------基本属性----------------
    Camp camp;
    String cname;
    int id;
    Battle battle;
    boolean healer;
    int speed = 1;
    boolean alive = true;
    String rscname;
    // --------------空间属性--------------
    public AtomicInteger curX = new AtomicInteger(0);
    public AtomicInteger curY = new AtomicInteger(0);
    protected int dstX; // 目的位置好像也没用到
    protected int dstY;
    public AtomicBoolean avaliable = new AtomicBoolean(true);
    public AtomicInteger cmd = new AtomicInteger(0);
    public AtomicBoolean visible = new AtomicBoolean(false);
    // ---------------战斗属性-------------
    final int maxHP = 100;
    int HP;
    final int maxMP = 100;
    int MP;
    int gnrAtk;
    int mgcAtk;
    Image img;
    int mgcCost;
    int healing;
    final int healCost = 5;

    // 构造器-----------------------------------------//
    public Creature(Camp itscamp, String itsname, boolean ishealer, int itsspeed, boolean isalive, String itsrsc,
            int itsmaxHP, int itsmaxMP, int itsgnrAtk, int itsmgcAtk, int itsmgcCost, int itshealing, int itshealCost) {
        this.camp = itscamp;
        this.cname = itsname;
        this.healer = ishealer;
        this.speed = itsspeed;
        this.alive = isalive;
        this.rscname = itsrsc;
        this.HP = this.maxHP;
        // this.maxMP = itsmaxMP;
        this.MP = 0;
        this.gnrAtk = itsgnrAtk;
        this.mgcAtk = itsmgcAtk;
        this.mgcCost = itsmgcCost;
        this.healing = itshealing;
       
    }

    public Creature(int id, String name, Camp icamp, boolean heal, Battle battle) {
        this.id = id;
        this.cname = name;
        this.battle = battle;
        this.HP = this.maxHP;
        this.MP = 0;
        this.camp = icamp;
        this.healer = heal;
        this.gnrAtk = 10;// 暂时写着
        this.mgcAtk = 20;
        this.healing = 10;

    }

    // 方法------------------------------------------//
    // -----------------战斗模块-------------
    // public void initatbt(int itsHP, int itsMP, int itsgnrAtk, int itsmgcAtk, int itsmgcCost, int itshealing,
    //         int itshealCost) {
    //     this.HP = itsHP;
    //     this.MP = itsMP;
    //     this.gnrAtk = itsgnrAtk;
    //     this.mgcAtk = itsmgcAtk;
    //     this.mgcCost = itsmgcCost;
    //     this.healing = itshealing;

    // }

    public void initpos(int srcx, int srxy, int dstx, int dsty) {// 暂时不用管
        // 进场时，人物图片在方格外，然后移动到初始队列位置
        // 可以实现一个移动效果
    }

    public void moveToDst(int x, int y) {
        this.dstX = x;
        this.dstY = y;
    }

    public void move(Direction dir) {// 朝着指定方向移动一步
        switch (dir) {
            case UP:
                move(curX.get(), curY.get() - 1);
                break;
            case DOWN:
                move(curX.get(), curY.get() + 1);
                break;
            case LEFT:
                move(curX.get() - 1, curY.get());
                break;
            case RIGHT:
                move(curX.get() + 1, curY.get());
                break;
            default:
                return;
        }
    }

    public boolean move(int x, int y) {// 移动到[x,y]
        if (x >= 0 && x < Attributes.gridNumX && y >= 0 && y < Attributes.gridNumY && !battle.isOccupied(x, y)) {
            battle.map[Attributes.gridNumX * curY.get() + curX.get()] = -1;// 空出原位置
            battle.map[Attributes.gridNumX * y + x] = id;// 标记新位置
            curX.set(x);
            curY.set(y);
            return true;
        }
        return false;
    }

    /*
     * parm:攻击方向 解释：只能朝一个格子内的敌人发起攻击 附加：后期可加红色数字图标展示失血效果
     */
    public int useGnrAtk(Direction dir) {
        int atkx = curX.get(), atky = curY.get();
        switch (dir) {
            case UP:
                atky--;
                break;
            case DOWN:
                atky++;
                break;
            case LEFT:
                atkx--;
                break;
            case RIGHT:
                atkx++;
                break;
            default:
                return -1;
        }
        if (battle.isOccupied(atkx, atky) == false)
            return -1;
        int atkid = battle.map[Attributes.gridNumX * atky + atkx];
        if (this.camp == battle.roles.get(atkid).camp)
            return -1;
        if (battle.roles.get(atkid).alive == false)
            return -1;
        battle.roles.get(atkid).beenAtked(this.gnrAtk);
        this.MP++;
        return atkid;
    }

    /*
     * 暂时不写 parm:释放技能方向 
     */
    int checkEnemy(int x, int y) {
        if (battle.isOccupied(x, y) == false)
            return -1;
        int atkid = battle.map[Attributes.gridNumX * y + x];
        if (this.camp == battle.roles.get(atkid).camp)
            return -1;
        if (battle.roles.get(atkid).alive == false)
            return -1;
        return atkid;
    }

    public void useMgcAtk(Direction dir) {
        if (this.MP <= 5)
            return;
        int x = curX.get();
        int y = curY.get();
        if (dir == Direction.RIGHT) {
            for (int i = x + 1; i < Attributes.gridNumX; i++) {
                int atkid = checkEnemy(i, y);
                if (atkid == -1)
                    continue;
                battle.roles.get(atkid).beenAtked(this.mgcAtk);
            }
        }
        else if (dir == Direction.LEFT){
            for (int i = x - 1; i >= 0; i--) {
                int atkid = checkEnemy(i, y);
                if (atkid == -1)
                    continue;
                battle.roles.get(atkid).beenAtked(this.mgcAtk);
            }
        }
        this.MP-=5;
    }

    /*
     * parm:指向方向 解释：只能向一个格子内的友方使用治愈术 附加：后期加绿色向上数字表示加血效果
     */

    int checkFriend(int x, int y) {
        if (battle.isOccupied(x, y) == false)
            return -1;
        int healid = battle.map[Attributes.gridNumX * y + x];
        if (this.camp != battle.roles.get(healid).camp)
            return -1;
        if (battle.roles.get(healid).alive == false)
            return -1;
        return healid;
    }

    public void useHealing(Direction dir) {
        if (this.MP <= 5)
            return ;
        System.out.println("使用治愈术");
        int x = curX.get(), y = curY.get();
        if (dir == Direction.RIGHT) {
            for (int i = x+1; i <= Attributes.gridNumX; i++) {
                int healid = checkFriend(i, y);
                if (healid == -1)
                    continue;
                battle.roles.get(healid).beenHealed(this.healing);
            }
        }
        else if (dir == Direction.LEFT){
            for (int i = x-1; i >= 0; i--) {
                int healid = checkFriend(i, y);
                if (healid == -1)
                    continue;
                battle.roles.get(healid).beenHealed(this.healing);
            }
        }
        this.MP -= this.healCost;
    }

    /*
     * parm:损失血量
     */
    public void beenAtked(int lost) {
        this.HP -= lost;
        battle.hpbars.get(this.id).setBar(battle.roles.get(this.id));
        // System.out.println(this.id + "被攻击" + this.HP);
        if (HP <= 0) { // 死亡后，地图上格子不被占用！！！
            this.alive = false;
            battle.map[Attributes.gridNumX * curY.get() + curX.get()] = -1;// 空出原位置

        }
    }

    public void beenHealed(int inc) {
        this.HP = Math.min(this.HP + inc, maxHP);
        battle.hpbars.get(this.id).setBar(battle.roles.get(this.id));
    }

}