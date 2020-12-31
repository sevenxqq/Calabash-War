package sample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;


public class Main extends Application {
    int myID;//自己的id
    int matchPlayerID=-1;//想匹配的玩家ID
    int enemyID=-1;//
    boolean started=false;
    String serverIP;//服务器ip地址
    List<Player> players=new ArrayList<>();//当前与服务器连接的可匹配玩家

    Pane canvas=new Pane();//
    Scene scene=new Scene(canvas, Attributes.width, Attributes.height);//
    ArrayList<ImageView> pics=new ArrayList<>();
    ArrayList<ImageView> picsDead=new ArrayList<>();
    double mouseX;
    double mouseY;
    CalabashClient calabashClient;//客户端
    Battle battle=new Battle();//一场对局
    ArrayList<Label> labels=new ArrayList<>();//???
    @Override
    public void init() throws Exception{
        Attributes.init();

    }


    //step1:连接服务器
    void connectServer(int flag){
        TextInputDialog dialog = new TextInputDialog(Attributes.localServerIP);
        dialog.setTitle("连接服务器");
        if(flag==0){
            dialog.setHeaderText("输入服务器IP地址后，按“确定”键连接服务器");
        }
        else{
            dialog.setHeaderText("服务器连接失败! 请检查服务器是否正在运行及服务器IP地址是否正确后重新输入");
        }
        dialog.setContentText("服务器IP地址：");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            serverIP=result.get();
            calabashClient=new CalabashClient(this);//客户端
            if(calabashClient.connect(serverIP)){
                startInterface();
            }
            else{
                connectServer(1);
            }
        }
    }

    //step2：选择开始游戏or回放or退出
    public void startInterface() {
        //TODO:背景图片的制作，添加键盘操作，添加回放功能
        //欢迎界面背景图片
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
        startLabel.setOnMouseClicked((MouseEvent e)->{ matchPlayer(); });
        canvas.getChildren().add(startLabel);
        //退出按钮
        ImageView exitImage = new ImageView(Attributes.images.get(Attributes.EXITICON));
        exitImage.setFitHeight(100);
        exitImage.setFitWidth(200);
        Label exitLabel = new Label("",exitImage);
        exitLabel.setMaxSize(200,100);
        exitLabel.setLayoutX(850);
        exitLabel.setLayoutY(550);
        exitLabel.setOnMouseClicked((MouseEvent e)->{ System.exit(0); });
        canvas.getChildren().add(exitLabel);
        /*canvas.setOnMousePressed((MouseEvent e)->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            System.out.println(mouseX);
        });*/

    }

    //step3-1：选择开始游戏后匹配玩家
    void matchPlayer(){
        List<String> choices = new ArrayList<>();
        for(Player p: players){
            System.out.println(p.id);
            choices.add(Integer.toString(p.id));
        }
        String defaultPlayer;
        if(choices.size()>0)
            defaultPlayer=choices.get(0);
        else
            defaultPlayer="--";
        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaultPlayer, choices);
        dialog.setTitle("匹配玩家");
        dialog.setHeaderText("选择当前在线玩家进行战斗吧！若想刷新新玩家请点击“取消”后再按“START“");
        dialog.setContentText("请选择想匹配的玩家id：");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && result.get()!="--"){
            matchPlayerID=Integer.parseInt(result.get());
            InviteMessage message=new InviteMessage(myID,matchPlayerID);
            calabashClient.send(message);
            /*
            enemyID=matchPlayerID;
            started=true;
             */
        }
        if (started==true)
            enterBattle();

        // The Java 8 way to get the response value (with lambda expression).
        //result.ifPresent(letter -> System.out.println("Your choice: " + letter));

    }

    void processInvitation(int playerID){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("玩家邀请");
        alert.setHeaderText("玩家"+Integer.toString(playerID)+"邀请您加入战斗");
        alert.setContentText("是否同意邀请？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            enemyID=playerID;
            battle=new Battle();
            battle.setCamp(Camp.MONSTER);
            started=true;
            enterBattle();
            ReplyMessage r=new ReplyMessage(myID,playerID,1);
            calabashClient.send(r);

        } else {
            ReplyMessage r=new ReplyMessage(myID,playerID,0);
            calabashClient.send(r);
        }

    }

    void invitationRefused(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("匹配失败");
        alert.setHeaderText(null);
        alert.setContentText("玩家"+Integer.toString(matchPlayerID)+"拒绝了您的邀请，请重新匹配！");
        alert.showAndWait();
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
        labels.get(id + 18).setLayoutX(xToPixel(x));
        labels.get(id + 18).setLayoutY(yToPixel(y)-3);
    }

    void waitStart(){//等待其他玩家进入后开局
        ImageView start = new ImageView(Attributes.images.get(Attributes.START));
        start.setFitHeight(Attributes.height);
        start.setFitWidth(Attributes.width);
        canvas.getChildren().add(start);
        //calabashClient.connect(Attributes.localServerIP);
        enterBattle();
    }

    //step3-2：匹配玩家后进入战斗
    public void enterBattle() {
        battle.enemyId=enemyID;
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

        pics.addAll(picsList);

        List<ImageView> picsListDead=Arrays.asList(
                new ImageView(Attributes.images.get(Attributes.CALABASH1_DEAD)),
                new ImageView(Attributes.images.get(Attributes.CALABASH2_DEAD)),
                new ImageView(Attributes.images.get(Attributes.CALABASH3_DEAD)),
                new ImageView(Attributes.images.get(Attributes.CALABASH4_DEAD)),
                new ImageView(Attributes.images.get(Attributes.CALABASH5_DEAD)),
                new ImageView(Attributes.images.get(Attributes.CALABASH6_DEAD)),
                new ImageView(Attributes.images.get(Attributes.CALABASH7_DEAD)),
                new ImageView(Attributes.images.get(Attributes.GRANDPA_DEAD)),
                new ImageView(Attributes.images.get(Attributes.PANGOLIN_DEAD)),
                new ImageView(Attributes.images.get(Attributes.SCORPION_DEAD)),
                new ImageView(Attributes.images.get(Attributes.SNAKE_DEAD)),
                new ImageView(Attributes.images.get(Attributes.MINION_DEAD)),
                new ImageView(Attributes.images.get(Attributes.MINION_DEAD)),
                new ImageView(Attributes.images.get(Attributes.MINION_DEAD)),
                new ImageView(Attributes.images.get(Attributes.MINION_DEAD)),
                new ImageView(Attributes.images.get(Attributes.MINION_DEAD)),
                new ImageView(Attributes.images.get(Attributes.MINION_DEAD)),
                new ImageView(Attributes.images.get(Attributes.MINION_DEAD))
        );
        //ArrayList<ImageView> picsDead=new ArrayList<>();
        picsDead.addAll(picsListDead);
        for(int i=0;i<pics.size();i++){
            pics.get(i).setFitWidth(100);
            pics.get(i).setFitHeight(100);
            picsDead.get(i).setFitHeight(100);
            picsDead.get(i).setFitWidth(100);
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
                    Direction dir;
                    if(battle.myCamp==Camp.CALABASH)
                        dir=Direction.RIGHT;
                    else
                        dir=Direction.LEFT;
                    int atkid = battle.roles.get(selected).useGnrAtk(dir);
                    if (atkid!=-1) {
                        System.out.println("攻击" + atkid + "血量为" + battle.roles.get(atkid).HP);
                        AttackMessage message = new AttackMessage(enemyID,selected,dir);
                        calabashClient.send(message);
                        if(battle.roles.get(atkid).HP<=0){//人物生命值耗尽
                            battle.enemyDeadCount++;
                            setDead(atkid);
                            if(battle.enemyDeadCount==Attributes.rolesNum){
                                gameOver(true);
                            }
                        }
                    }
                }
                int x=battle.roles.get(selected).curX.get();
                int y=battle.roles.get(selected).curY.get();
                moveRoleLabel(selected,x,y);
                labels.get(selected + 18).setLayoutX(xToPixel(battle.roles.get(selected).curX.get()));
                labels.get(selected + 18).setLayoutY(yToPixel(battle.roles.get(selected).curY.get()) -3 );
                RoleMoveMessage message=new RoleMoveMessage(enemyID,selected,x,y);
                calabashClient.send(message);
            }
        });
        //响应鼠标，选中人物
        /*canvas.setOnMousePressed((MouseEvent e)->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });*/
    }

    //step3-3:游戏结束
    void gameOver(boolean winFlag){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("对战结果");
        alert.setHeaderText(null);
        if(winFlag){
            alert.setContentText("恭喜您，取得胜利!");
        }
        else{
            alert.setContentText("很遗憾，对局失败...");
        }

        labels.clear();
        pics.clear();
        picsDead.clear();

        started=false;
        alert.showAndWait();
        startInterface();
    }

    void setDead(int roleID){
        labels.get(roleID).setGraphic(picsDead.get(roleID));
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
        //startInterface();
        connectServer(0);
    }



    public static void main(String[] args) {
        launch(args);
    }


}