
package com.xjmandzq;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/*
    样式： textname: 矩形框，框内填充颜色 这里
    信息类：显示血条
    属性：血条，宽度，高度,提示，可见性
    构造器：
    方法：
        填充颜色
*/

class Info extends Pane{
//属性-------------------------------
    Rectangle HPbar;
    // Rectangle MPbar;
    final int width = 90; 
    final int height = 5;
    AtomicBoolean visible=new AtomicBoolean(true);
    Lock lock = new ReentrantLock();

//构造器------------------------------
    public Info()
    {
        HPbar = new Rectangle(width,height);
        Stop[] stops = new Stop[] { new Stop(0, Color.RED), new Stop(1, Color.RED)}; 
        LinearGradient lg1 = new LinearGradient( 0,0,1,0,true, CycleMethod.NO_CYCLE,stops);
        HPbar.setFill(lg1);
        setMaxSize(width,height);
        HPbar.setWidth(width);
        HPbar.setHeight(height);
        HPbar.setArcWidth(1);
        HPbar.setArcHeight(1);
        getChildren().add(HPbar);
    }
//方法-----------------------------
    public void setBar(Creature role){
        if (role.HP==role.maxHP)
            return;
        float ratio = (float)role.HP/(float)role.maxHP;
        Stop[] stops = new Stop[] { new Stop(0, Color.rgb(255,0,0)), new Stop(ratio,Color.rgb(255, 153, 18,0.5)),
                 new Stop(1, Color.rgb(255,255,255,0)) };
        LinearGradient lg1 = new LinearGradient( 0,0,1,0,true, CycleMethod.NO_CYCLE,stops);
        HPbar.setFill(lg1);
    }
}