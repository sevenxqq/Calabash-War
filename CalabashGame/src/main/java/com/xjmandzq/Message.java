
package com.xjmandzq;

import javafx.application.Platform;

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
    public static final int ROLE_MOVE = 2;
    public static final int ATTACK = 3;
    static final int NEW_PLAYER = 4;
    static final int INVITATION = 5;
    static final int REPLY = 6;
    static final int MGCATTACK = 7;
    static final int HEAL = 8;
    public static final int TANK_DEAD_Message = 4;
    public static final int MISSILE_DEAD_Message = 5;
    public static final int TANK_ALREADY_EXIST_Message = 6;
    public static final int TANK_REDUCE_BLOOD_Message = 7;

    public void send(DatagramSocket ds, String ip, int udpPort);

    public void parse(DataInputStream in);
}

/*
 * import client.bean.Direction; import client.bean.Tank; import
 * client.client.TankClient; import java.io.ByteArrayOutputStream; import
 * java.io.DataInputStream; import java.io.DataOutputStream; import
 * java.io.IOException; import java.net.DatagramPacket; import
 * java.net.DatagramSocket; import java.net.InetSocketAddress;
 */

/**
 * 坦克移动消息协议
 */
class RoleMoveMessage implements Message {
    private int type = Message.ROLE_MOVE;
    private int targetClientID;
    private int id;
    private int x, y;
    Main player;

