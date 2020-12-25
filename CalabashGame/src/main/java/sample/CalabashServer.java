package sample;


//import client.client.TankClient;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

//import static client.client.TankClient.GAME_HEIGHT;
//import static client.client.TankClient.GAME_WIDTH;

/**
 * 服务器端
 */
public class CalabashServer extends Frame {

    public static int clientCount = 0;//连接的客户端数量
    public static final int TCP_PORT = 55555;//TCP端口号//TODO：端口号的约定
    public static final int UDP_PORT = 55556;//转发客户端数据的UDP端口号//TODO：端口号的约定
    public static final int TANK_DEAD_UDP_PORT = 55557;//接收客户端坦克死亡的端口号//TODO：端口号的约定
    private List<Client> clients = new ArrayList<>();//客户端集合
    private Image offScreenImage = null;//服务器画布
    private static final int SERVER_HEIGHT = 500;
    private static final int SERVER_WIDTH = 300;

    public static void main(String[] args) {
        CalabashServer calabashServer = new CalabashServer();
        calabashServer.launchFrame();
        calabashServer.start();
    }

    public void start(){
        new Thread(new UDPThread()).start();
        new Thread(new TankDeadUDPThread()).start();//TODO：理解该线程
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(TCP_PORT);//在TCP欢迎套接字上监听客户端连接
            System.out.println("CalabashServer has started...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            Socket clientSocket = null;
            try {
                //TODO：对玩家数量添加限制，大于两个观战or拒绝接入
                clientSocket = serverSocket.accept();//给客户但分配专属TCP套接字
                System.out.println("A client has connected...");
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                int UDP_PORT = in.readInt();//记录客户端UDP端口//TODO：消息格式的约定（协议）
                Client client = new Client(clientSocket.getInetAddress().getHostAddress(), UDP_PORT, clientCount);//创建Client对象
                clients.add(client);//添加进客户端容器

                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                //TODO:要发送哪些消息？
                out.writeInt(clientCount++);//向客户端分配id号
                out.writeInt(CalabashServer.UDP_PORT);//告诉客户端自己的UDP端口号

                //out.writeInt(CalabashServer.TANK_DEAD_UDP_PORT);
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(clientSocket != null) clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class UDPThread implements Runnable{

        byte[] buf = new byte[1024];

        @Override
        public void run() {
            DatagramSocket ds = null;
            try{
                ds = new DatagramSocket(UDP_PORT);
            }catch (SocketException e) {
                e.printStackTrace();
            }

            while (ds!=null){
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                try {
                    ds.receive(dp);
                    System.out.println("receive");
                    for (Client c : clients){
                        dp.setSocketAddress(new InetSocketAddress(c.ip, c.udpPORT));
                        ds.send(dp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 监听坦克死亡的UDP线程
     */
    private class TankDeadUDPThread implements Runnable{
        byte[] buf = new byte[300];
        @Override
        public void run() {
            DatagramSocket ds = null;
            try{
                ds = new DatagramSocket(TANK_DEAD_UDP_PORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            while(null != ds){
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                ByteArrayInputStream bais = null;
                DataInputStream dis = null;
                try{
                    ds.receive(dp);
                    bais = new ByteArrayInputStream(buf, 0, dp.getLength());
                    dis = new DataInputStream(bais);
                    int deadTankUDPPort = dis.readInt();
                    for(int i = 0; i < clients.size(); i++){
                        Client c = clients.get(i);
                        if(c.udpPORT == deadTankUDPPort){
                            clients.remove(c);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (null != dis){
                        try {
                            dis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(null != bais){
                        try {
                            bais.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public class Client{
        String ip;
        int udpPORT;
        int id;

        public Client(String ipAddr, int UDP_PORT, int id) {
            this.ip = ipAddr;
            this.udpPORT = UDP_PORT;
            this.id = id;
        }
    }

    /************** 服务器可视化 **************/
    @Override
    public void paint(Graphics g) {
        g.drawString("TankClient :", 30, 50);
        int y = 80;
        for(int i = 0; i < clients.size(); i++){//显示出每个客户端的信息
            Client c = clients.get(i);
            g.drawString("id : " + c.id + " - IP : " + c.ip, 30, y);
            y += 30;
        }
    }

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(SERVER_WIDTH, SERVER_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.yellow);
        gOffScreen.fillRect(0, 0, SERVER_WIDTH, SERVER_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public void launchFrame() {
        this.setLocation(200, 100);
        this.setSize(SERVER_WIDTH, SERVER_HEIGHT);
        this.setTitle("CalabashServer");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.setBackground(Color.yellow);
        this.setVisible(true);
        new Thread(new PaintThread()).start();

    }

    /**
     * 重画线程
     */
    class PaintThread implements Runnable {
        public void run() {
            while(true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

