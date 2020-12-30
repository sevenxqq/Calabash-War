
  
package com.xjmandzq;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;


public class Main extends Application {
    int id;
    Pane canvas=new Pane();
    Scene scene=new Scene(canvas, Attributes.width, Attributes.height);
    double mouseX;
    double mouseY;
    CalabashClient calabashClient=new CalabashClient(this);
    Battle battle=new Battle();
    ArrayList<Label> labels=new ArrayList<>();
    @Override
    public void init() throws Exception{
        Attributes.init();

    }


    public void startInterface() {
        //背景
        ImageView start = new ImageView(Attributes.images.get(Attributes.START));
        start.setFitHeight(Attributes.height);
        start.setFitWidth(Attributes.width);
        canvas.getChildren().add(start);
        //开始游戏按钮
        ImageView startGame = new ImageView(Attributes.images.get(Attributes.STARTICON));
        startGame.setFitHeight(100);
        startGame.setFitWidth(200);
        Label startLabel = new Label("",startGame);
        startLabel.setMaxSize(200,100);
        startLabel.setLayoutX(550);
        startLabel.setLayoutY(550);
        startLabel.setOnMouseClicked((MouseEvent e)->{ waitStart(); });
        canvas.getChildren().add(startLabel);
        //退出按钮
        /*canvas.setOnMousePressed((MouseEvent e)->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            System.out.println(mouseX);
        });*/

        // ImageView saveimg = new ImageView(Configs.SysIcons.get(Configs.INDEX_SAVE));
        // saveimg.setFitWidth(Configs.B_SIZE/2.0);
        // saveimg.setFitHeight(Configs.B_SIZE/2.0);
        // save = new Label("", saveimg);
        // save.setLayoutX(Configs.WIN_WIDTH - Configs.B_SIZE*1.5);
        // save.setOnMouseClicked((MouseEvent)->{
        //     FileChooser chooser = new FileChooser();
        //     File file = chooser.showSaveDialog(stage);
        //     if(file!=null)
        //         manager.savestack.saveToFile(file.getPath()+".xml");
        // });
        // pane.getChildren().add(save);

    }


    int xToPixel(int x){//地图格X坐标转换为像素横坐标
        return Attributes.mapLeft+x*Attributes.gridWidth;
    }

    int yToPixel(int y){//地图格Y坐标转换为像素纵坐标
        return Attributes.mapTop+y*Attributes.gridHeight;
    }

    void moveRoleLabel(int id,int x,int y){
        labels.get(id).setLayoutX(xToPixel(x));
        labels.get(id).setLayoutY(yToPixel(y));
        labels.get(id + Attributes.hpoffset).setLayoutX(xToPixel(x));
        labels.get(id + Attributes.hpoffset).setLayoutY(yToPixel(y));
    }

    void waitStart(){//等待其他玩家进入后开局
        ImageView start = new ImageView(Attributes.images.get(Attributes.START));
        start.setFitHeight(Attributes.height);
        start.setFitWidth(Attributes.width);
        canvas.getChildren().add(start);
        calabashClient.connect(Attributes.serverIP); 
        enterBattle();
    }

