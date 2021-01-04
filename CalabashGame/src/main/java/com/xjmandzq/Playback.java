
package com.xjmandzq;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

class Playback  extends Thread {
// class Playback  {
    String filepath;
    private Document docs;
    private Element fileroot;

    Main play;

    public Playback() {

    }

    public Playback(String path, Main tmpmain) {
        this.filepath = path;
        this.play = tmpmain;
        play.decorateStage();
        System.out.println("playback" + " " + play.labels.size());
        if (filepath == null)
            return;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            docs = db.parse(filepath);
            fileroot = docs.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parse() {
        if (docs == null)
            return;

        NodeList rounds = fileroot.getChildNodes();
        for (int i = 1; i < rounds.getLength(); i++) {
            NodeList battle = rounds.item(i).getChildNodes();
            for (int j = 0; j < battle.getLength(); j++) {
                Node action = battle.item(j);
                String acttype = action.getNodeName();
                switch (acttype) {
                    case "move":
                        parseMove(action);
                        break;
                    case "gnrAtk":
                        parseGnrAtk(action);
                        break;
                    case "heal":
                        parseHeal(action);
                        break;
                    case "mgcAtk":
                        parseMgcAtk(action);
                        break;
                    default:
                        break;
                }
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        }
    }

    private void parseMove(Node action) {
        int Chatid = Integer.parseInt(action.getAttributes().getNamedItem("ChatId").getNodeValue());
        int x = Integer.parseInt(action.getAttributes().getNamedItem("x").getNodeValue());
        int y = Integer.parseInt(action.getAttributes().getNamedItem("y").getNodeValue());
        boolean canmove = play.battle.roles.get(Chatid).move(x, y);
        if (canmove){
            play.moveRoleLabel(Chatid, x, y);  
        }
    }

    private void parseGnrAtk(Node action) {
        int Chatid = Integer.parseInt(action.getAttributes().getNamedItem("ChatId").getNodeValue());
        String direction = action.getAttributes().getNamedItem("dir").getNodeValue();
        Direction dir;
        switch (direction) {
            case "UP":
                dir = Direction.UP;
                break;
            case "DOWN":
                dir = Direction.DOWN;
                break;
            case "LEFT":
                dir = Direction.LEFT;
                break;
            case "RIGHT":
                dir = Direction.RIGHT;
                break;
            default:
                return;
        }
        int atkid = play.battle.roles.get(Chatid).useGnrAtk(dir);
        if (atkid != -1) {
            // System.out.println("攻击" + atkid + "血量为" + play.battle.roles.get(atkid).HP);
            if (play.battle.roles.get(atkid).alive == false) {
                play.setDead(atkid); 
            }
        }
    }

    private void parseMgcAtk(Node action) {
        int Chatid = Integer.parseInt(action.getAttributes().getNamedItem("ChatId").getNodeValue());
        String direction = action.getAttributes().getNamedItem("dir").getNodeValue();
        Direction dir;
        switch (direction) {
            case "UP":
                dir = Direction.UP;
                break;
            case "DOWN":
                dir = Direction.DOWN;
                break;
            case "LEFT":
                dir = Direction.LEFT;
                break;
            case "RIGHT":
                dir = Direction.RIGHT;
                break;
            default:
                return;
        }
        play.battle.roles.get(Chatid).useMgcAtk(dir);
        play.afterMgc(Chatid, dir);
       
    }


    private void parseHeal(Node action) {
        int Chatid = Integer.parseInt(action.getAttributes().getNamedItem("ChatId").getNodeValue());
        String direction = action.getAttributes().getNamedItem("dir").getNodeValue();
        Direction dir;
        switch (direction) {
            case "UP":
                dir = Direction.UP;
                break;
            case "DOWN":
                dir = Direction.DOWN;
                break;
            case "LEFT":
                dir = Direction.LEFT;
                break;
            case "RIGHT":
                dir = Direction.RIGHT;
                break;
            default:
                return;
        }
        play.battle.roles.get(Chatid).useHealing(dir);

    }

    @Override
    public void run() {
        System.out.println("begin playback!");
        parse();
        System.out.println("playback done!");
    }

}