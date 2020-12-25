
package com.xjmandzq;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 应用层协议接口
 */
public interface Message {
    public static final int GAME_START = 1;
    public static final int ROLE_MOVE= 2;
    public static final int ATTACK = 3;
    public static final int TANK_DEAD_Message = 4;
    public static final int MISSILE_DEAD_Message = 5;
    public static final int TANK_ALREADY_EXIST_Message = 6;
    public static final int TANK_REDUCE_BLOOD_Message = 7;

    public void send(DatagramSocket ds, String ip, int udpPort);
    public void parse(DataInputStream in);
}

/*
        import client.bean.Direction;
        import client.bean.Tank;
        import client.client.TankClient;
        import java.io.ByteArrayOutputStream;
        import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.net.DatagramPacket;
        import java.net.DatagramSocket;
        import java.net.InetSocketAddress;
*/

/**
 * 坦克移动消息协议
 */
class RoleMoveMessage implements Message {
    private int type = Message.ROLE_MOVE;
    private int id;
    private int x, y;
    Main player;

    public RoleMoveMessage(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public RoleMoveMessage(Main player){
        this.player=player;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);//指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(id);
            out.writeInt(x);
            out.writeInt(y);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try{
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
            System.out.println("send");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try{
            int id = in.readInt();
            int x = in.readInt();
            int y = in.readInt();
            player.battle.roles.get(id).move(x,y);
            player.moveRoleLabel(id,x,y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class AttackMessage implements Message {
    private int type = Message.ATTACK;
    private int id;
    Main player;

    public AttackMessage(int id){
        this.id = id;
    }

    public AttackMessage(Main player){
        this.player=player;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);//指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try{
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
            System.out.println("send");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try{
            int id = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class GameStartMessage implements Message {
    private int type = Message.GAME_START;
    private int id;//TODO
    Main player;

    public GameStartMessage(int id){
        this.id=id;
    }
    public GameStartMessage(Main player){
        this.player=player;
    }


    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);//指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try{
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
            System.out.println("send");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try{
            int id = in.readInt();
            player.battle.enemyId=id;
            player.battle.started=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

