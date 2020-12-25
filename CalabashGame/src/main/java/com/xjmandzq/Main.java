
  
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
    }

    void waitStart(){//等待其他玩家进入后开局
        ImageView start = new ImageView(Attributes.images.get(Attributes.START));
        start.setFitHeight(Attributes.height);
        start.setFitWidth(Attributes.width);
        canvas.getChildren().add(start);
        calabashClient.connect("127.0.0.1");
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
                new ImageView(Attributes.images.get(Attributes.MINION))
        );
        ArrayList<ImageView> pics=new ArrayList<>();
        pics.addAll(picsList);
        for(int i=0;i<pics.size();i++){
            pics.get(i).setFitWidth(100);
            pics.get(i).setFitHeight(100);
            //canvas.getChildren().add(pics.get(i));
        }
        //人物的显示
        for(int i=0;i< pics.size();i++){
            Label label=new Label("",pics.get(i));
            label.setLayoutX(Attributes.mapLeft+battle.roles.get(i).curX.get()*Attributes.gridWidth);
            label.setLayoutY(Attributes.mapTop+battle.roles.get(i).curY.get()*Attributes.gridHeight);
            System.out.printf("%d,%d,%d",i,battle.roles.get(i).curX.get(),battle.roles.get(i).curY.get());
            System.out.println();
            labels.add(label);
            canvas.getChildren().add(label);
        }
        //血量条的显示
        for(int i=0;i< pics.size();i++){
            battle.hpbars.get(i).setBar(battle.roles.get(i));
            Label labelbar = new Label("",battle.hpbars.get(i));
            labelbar.setLayoutX(Attributes.mapLeft+battle.roles.get(i).curX.get()*Attributes.gridWidth);
            labelbar.setLayoutY(Attributes.mapTop+battle.roles.get(i).curY.get()*Attributes.gridHeight - 3);
            labels.add(labelbar);
            canvas.getChildren().add(labelbar);
        }

        /*scene.setOnKeyPressed((KeyEvent e) -> {
            changePos(e.getText());
            System.out.println("a");
        });*/
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
                else if (key.equals("a")) {
                    battle.roles.get(selected).move(Direction.LEFT);
                }
                else if (key.equals("d")) {
                    battle.roles.get(selected).move(Direction.RIGHT);
                }
                else if (key.equals("w")) {
                    battle.roles.get(selected).move(Direction.UP);
                }
                else if (key.equals("s")) {
                    battle.roles.get(selected).move(Direction.DOWN);
                }
                else if (key.equals("j")){ //暂时设置为向右攻击
                    int atkid = battle.roles.get(selected).useGnrAtk(Direction.RIGHT);
                    if (atkid!=-1)
                        System.out.println("攻击" + atkid +"血量为" + battle.roles.get(atkid).HP);

                }
                int x=battle.roles.get(selected).curX.get();
                int y=battle.roles.get(selected).curY.get();
                moveRoleLabel(selected,x,y);
                labels.get(selected + 18).setLayoutX(xToPixel(battle.roles.get(selected).curX.get()));
                labels.get(selected + 18).setLayoutY(yToPixel(battle.roles.get(selected).curY.get()) -3 );
                RoleMoveMessage message=new RoleMoveMessage(selected,x,y);
                calabashClient.send(message);
            }
        });
        //响应鼠标，选中人物
        /*canvas.setOnMousePressed((MouseEvent e)->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });*/
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Hello World");

        primaryStage.setScene(scene);
        primaryStage.show();
        //canvas.setStyle("-fx-background-color: black;");
        /*ImageView map = new ImageView(Attributes.images.get(Attributes.MAP));
        map.setFitHeight(Attributes.height);
        map.setFitWidth(Attributes.width);
        //canvas.setPrefSize(500,200);
        canvas.getChildren().add(map);*/

        canvas.setOnMousePressed((MouseEvent e)->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        canvas.setOnMouseDragged((MouseEvent e)->{
            primaryStage.setX(e.getScreenX() - mouseX);
            primaryStage.setY(e.getScreenY() - mouseY);

        });
        /*scene.setOnKeyPressed((KeyEvent e)->{
            if (e.getCode() == KeyCode.A) {
                System.out.println("a");
            }
            System.out.println("a");
        });*/
        startInterface();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

