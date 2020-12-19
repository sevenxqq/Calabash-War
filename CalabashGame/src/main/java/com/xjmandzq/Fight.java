/*

子弹类：
    属性：位置，可见性，图标（待定）
    构造器：
    方法：
战斗类：
    属性：
    构造器：
    方法：
        void generalAtk()
*/

package com.xjmandzq;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class Bullet {
    AtomicInteger curX = new AtomicInteger(0);
    AtomicInteger curY = new AtomicInteger(0);
    AtomicBoolean visible = new AtomicBoolean(false);
    Image icon;     
    public Bullet(Image img)
    {
        icon = img;
    }
}

class Fight{
//属性-----------------------------

//构造器---------------------------

//方法--------------------------
    void generalAtk(){

    }
}