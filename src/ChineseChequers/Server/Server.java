package ChineseChequers.Server;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server extends JFrame {
    private JTextArea textArea;
    private JTextArea IPTextArea;
    private JTextArea PortTestArea;
    private JPanel metaPanel;
    private DataInputStream inputFromClient[] = new DataInputStream[2];
    private DataOutputStream outputToClient[] = new DataOutputStream[2];
    private ServerSocket serverSocket;
    private Socket socket;

    private int current_player = 1;
    private int player_number = 2;
    private static Lock lock = new ReentrantLock(); // 创建一把进程锁

    public Server(int portNum) {
        // GUI相关
        setTitle("Server");
        add(metaPanel);
        setVisible(true);
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            // 端口
            serverSocket = new ServerSocket(portNum);  // 监听portNum
            textArea.append("Server started at " + new Date() + '\n');
            PortTestArea.append(String.valueOf(portNum));
            // IP地址


            int clientNo = 1;  //clientNUM
            while (clientNo <= player_number) {  //循环接受client
                Socket socket = serverSocket.accept();

                textArea.append("Starting thread for client " + clientNo + " at " + new Date() + '\n');

                // 获得主机名和IP地址
                InetAddress inetAddress = socket.getInetAddress();
                textArea.append("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
                textArea.append("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");

                //连接输入输出
                inputFromClient[clientNo - 1] = new DataInputStream(socket.getInputStream());
                outputToClient[clientNo - 1] = new DataOutputStream(socket.getOutputStream());

                // 使用多线程来处理，每个线程处理一个client
                HandleAClient task = new HandleAClient(socket, clientNo);

                // 开启多线程
                new Thread(task).start();

                clientNo++; //注意++
            }

            send("START# "); // 向两位玩家告知游戏开始
            outputToClient[0].writeInt(1);
            outputToClient[0].flush();
            outputToClient[1].writeInt(2);
            outputToClient[1].flush();
            textArea.append("游戏开始\n");

            // 利用多线程处理各种指令
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public void send(String content) {
        try {
            for (int i = 1; i <= player_number; i++) {
                outputToClient[i - 1].writeUTF(content);
                outputToClient[i - 1].flush();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    public String receive(int clientNo) {
        try {
            String receiveLine = inputFromClient[clientNo - 1].readUTF();
            StringTokenizer strtk = new StringTokenizer(receiveLine, "#");
            int count = strtk.countTokens();
            String op = strtk.nextToken();

            if (op.equals("CHAT")) { // 聊天指令，将其传递给所有玩家
                if (count < 2) return "WRONG";   //如果没有聊天内容，则报错

                String content = strtk.nextToken();
                textArea.append("Client " + clientNo + " says: " + content);
                //send(receiveLine);
                //将消息传给另一位玩家
                int next_player = clientNo + 1;
                if (next_player == player_number + 1) next_player = 1;
                outputToClient[next_player - 1].writeUTF(receiveLine);
                outputToClient[next_player - 1].flush();

                return "CHAT";
            } else if (op.equals("START")) {   // 游戏开始
                return "START";
            } else if (op.equals("FAIL")) {    // 游戏结束
                System.exit(1);
                return "FAIL";
            } else if (op.equals("MOVE")) {    // 移动棋子，可能需要上锁
                lock.lock();    // 由于MOVE要严格保持先后顺序，因此需要上锁
                if (clientNo != current_player) {    // 如果移动棋子的不是应该移动的玩家
                    textArea.append("Client " + clientNo + ": UNSUCCESSFULLY MOVE");
                    lock.unlock();
                    return "WRONG";
                }
                // 移动的棋子是正确的玩家
                textArea.append("Client " + clientNo + ": SUCCESSFULLY MOVE");
                //send(receiveLine);
                // 将MOVE的消息传给另一位玩家

                current_player = current_player + 1;  // 调整至下一位下棋的玩家
                if (current_player == player_number + 1) current_player = 1;

                outputToClient[current_player - 1].writeUTF(receiveLine);
                outputToClient[current_player - 1].flush();

                lock.unlock();  // 解锁
                return "MOVE";
            }


        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    class HandleAClient implements Runnable {   // 多线程处理客户端请求的函数
        private Socket socket; // 已连接的socket
        private int clientNo;   // 自己的客户端数字

        public HandleAClient(Socket socket, int clientNo) {
            this.socket = socket;
            this.clientNo = clientNo;
        }

        public void run() {

            // 不断响应并发送信息
            while (true) {

                String OP = receive(clientNo);  // 接受行

            }

        }
    }

}
