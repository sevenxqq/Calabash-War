/*

子弹类：
    属性：位置，可见性，图标（待定）,
    构造器：
    方法：

*/

package com.xjmandzq;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.spi.DirObjectFactory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.CycleMethod;

class Bullet {
    int curx = 0;
    int cury = 0;
    int imgindex; //对应的图片下标
    Direction dir;   //飞行方向
    public Bullet(int index,Direction dirt)
    {
       imgindex = index;
       dir = dirt;
    }

    public Bullet(int index,int x,int y){
        imgindex = index;
        curx = x;
        cury = y;
    }

    public void setPos(int x,int y){//怎么创建一个图片让它在画布上显示，消亡就消失
        curx = x;
        cury = y;
    }

    public void destroy(){//删除label?

    }
}

class BulletControl{

}
