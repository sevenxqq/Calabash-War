package sample;

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
    Pane canvas=new Pane();
    Scene scene=new Scene(canvas, Attributes.width, Attributes.height);
    double mouseX;
    double mouseY;
    int posX=310;
    int posY=110;
    Battle battle=new Battle();

    @Override
    public void init() throws Exception{
        Attributes.init();
        List<Creature> creaturesList = Arrays.asList(
                new Creature("calabash1"),
                new Creature("calabash2"),
                new Creature("calabash3"),
                new Creature("calabash4"),
                new Creature("calabash5"),
                new Creature("calabash6"),
                new Creature("calabash7"),
                new Creature("grandpa"),
                new Creature("scorpion"),
                new Creature("snake"),
                new Creature("pangolin"),
                new Creature("minion")
        );
        //ArrayList<Creature> creatures = new ArrayList<>();


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
        startLabel.setOnMouseClicked((MouseEvent e)->{ enterBattle(); });
        canvas.getChildren().add(startLabel);
        //退出按钮
        /*canvas.setOnMousePressed((MouseEvent e)->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            System.out.println(mouseX);
        });*/


    }

    /*public int changePos(String key){
        switch(key){
            case "a":
                if (posX>310)
                    posX-=100;
                break;
            case "d":
                posX+=100;

        }
    }*/
    public void enterBattle() {
        //地图
        ImageView map = new ImageView(Attributes.images.get(Attributes.MAP));
        map.setFitHeight(Attributes.height);
        map.setFitWidth(Attributes.width);
        canvas.getChildren().add(map);

        List<ImageView> picsList=Arrays.asList(
                new ImageView(Attributes.images.get(Attributes.CALABASH1)),
                new ImageView(Attributes.images.get(Attributes.CALABASH2)),
                new ImageView(Attributes.images.get(Attributes.CALABASH3)),
                new ImageView(Attributes.images.get(Attributes.CALABASH4)),
                new ImageView(Attributes.images.get(Attributes.CALABASH5)),
                new ImageView(Attributes.images.get(Attributes.CALABASH6)),
                new ImageView(Attributes.images.get(Attributes.CALABASH7)),
                new ImageView(Attributes.images.get(Attributes.GRANDPA)),
                new ImageView(Attributes.images.get(Attributes.SCORPION)),
                new ImageView(Attributes.images.get(Attributes.SNAKE)),
                new ImageView(Attributes.images.get(Attributes.PANGOLIN)),
                new ImageView(Attributes.images.get(Attributes.MINION))
        );
        ArrayList<ImageView> pics=new ArrayList<>();
        pics.addAll(picsList);
        for(int i=0;i<pics.size();i++){
            pics.get(i).setFitWidth(100);
            pics.get(i).setFitHeight(100);
            //canvas.getChildren().add(pics.get(i));
        }
        ArrayList<Label> labels=new ArrayList<>();
        for(int i=0;i< pics.size();i++){
            Label label=new Label("",pics.get(i));
            label.setLayoutX(posX);
            posX+=100;
            System.out.println(posX);
            label.setLayoutY(posY);
            System.out.println(posY);
            labels.add(label);
            canvas.getChildren().add(label);
        }
        ImageView c = new ImageView(Attributes.images.get(Attributes.CALABASH4));
        c.setFitHeight(100);
        c.setFitWidth(100);
        canvas.getChildren().add(c);

        Label startLabel = new Label("", c);
        //startLabel.setMaxSize(200,100);
        startLabel.setLayoutX(posX);
        startLabel.setLayoutY(posY);
        canvas.getChildren().add(startLabel);
        /*scene.setOnKeyPressed((KeyEvent e) -> {
            changePos(e.getText());
            System.out.println("a");
        });*/
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.A) {
                    System.out.println("a");
                    if (posX>Attributes.mapLeft)
                        posX-=Attributes.gridWidth;
                }
                else if (event.getCode() == KeyCode.D) {
                    System.out.println("d");
                    if (posX<Attributes.mapRight)
                        posX+=Attributes.gridWidth;
                }
                else if (event.getCode() == KeyCode.W) {
                    System.out.println("w");
                    if (posY>Attributes.mapTop)
                        posY-=Attributes.gridHeight;
                }
                else if (event.getCode() == KeyCode.S) {
                    System.out.println("s");
                    if (posY<Attributes.mapBottom)
                        posY+=Attributes.gridHeight;
                }
                //startLabel.setLayoutX(posX);
                //startLabel.setLayoutY(posY);
            }
        });
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