    public RoleMoveMessage(int targetClientID, int id, int x, int y) {
        this.targetClientID = targetClientID;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public RoleMoveMessage(Main player) {
        this.player = player;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);// 指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(targetClientID);
            out.writeInt(id);
            out.writeInt(x);
            out.writeInt(y);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
            // System.out.println("client send");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try {
            int targetClientID = in.readInt();
            int id = in.readInt();
            int x = in.readInt();
            int y = in.readInt();
            if (targetClientID == player.myID) {
                player.battle.gameprogress.writeIn(ActionType.MOVE, id + " " + x + " " + y);
                player.battle.roles.get(id).move(x, y);
                player.moveRoleLabel(id, x, y);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class AttackMessage implements Message {
    private int type = Message.ATTACK;
    private int targetClientID;
    private int id;
    private Direction dir;
    Main player;

    public AttackMessage(int targetClientID, int id, Direction dir) {
        this.targetClientID = targetClientID;
        this.id = id;
        this.dir = dir;
    }

    public AttackMessage(Main player) {
        this.player = player;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);// 指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(targetClientID);
            out.writeInt(id);
            int dirInt = -1;
            switch (dir) {
                case UP:
                    dirInt = 0;
                    break;
                case DOWN:
                    dirInt = 1;
                    break;
                case LEFT:
                    dirInt = 2;
                    break;
                case RIGHT:
                    dirInt = 3;
                    break;
            }
            out.writeInt(dirInt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try {
            int targetClientID = in.readInt();
            int id = in.readInt();
            int dir = in.readInt();
            if (targetClientID == player.myID) {
                Direction direction = Direction.RIGHT;
                switch (dir) {
                    case 0:
                        direction = Direction.UP;
                        break;
                    case 1:
                        direction = Direction.DOWN;
                        break;
                    case 2:
                        direction = Direction.LEFT;
                        break;
                    case 3:
                        direction = Direction.RIGHT;
                        break;
                }
                int atkid = player.battle.roles.get(id).useGnrAtk(direction);
                player.battle.gameprogress.writeIn(ActionType.GNRATK, id + " " + player.battle.dir2str(direction));
                if (atkid != -1) {
                    System.out.println("HP:" + player.battle.roles.get(atkid).HP);
                    if (player.battle.roles.get(atkid).HP <= 0) {
                        player.battle.myDeadCount++;
                        Platform.runLater(() -> {
                            player.setDead(atkid);
                        });
                        if (player.battle.myDeadCount == Attributes.rolesNum) {
                            Platform.runLater(() -> {
                                player.gameOver(false);
                            });
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MgcAttackMessage implements Message {
    private int type = Message.MGCATTACK;
    private int targetClientID;
    private int id;
    private Direction dir;
    Main player;

    public MgcAttackMessage(int targetClientID, int id, Direction dir) {
        this.targetClientID = targetClientID;
        this.id = id;
        this.dir = dir;
    }

    public MgcAttackMessage(Main player) {
        this.player = player;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);// 指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(targetClientID);
            out.writeInt(id);
            int dirInt = -1;
            switch (dir) {
                case UP:
                    dirInt = 0;
                    break;
                case DOWN:
                    dirInt = 1;
                    break;
                case LEFT:
                    dirInt = 2;
                    break;
                case RIGHT:
                    dirInt = 3;
                    break;
            }
            out.writeInt(dirInt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try {
            int targetClientID = in.readInt();
            int id = in.readInt();
            int dir = in.readInt();
            if (targetClientID == player.myID) {
                Direction direction = Direction.RIGHT;
                switch (dir) {
                    case 0:
                        direction = Direction.UP;
                        break;
                    case 1:
                        direction = Direction.DOWN;
                        break;
                    case 2:
                        direction = Direction.LEFT;
                        break;
                    case 3:
                        direction = Direction.RIGHT;
                        break;
                }
                player.battle.roles.get(id).useMgcAtk(direction);
                player.battle.gameprogress.writeIn(ActionType.MGCATK, id + " " + player.battle.dir2str(direction));
                int beginid = 0,endid = 8,deadlife = 0;
                if (player.battle.myCamp == Camp.MONSTER){
                    beginid = 9;
                    endid = 17;
                }
                for(int i = beginid;i<=endid;i++){
                    if (player.battle.roles.get(i).HP <= 0) {
                        final int deadid = i;
                        deadlife++;
                        Platform.runLater(() -> {
                            player.setDead(deadid);
                        });
                        player.battle.myDeadCount = deadlife;
                        if (player.battle.myDeadCount == Attributes.rolesNum) {
                            Platform.runLater(() -> {
                                player.gameOver(false);
                            });
                        }
                    }
                }
                
           
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


///

class HealMessage implements Message {
    private int type = Message.HEAL;
    private int targetClientID;
    private int id;
    private Direction dir;
    Main player;

    public HealMessage(int targetClientID, int id, Direction dir) {
        this.targetClientID = targetClientID;
        this.id = id;
        this.dir = dir;
    }

    public HealMessage(Main player) {
        this.player = player;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);// 指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(targetClientID);
            out.writeInt(id);
            int dirInt = -1;
            switch (dir) {
                case UP:
                    dirInt = 0;
                    break;
                case DOWN:
                    dirInt = 1;
                    break;
                case LEFT:
                    dirInt = 2;
                    break;
                case RIGHT:
                    dirInt = 3;
                    break;
            }
            out.writeInt(dirInt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try {
            int targetClientID = in.readInt();
            int id = in.readInt();
            int dir = in.readInt();
            if (targetClientID == player.myID) {
                Direction direction = Direction.RIGHT;
                switch (dir) {
                    case 0:
                        direction = Direction.UP;
                        break;
                    case 1:
                        direction = Direction.DOWN;
                        break;
                    case 2:
                        direction = Direction.LEFT;
                        break;
                    case 3:
                        direction = Direction.RIGHT;
                        break;
                }
                player.battle.roles.get(id).useHealing(direction);
                player.battle.gameprogress.writeIn(ActionType.HEAL, id + " " + player.battle.dir2str(direction));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
////

class NewPlayerMessage implements Message {
    private int type = Message.NEW_PLAYER;
    private int playerID;
    // private int playerName;
    private Main player;

    public NewPlayerMessage(int playerID) {
        this.playerID = playerID;
        // this.playerName=playerName;
    }

    public NewPlayerMessage(Main player) {
        this.player = player;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);// 指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(playerID);
            // out.writeInt(playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try {
            int playerID = in.readInt();
            System.out.println(playerID);
            Player newPlayer = new Player(playerID);
            player.players.add(newPlayer);
            System.out.println("add new player");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class InviteMessage implements Message {
    private int type = Message.INVITATION;
    private int player1;
    private int player2;// TODO
    Main player;

    public InviteMessage(int id1, int id2) {
        this.player1 = id1;
        this.player2 = id2;
    }

    public InviteMessage(Main player) {
        this.player = player;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);// 指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(player1);
            out.writeInt(player2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
            System.out.println("send");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try {
            int id1 = in.readInt();
            int id2 = in.readInt();
            if (id2 == player.myID) {// 被邀请
                System.out.println("被邀请");
                Platform.runLater(() -> {
                    player.processInvitation(id1);
                });
                // player.started=true;
                // player.processInvitation(id1);
            }
            // player.battle.enemyId=id;
            // player.battle.started=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ReplyMessage implements Message {
    private int type = Message.REPLY;
    private int player1;
    private int player2;
    private int reply;
    Main player;

    public ReplyMessage(int id1, int id2, int reply) {
        this.player1 = id1;
        this.player2 = id2;
        this.reply = reply;
    }

    public ReplyMessage(Main player) {
        this.player = player;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(30);// 指定大小, 免得字节数组扩容占用时间//TODO
        DataOutputStream out = new DataOutputStream(bout);
        try {
            out.writeInt(type);
            out.writeInt(player1);
            out.writeInt(player2);
            out.writeInt(reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = bout.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpPort));
            ds.send(dp);
            System.out.println("send");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream in) {
        try {
            int id1 = in.readInt();
            int id2 = in.readInt();
            int r = in.readInt();
            System.out.println("收到回复");
            if (id2 == player.myID) {// 被回复
                System.out.println("收到给我的回复");
                System.out.println(r);
                if (r == 1) {// 对方同意邀请
                    player.enemyID = id1;
                    player.battle = new Battle();
                    player.battle.setCamp(Camp.CALABASH);
                    player.started = true;

                    Platform.runLater(() -> {
                        player.enterBattle();
                    });
                } else {
                    Platform.runLater(() -> {
                        player.invitationRefused();
                    });
                }
            }
            // player.battle.enemyId=id;
            // player.battle.started=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