    public void enterBattle() {

        //Battle battle=new Battle();
        if(battle.myCamp==Camp.MONSTER)
            battle.selected=9;
        else
            battle.selected=0;
        //显示地图
        ImageView map = new ImageView(Attributes.images.get(Attributes.MAP));
        map.setFitHeight(Attributes.height);
        map.setFitWidth(Attributes.width);
        canvas.getChildren().add(map);

        //加载角色图片
        List<ImageView> picsList=Arrays.asList(
                new ImageView(Attributes.images.get(Attributes.CALABASH1)),
                new ImageView(Attributes.images.get(Attributes.CALABASH2)),
                new ImageView(Attributes.images.get(Attributes.CALABASH3)),
                new ImageView(Attributes.images.get(Attributes.CALABASH4)),
                new ImageView(Attributes.images.get(Attributes.CALABASH5)),
                new ImageView(Attributes.images.get(Attributes.CALABASH6)),
                new ImageView(Attributes.images.get(Attributes.CALABASH7)),
                new ImageView(Attributes.images.get(Attributes.GRANDPA)),
                new ImageView(Attributes.images.get(Attributes.PANGOLIN)),
                new ImageView(Attributes.images.get(Attributes.SCORPION)),
                new ImageView(Attributes.images.get(Attributes.SNAKE)),
                new ImageView(Attributes.images.get(Attributes.MINION)),
                new ImageView(Attributes.images.get(Attributes.MINION)),
                new ImageView(Attributes.images.get(Attributes.MINION)),
                new ImageView(Attributes.images.get(Attributes.MINION)),
                new ImageView(Attributes.images.get(Attributes.MINION)),
                new ImageView(Attributes.images.get(Attributes.MINION)),
                new ImageView(Attributes.images.get(Attributes.MINION)),
                //18,不加入新的label,用于替换
                new ImageView(Attributes.images.get(Attributes.DEADBRO1)),
                new ImageView(Attributes.images.get(Attributes.DEADBRO2)),
                new ImageView(Attributes.images.get(Attributes.DEADBRO3)),
                new ImageView(Attributes.images.get(Attributes.DEADBRO4)),
                new ImageView(Attributes.images.get(Attributes.DEADBRO5)),
                new ImageView(Attributes.images.get(Attributes.DEADBRO6)),
                new ImageView(Attributes.images.get(Attributes.DEADBRO7)),
                new ImageView(Attributes.images.get(Attributes.DEADGRANDPA)),
                new ImageView(Attributes.images.get(Attributes.DEADPANGOLIN)),
                new ImageView(Attributes.images.get(Attributes.DEADSCORPION)),
                new ImageView(Attributes.images.get(Attributes.DEADSNAKE)),
                new ImageView(Attributes.images.get(Attributes.DEADMINION)),
                new ImageView(Attributes.images.get(Attributes.DEADMINION)),
                new ImageView(Attributes.images.get(Attributes.DEADMINION)),
                new ImageView(Attributes.images.get(Attributes.DEADMINION)),
                new ImageView(Attributes.images.get(Attributes.DEADMINION)),
                new ImageView(Attributes.images.get(Attributes.DEADMINION)),
                new ImageView(Attributes.images.get(Attributes.DEADMINION))      
        );
        ArrayList<ImageView> pics=new ArrayList<>();
        pics.addAll(picsList);
        for(int i=0;i<2*Attributes.rolesNum;i++){
            pics.get(i).setFitWidth(100);
            pics.get(i).setFitHeight(100);
           
        }
        //人物的显示
        for(int i=0;i< 2*Attributes.rolesNum;i++){
            Label label=new Label("",pics.get(i));
            label.setLayoutX(Attributes.mapLeft+battle.roles.get(i).curX.get()*Attributes.gridWidth);
            label.setLayoutY(Attributes.mapTop+battle.roles.get(i).curY.get()*Attributes.gridHeight);
            System.out.printf("%d,%d,%d",i,battle.roles.get(i).curX.get(),battle.roles.get(i).curY.get());
            System.out.println();
            labels.add(label);
            canvas.getChildren().add(label);
        }
        //血量条的显示
        for(int i=0;i< 2*Attributes.rolesNum;i++){
            battle.hpbars.get(i).setBar(battle.roles.get(i));
            Label labelbar = new Label("",battle.hpbars.get(i));
            labelbar.setLayoutX(Attributes.mapLeft+battle.roles.get(i).curX.get()*Attributes.gridWidth);
            labelbar.setLayoutY(Attributes.mapTop+battle.roles.get(i).curY.get()*Attributes.gridHeight - 3);
            labels.add(labelbar);
            canvas.getChildren().add(labelbar);
        }

       
        //响应键盘，控制角色移动和攻击
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int selected=battle.selected;
                String key= event.getText();
                if (key.length()>0 && key.charAt(0)>='1' && key.charAt(0)<='9'){
                    selected=key.charAt(0)-'1';
                    if(battle.myCamp==Camp.MONSTER)
                        selected+=9;
                    battle.selected=selected;
                    System.out.println(selected); 
                }
                if (battle.roles.get(selected).alive==false)
                    return;
                if (key.equals("a")) {
                    battle.roles.get(selected).move(Direction.LEFT);
                    int x=battle.roles.get(selected).curX.get();
                    int y=battle.roles.get(selected).curY.get();
                    moveRoleLabel(selected,x,y);
                    RoleMoveMessage message=new RoleMoveMessage(selected,x,y); //怎么把main类传过去
                    battle.gameprogress.writeIn(ActionType.MOVE, selected + " " + x +" "+y);
                    calabashClient.send(message);
                }
                else if (key.equals("d")) {
                    battle.roles.get(selected).move(Direction.RIGHT);
                    int x=battle.roles.get(selected).curX.get();
                    int y=battle.roles.get(selected).curY.get();
                    moveRoleLabel(selected,x,y);
                    RoleMoveMessage message=new RoleMoveMessage(selected,x,y);
                    battle.gameprogress.writeIn(ActionType.MOVE, selected + " " + x +" "+y);
                    calabashClient.send(message);
                }
                else if (key.equals("w")) {
                    battle.roles.get(selected).move(Direction.UP);
                    int x=battle.roles.get(selected).curX.get();
                    int y=battle.roles.get(selected).curY.get();
                    moveRoleLabel(selected,x,y);
                    RoleMoveMessage message=new RoleMoveMessage(selected,x,y);
                    battle.gameprogress.writeIn(ActionType.MOVE, selected + " " + x +" "+y);
                    calabashClient.send(message);
                }
                else if (key.equals("s")) {
                    battle.roles.get(selected).move(Direction.DOWN);
                    int x=battle.roles.get(selected).curX.get();
                    int y=battle.roles.get(selected).curY.get();
                    moveRoleLabel(selected,x,y);
                    RoleMoveMessage message=new RoleMoveMessage(selected,x,y);
                    battle.gameprogress.writeIn(ActionType.MOVE, selected + " " + x +" "+y);
                    calabashClient.send(message);
                }
                else if (key.equals("j")){ 
                    Direction dir = Direction.RIGHT;
                    if (battle.roles.get(selected).camp == Camp.MONSTER)
                        dir = Direction.LEFT;
                    int atkid = battle.roles.get(selected).useGnrAtk(dir);
                    AttackMessage message=new AttackMessage(selected,dir);
                    battle.gameprogress.writeIn(ActionType.GNRATK, selected + battle.dir2str(dir));
                    calabashClient.send(message);
                    if (atkid!=-1){
                        System.out.println("攻击" + atkid +"血量为" + battle.roles.get(atkid).HP);
                        if (battle.roles.get(atkid).alive == false){
                           ImageView iv =  picsList.get(atkid + Attributes.deadoffset);
                           iv.setFitHeight(Attributes.gridHeight);
                           iv.setFitWidth(Attributes.gridWidth);
                           labels.get(atkid).setGraphic(iv);
                        }
                    }   
                }
               
            }
        });
       
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Calabash VS Monster");

        primaryStage.setScene(scene);
        primaryStage.show();
        

        canvas.setOnMousePressed((MouseEvent e)->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        canvas.setOnMouseDragged((MouseEvent e)->{
            primaryStage.setX(e.getScreenX() - mouseX);
            primaryStage.setY(e.getScreenY() - mouseY);

        });
       
        startInterface();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

