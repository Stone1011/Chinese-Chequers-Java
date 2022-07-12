package ChineseChequers.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Online extends JFrame {
    private JPanel panel1;
    private JTextArea messageBox;
    private JButton Button2;
    private JTextArea sendArea;
    private JButton Button1;

    private Board board, lastBoard;
    private Socket socket;
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    private Settings.Team myTeam;
    private int player_number = 2;

    public Online(Socket socket)
    {
        //Online的构造函数
        this.socket = socket;
        System.out.println("in\n");

        //GUI相关
        panel1.setSize(Settings.WIDTH - Settings.BOARD_SIZE, Settings.HEIGHT);
        board = new Board(player_number);
        lastBoard = new Board(player_number);
        board.setSize(Settings.BOARD_SIZE, Settings.BOARD_SIZE);

        // 注册事件监听器
        Button1.addActionListener(new Online.Button1Action());
        Button2.addActionListener(new Online.Button2Action());
        addMouseListener(new Online.MouseAction());

        // 设置窗体
        add(panel1, BorderLayout.EAST);
        add(board, BorderLayout.CENTER);
        setSize(Settings.WIDTH, Settings.HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        try {
            fromServer = new DataInputStream(socket.getInputStream());  //连接流
            toServer = new DataOutputStream(socket.getOutputStream());

            messageBox.append("您已经成功进入游戏，请等待游戏开始！\n");

            Task task = new Task();
            new Thread(task).start();

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void receive() {
        try {
            String receiveLine = fromServer.readUTF();
            StringTokenizer strtk = new StringTokenizer(receiveLine, "#");
            int count = strtk.countTokens();
            String op = strtk.nextToken();

            if (op.equals("CHAT")) { // 聊天指令，将其传递给所有玩家
                if (count < 2) return;   //如果没有聊天内容，则报错

                String content = strtk.nextToken();

                messageBox.append("Another player says: " + content);   // TODO: change语言

            } else if (op.equals("START")) {   // 游戏开始
                // 设置棋盘GUI
                board.setStatus(Settings.BoardStatus.started);
                lastBoard.setStatus(Settings.BoardStatus.started);

                // 从服务器获得颜色 TODO:从服务器发送teamnum
                myTeam = Settings.Team.values()[fromServer.readInt() - 1];  //从0开始计数

                messageBox.append("游戏开始！您的颜色是：" + Settings.COLOR_NAME[myTeam.ordinal()] + "\n");

                repaint();

            } else if (op.equals("FAIL")) {    // 游戏结束
                setVisible(false);
                dispose();      // 关闭并删除JFrame

            } else if (op.equals("MOVE")) {    // 移动棋子

                // 另一个玩家的移动
                board.nowSelected = Utils.intToPos(Integer.parseInt(strtk.nextToken()));
                Pos pos = Utils.intToPos(Integer.parseInt(strtk.nextToken()));
                Vector<Pos> tempPath = board.move(board.nowSelected, pos);
                board.at(pos).setStatus(Settings.ChequerStatus.unselected);
                if (!tempPath.isEmpty()) {
                    board.circulateTeam();

                    //send("MOVE#" + Integer.toString(Utils.posToInt(board.nowSelected))+"#"+Integer.toString(pos.toInt()));

                    board.nowPath = tempPath;
                    board.nowPath.add(0, board.nowSelected);

                    for (int i = '0'; i <= 'F'; i++) {
                        for (int j = 0; j < 10; j++) {
                            board.canTo[i][j] = false;
                            if (board.chequers[i][j].getStatus() == Settings.ChequerStatus.accessing)
                                board.chequers[i][j].setStatus(Settings.ChequerStatus.unselected);
                        }
                    }

                    board.nowSelected = new Pos((char) 0, 0);
                    board.at(board.nowSelected).setStatus(Settings.ChequerStatus.unselected);
                    //nowClicked.setStatus(Settings.ChequerStatus.unselected);

                    if (board.nowTeam == Settings.Team.one) {
                        Settings.Team winner = board.checkSituation();
                        if (winner != Settings.Team.noTeam) {
                            board.setStatus(Settings.BoardStatus.finished);
                            // startButton.setText("开始");
                            if (winner == Settings.Team.draw) {
                                messageBox.append("平局");
                            } else {
                                String str = "玩家";
                                String[] numChar = {"一", "二", "三", "四", "五", "六"};
                                String str2 = "取得了胜利！";
                                str += numChar[winner.ordinal()];
                                str += str2;
                                str += "\n";
                                messageBox.append(str);
                            }
                        }
                    }
                    repaint();
                }


            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    class Task implements Runnable {   // 多线程处理客户端请求的函数

        public Task() {
            // nothing
        }

        public void run() {

            // 不断响应并发送信息
            while (true) {
                receive();  // 接受行
            }

        }
    }


    // TODO:细节修改
    class MouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouse) {
            if (myTeam != board.nowTeam) return; // 如果未到自己操作，则是无效操作

            //计算棋盘上之前有没有被选中的棋子数目
            int cnt_selected = 0;
            if (board.nowSelected.group != 0 || board.nowSelected.num != 0)
                cnt_selected++;

            //如果是有效点击
            if (mouse.getButton() == MouseEvent.BUTTON1 && !(new Coor(mouse.getX(), mouse.getY()).toPos().equalsTo(new Pos((char) 0, 0)))
                    && board.getStatus() == Settings.BoardStatus.started &&
                    (board.at(new Coor(mouse.getX(), mouse.getY()).toPos()).getTeam() == board.nowTeam ||
                            board.at(new Coor(mouse.getX(), mouse.getY()).toPos()).getTeam() == Settings.Team.noTeam)
            ) {
                //目前点击的位置
                Chequer nowClicked = board.at(new Coor(mouse.getX(), mouse.getY()).toPos());
                Pos nowPos = new Coor(mouse.getX(), mouse.getY()).toPos();
                lastBoard = new Board(board);
                /*如果这个地方是棋子*/
                if (nowClicked.getTeam() != Settings.Team.noTeam) {
                    /*如果这个棋子之前是被选中的状态*/
                    if (nowClicked.getStatus() == Settings.ChequerStatus.selected) {
                        nowClicked.setStatus(Settings.ChequerStatus.unselected);
                        Vector<Pos> originalAccess = board.canAccess(nowPos);
                        while (!originalAccess.isEmpty()) {
                            Pos temp = originalAccess.firstElement();
                            originalAccess.remove(0);
                            board.at(temp).setStatus(Settings.ChequerStatus.unselected);
                        }
                        board.nowSelected = new Pos((char) 0, 0);
                    }
                    /*如果这个棋子之前是非选中状态*/
                    else if (nowClicked.getStatus() == Settings.ChequerStatus.unselected) {
                        /*如果棋盘上之前没有选中的棋子*/
                        if (cnt_selected == 0) {
                            nowClicked.setStatus(Settings.ChequerStatus.selected);
                            board.nowSelected = new Coor(mouse.getX(), mouse.getY()).toPos();
                        }
                        /*如果棋盘上之前有被选中的棋子*/
                        else {
                            board.at(board.nowSelected).setStatus(Settings.ChequerStatus.unselected);

                            Vector<Pos> originalAccess = board.canAccess(board.nowSelected);
                            while (!originalAccess.isEmpty()) {
                                Pos temp = originalAccess.firstElement();
                                originalAccess.remove(0);
                                board.at(temp).setStatus(Settings.ChequerStatus.unselected);
                            }

                            nowClicked.setStatus(Settings.ChequerStatus.selected);
                            board.nowSelected = new Coor(mouse.getX(), mouse.getY()).toPos();
                        }

                        // 确认能走的
                        Vector<Pos> can = board.canAccess(board.nowSelected);
                        while (!can.isEmpty()) {
                            Pos temp = can.firstElement();
                            can.remove(0);
                            board.at(temp).setStatus(Settings.ChequerStatus.accessing);
                        }
                    }
                    repaint();
                }
                /*如果这个地方是非棋子*/
                else {
                    /*如果棋盘上之前没有选中的棋子*/
                    // NULL
                    /*如果棋盘上之前有被选中的棋子*/
                    if (cnt_selected != 0) {
                        /*如果移动正确*/
                        Coor temp = new Coor(mouse.getX(), mouse.getY());
                        Pos pos = temp.toPos();
                        Vector<Pos> tempPath = board.move(board.nowSelected, pos);
                        board.at(pos).setStatus(Settings.ChequerStatus.unselected);
                        if (!tempPath.isEmpty()) {
                            board.circulateTeam();

                            send("MOVE#" + Integer.toString(Utils.posToInt(board.nowSelected)) + "#" + Integer.toString(pos.toInt()));

                            board.nowPath = tempPath;
                            board.nowPath.add(0, board.nowSelected);

                            for (int i = '0'; i <= 'F'; i++) {
                                for (int j = 0; j < 10; j++) {
                                    board.canTo[i][j] = false;
                                    if (board.chequers[i][j].getStatus() == Settings.ChequerStatus.accessing)
                                        board.chequers[i][j].setStatus(Settings.ChequerStatus.unselected);
                                }
                            }

                            board.nowSelected = new Pos((char) 0, 0);
                            board.at(board.nowSelected).setStatus(Settings.ChequerStatus.unselected);
                            nowClicked.setStatus(Settings.ChequerStatus.unselected);

                            if (board.nowTeam == Settings.Team.one) {
                                Settings.Team winner = board.checkSituation();
                                if (winner != Settings.Team.noTeam) {
                                    board.setStatus(Settings.BoardStatus.finished);
                                    // startButton.setText("开始");
                                    if (winner == Settings.Team.draw) {
                                        messageBox.append("平局");
                                    } else {
                                        String str = "玩家";
                                        String[] numChar = {"一", "二", "三", "四", "五", "六"};
                                        String str2 = "取得了胜利！";
                                        str += numChar[winner.ordinal()];
                                        str += str2;
                                        str += "\n";
                                        messageBox.append(str);
                                    }
                                }
                            }
                            repaint();
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    // 发送按钮
    class Button1Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {    // 如果点击发送
            String message = sendArea.getText();
            if (message.equals("")) return;  // 如果为空，则直接返回
            messageBox.append("I say: " + message + '\n');
            send("CHAT#" + message + '\n');

            String nothing = "";
            sendArea.setText(nothing);
        }
    }

    void closeWindow()
    {
        this.setVisible(false);
    }

    class Button2Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int confirm = JOptionPane.showConfirmDialog(null, "请确认是否回到主菜单！","请确认", JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION)
                return;
            closeWindow();
            new MainWindow();
        }

    }

    public void send(String data) {
        try {
            toServer.writeUTF(data);
            toServer.flush();
        } catch (IOException e) {
            System.err.println(e);
            System.exit(4);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //画纸张
        graphics.setColor(Color.white);
        graphics.fillRect(25, 50, Settings.BOARD_SIZE - 50, Settings.BOARD_SIZE - 100);
        graphics.setColor(Color.black);
        graphics.setStroke(new BasicStroke(1));
        graphics.drawRect(25, 50, Settings.BOARD_SIZE - 50, Settings.BOARD_SIZE - 100);

        if (board.getStatus() == Settings.BoardStatus.started) {  // 如果已经开始
            // TODO:是您的队伍
        }

        //画棋盘、棋子
        board.repaint(graphics);
    }

}
