
package com.xjmandzq;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/*
    信息类：显示血条和蓝条; （有时间或许还能做个进度条？）
    暂时没想到应该放哪，想把这个实现了然后看看普通攻击是否正常工作
    属性：血值显示图，蓝值显示图，显示条,宽度，高度,提示
    构造器：
    方法：
        显示
        隐藏
*/

class Info extends Pane{
//属性-------------------------------
    ImageView HPline;
    ImageView MPline;
    Rectangle infobar;
    final int width = 20; //参数值之后再调整
    final int height = 20;
    Text textname;

//构造器------------------------------
    public Info()
    {
        infobar = new Rectangle(width,height);
        Stop[] stops = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.RED)}; //指定如何渐变；这个是从开始到结束颜色从黑到红，
        //当然也可以在其中加入新节点，例如new Stop(0.3, Color.BLUE) 
        //要用不同的颜色可以使用Color.color(R,G,B)
        LinearGradient lg1 = new LinearGradient( 0,0,1,0,true, CycleMethod.NO_CYCLE,stops);
        infobar.setFill(lg1);
        setMaxSize(width,height);
     
    }
//方法-----------------------------
    public void showBar(){

    }

    public void hideBar(){

    }

}