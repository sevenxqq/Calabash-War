
package com.xjmandzq;

import java.io.File;
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
    Pane canvas = new Pane();
    Stage stage = new Stage();
    Scene scene = new Scene(canvas, Attributes.width, Attributes.height);
    double mouseX;
    double mouseY;
    CalabashClient calabashClient = new CalabashClient(this);
    Battle battle = new Battle();
    List<ImageView> picsList;
    ArrayList<Label> labels = new ArrayList<>();

    @Override
    public void init() throws Exception {
        Attributes.init();
    }

    public void startInterface() {
        // 背景
        ImageView start = new ImageView(Attributes.images.get(Attributes.START));
        start.setFitHeight(Attributes.height);
        start.setFitWidth(Attributes.width);
        canvas.getChildren().add(start);
        // 开始游戏按钮
        ImageView startGame = new ImageView(Attributes.images.get(Attributes.STARTICON));
        startGame.setFitHeight(100);
        startGame.setFitWidth(200);
        Label startLabel = new Label("", startGame);
        startLabel.setMaxSize(200, 100);
        startLabel.setLayoutX(550);
        startLabel.setLayoutY(550);
        startLabel.setOnMouseClicked((MouseEvent e) -> {
            waitStart();
        });
        canvas.getChildren().add(startLabel);
        // 退出按钮
        /*
         * canvas.setOnMousePressed((MouseEvent e)->{ mouseX = e.getSceneX(); mouseY =
         * e.getSceneY(); System.out.println(mouseX); });
         */
        // 读取进度按钮
        ImageView loadfile = new ImageView(Attributes.images.get(Attributes.LOADICON));
        loadfile.setFitHeight(100);
        loadfile.setFitWidth(200);
        Label loadLabel = new Label("", loadfile);
        loadLabel.setMaxSize(200, 100);
        loadLabel.setLayoutX(360);
        loadLabel.setLayoutY(360);
        System.out.println("add load label");
        loadLabel.setOnMouseClicked((MouseEvent e) -> {
            try {
                loadProgress();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        canvas.getChildren().add(loadLabel);

    }

    int xToPixel(int x) {// 地图格X坐标转换为像素横坐标
        return Attributes.mapLeft + x * Attributes.gridWidth;
    }

    int yToPixel(int y) {// 地图格Y坐标转换为像素纵坐标
        return Attributes.mapTop + y * Attributes.gridHeight;
    }

    void moveRoleLabel(int id, int x, int y) {
        labels.get(id).setLayoutX(xToPixel(x));
        labels.get(id).setLayoutY(yToPixel(y));
        labels.get(id + Attributes.hpoffset).setLayoutX(xToPixel(x));
        labels.get(id + Attributes.hpoffset).setLayoutY(yToPixel(y));
    }

    void waitStart() {// 等待其他玩家进入后开局
        ImageView start = new ImageView(Attributes.images.get(Attributes.START));
        start.setFitHeight(Attributes.height);
        start.setFitWidth(Attributes.width);
        canvas.getChildren().add(start);
        calabashClient.connect(Attributes.serverIP);
        enterBattle();
    }

    void loadProgress() throws InterruptedException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            decorateStage();
            Playback autoplay = new Playback(file.getPath(), this);
            new Thread(autoplay).start();
        }
        // 回放完成后是否回到主界面

    }

    void decorateStage() {
        // 显示地图
        ImageView map = new ImageView(Attributes.images.get(Attributes.MAP));
        map.setFitHeight(Attributes.height);
        map.setFitWidth(Attributes.width);
        canvas.getChildren().add(map);

        // 加载角色图片
        picsList = Arrays.asList(new ImageView(Attributes.images.get(Attributes.CALABASH1)),
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
                // 18,不加入新的label,用于替换
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
                new ImageView(Attributes.images.get(Attributes.DEADMINION)));
        ArrayList<ImageView> pics = new ArrayList<>();
        pics.addAll(picsList);
        for (int i = 0; i < 2 * Attributes.rolesNum; i++) {
            pics.get(i).setFitWidth(100);
            pics.get(i).setFitHeight(100);

        }
        // 人物的显示
        for (int i = 0; i < 2 * Attributes.rolesNum; i++) {
            Label label = new Label("", pics.get(i));
            label.setLayoutX(Attributes.mapLeft + battle.roles.get(i).curX.get() * Attributes.gridWidth);
            label.setLayoutY(Attributes.mapTop + battle.roles.get(i).curY.get() * Attributes.gridHeight);
            System.out.printf("%d,%d,%d", i, battle.roles.get(i).curX.get(), battle.roles.get(i).curY.get());
            System.out.println();
            labels.add(label);
            canvas.getChildren().add(label);
        }
        // 血量条的显示
        for (int i = 0; i < 2 * Attributes.rolesNum; i++) {
            battle.hpbars.get(i).setBar(battle.roles.get(i));
            Label labelbar = new Label("", battle.hpbars.get(i));
            labelbar.setLayoutX(Attributes.mapLeft + battle.roles.get(i).curX.get() * Attributes.gridWidth);
            labelbar.setLayoutY(Attributes.mapTop + battle.roles.get(i).curY.get() * Attributes.gridHeight - 3);
            labels.add(labelbar);
            canvas.getChildren().add(labelbar);
        }
        System.out.println("show 地图");

    }

    public void enterBattle() {

        decorateStage();

        if (battle.myCamp == Camp.MONSTER)
            battle.selected = 9;
        else
            battle.selected = 0;

        // 响应键盘，控制角色移动和攻击
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int selected = battle.selected;
                String key = event.getText();
                if (key.length() > 0 && key.charAt(0) >= '1' && key.charAt(0) <= '9') {
                    selected = key.charAt(0) - '1';
                    if (battle.myCamp == Camp.MONSTER)
                        selected += 9;
                    battle.selected = selected;
                    System.out.println(selected);
                }
                if (battle.roles.get(selected).alive == false)
                    return;
                if (key.equals("a")) {
                    battle.roles.get(selected).move(Direction.LEFT);
                    int x = battle.roles.get(selected).curX.get();
                    int y = battle.roles.get(selected).curY.get();
                    moveRoleLabel(selected, x, y);
                    RoleMoveMessage message = new RoleMoveMessage(selected, x, y); // 之后全改在client里
                    battle.gameprogress.writeIn(ActionType.MOVE, selected + " " + x + " " + y);
                    calabashClient.send(message);
                } else if (key.equals("d")) {
                    battle.roles.get(selected).move(Direction.RIGHT);
                    int x = battle.roles.get(selected).curX.get();
                    int y = battle.roles.get(selected).curY.get();
                    moveRoleLabel(selected, x, y);
                    RoleMoveMessage message = new RoleMoveMessage(selected, x, y);
                    battle.gameprogress.writeIn(ActionType.MOVE, selected + " " + x + " " + y);
                    calabashClient.send(message);
                } else if (key.equals("w")) {
                    battle.roles.get(selected).move(Direction.UP);
                    int x = battle.roles.get(selected).curX.get();
                    int y = battle.roles.get(selected).curY.get();
                    moveRoleLabel(selected, x, y);
                    RoleMoveMessage message = new RoleMoveMessage(selected, x, y);
                    battle.gameprogress.writeIn(ActionType.MOVE, selected + " " + x + " " + y);
                    calabashClient.send(message);
                } else if (key.equals("s")) {
                    battle.roles.get(selected).move(Direction.DOWN);
                    int x = battle.roles.get(selected).curX.get();
                    int y = battle.roles.get(selected).curY.get();
                    moveRoleLabel(selected, x, y);
                    RoleMoveMessage message = new RoleMoveMessage(selected, x, y);
                    battle.gameprogress.writeIn(ActionType.MOVE, selected + " " + x + " " + y);
                    calabashClient.send(message);
                } else if (key.equals("j")) {
                    Direction dir = Direction.RIGHT;
                    if (battle.roles.get(selected).camp == Camp.MONSTER)
                        dir = Direction.LEFT;
                    if (battle.roles.get(selected).healer == false) {
                        int atkid = battle.roles.get(selected).useGnrAtk(dir);
                        AttackMessage message = new AttackMessage(selected, dir);
                        battle.gameprogress.writeIn(ActionType.GNRATK, selected + " " + battle.dir2str(dir));
                        calabashClient.send(message);
                        if (atkid != -1) {
                            System.out.println("攻击" + atkid + "血量为" + battle.roles.get(atkid).HP);
                            if (battle.roles.get(atkid).alive == false) {
                                ImageView iv = picsList.get(atkid + Attributes.deadoffset);
                                iv.setFitHeight(Attributes.gridHeight);
                                iv.setFitWidth(Attributes.gridWidth);
                                labels.get(atkid).setGraphic(iv);
                            }
                        }
                    } else {// 治愈者无攻击能力，按j使用的是治愈术
                        int healid =battle.roles.get(selected).useHealing(dir);
                        AttackMessage message = new AttackMessage(selected, dir);
                        battle.gameprogress.writeIn(ActionType.HEAL, selected + " " + battle.dir2str(dir));
                        calabashClient.send(message);
                        if (healid != -1) {
                            System.out.println("治愈" + healid + "血量为" + battle.roles.get(healid).HP);
                        }
                    }
                }

            }
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Calabash VS Monster");

        primaryStage.setScene(scene);
        primaryStage.show();

        this.stage = primaryStage;
        canvas.setOnMousePressed((MouseEvent e) -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        canvas.setOnMouseDragged((MouseEvent e) -> {
            primaryStage.setX(e.getScreenX() - mouseX);
            primaryStage.setY(e.getScreenY() - mouseY);

        });

        startInterface();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
