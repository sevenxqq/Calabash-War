
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
    信息类：显示血条和蓝条; （有时间或许还能做个进度条？）
    暂时没想到应该放哪，想把这个实现了然后看看普通攻击是否正常工作
    属性：血值显示图，蓝值显示图，显示条,血条，蓝条，宽度，高度,提示，可见性
    构造器：
        正在学习如何显示，默认构造器里先放一些如何使用的代码
    方法：
        显示
        隐藏
*/

class Info extends Pane{
//属性-------------------------------
    ImageView HPview;
    ImageView MPview;
    Rectangle HPbar;
    // Rectangle MPbar;
    final int width = 200; //参数值之后再调整
    final int height = 200;
    Text textname;
    AtomicBoolean visible=new AtomicBoolean(true);
    Lock lock = new ReentrantLock();

//构造器------------------------------
    public Info()
    {
        //矩形框颜色填充
        HPbar = new Rectangle(width,height);
    
        Stop[] stops = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.RED)}; //指定如何渐变；这个是从开始到结束颜色从黑到红，
        //当然也可以在其中加入新节点，例如new Stop(0.3, Color.BLUE) 
        //要用不同的颜色可以使用Color.color(R,G,B)
        LinearGradient lg1 = new LinearGradient( 0,0,1,0,true, CycleMethod.NO_CYCLE,stops);
        HPbar.setFill(lg1);
        setMaxSize(width,height);
        //矩形框坐标位置，大小，圆角（默认矩形框是尖角）
        // r.setX(50);
        // r.setY(50);
        HPbar.setWidth(200);
        HPbar.setHeight(100);
        HPbar.setArcWidth(20);
        HPbar.setArcHeight(20);
        getChildren().add(HPbar);
        //文本
       // Text textname = new Text (10, 20, "This is a text sample"); //在指定坐标放置文本，以及文本内容
        textname.setText("This is a text sample"); //设置文本内容
        textname.setFont(Font.font ("Verdana", 20));//设置字体大小和样式
        textname.setFill(Color.RED);//字体颜色
        textname.setTextAlignment(TextAlignment.CENTER);//中点对齐
        textname.setLayoutX(width);
        System.out.printf("%s,%s",width,height);
        textname.setLayoutY(height);
        getChildren().add(textname);
     
    }
//方法-----------------------------
    public void setBar(Creature role){
        float ratio = role.HP/role.maxHP;
        Stop[] stops = new Stop[] { new Stop(0, Color.RED), new Stop(ratio,Color.RED), new Stop(1, Color.RED)}; 
        LinearGradient lg1 = new LinearGradient( 0,0,1,0,true, CycleMethod.NO_CYCLE,stops);
        HPbar.setFill(lg1);
        HPbar.setLayoutX(width);
        HPbar.setLayoutY(height);
        getChildren().add(HPbar);

    }

    public void showBar(){
        double dy = width/36.0;
        new Thread(()->{
            lock.lock();
            if (visible.get() == true) //已经是显示状态
                return;
            visible.set(true);
            double cury = getLayoutY();
            for (int i = 0; i < 36; i++) {
                cury -= dy;
                double y = cury;
                Platform.runLater(()-> {
                    relocate(getLayoutX(), y);
                });
                try {
                    Thread.sleep(700 / 36);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }).start();
    }

    public void hideBar(){
        double dy = width;
        new Thread(()->{
            lock.lock();
            if(visible.get() == false) {
                return;
            }
            visible.set(false);
            double cury = getLayoutY();
            for (int i = 0; i < 36; i++) {
                cury += dy;
                double y = cury;
                Platform.runLater(()-> {
                    relocate(getLayoutX(), y);
                });
                try {
                    Thread.sleep(700 / 36);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }).start();
    }

}