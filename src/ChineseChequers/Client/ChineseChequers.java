package ChineseChequers.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import ChineseChequers.Client.Settings.*;

public class ChineseChequers extends JFrame
{
    private JPanel metaArea;
    private JButton startButton;
    private JButton restartButton;
    private JButton pauseButton;
    private JButton regretButton;
    private JTextField playerNum;
    private JButton backButton;
    private JTextArea messageBox;

    public ChineseChequers()
    {
        board = new Board(2);
        board.setSize(Settings.BOARD_SIZE, Settings.BOARD_SIZE);
        metaArea.setSize(Settings.WIDTH - Settings.BOARD_SIZE, Settings.HEIGHT);

        // 注册事件监听器
        startButton.addActionListener(new ChineseChequers.StartAction());
        restartButton.addActionListener(new ChineseChequers.RestartAction());
        pauseButton.addActionListener(new ChineseChequers.PauseAction());
        regretButton.addActionListener(new ChineseChequers.RegretAction());
        backButton.addActionListener(new ChineseChequers.BackAction());
        addMouseListener(new ChineseChequers.MouseAction());

        // 设置窗体
        add(board, BorderLayout.CENTER);
        add(metaArea, BorderLayout.EAST);
        setSize(Settings.WIDTH, Settings.HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D graphics = (Graphics2D)g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        //画纸张
        graphics.setColor(Color.white);
        graphics.fillRect(25,50, Settings.BOARD_SIZE - 50, Settings.BOARD_SIZE - 100);
        graphics.setColor(Color.black);
        graphics.setStroke(new BasicStroke(1));
        graphics.drawRect(25,50, Settings.BOARD_SIZE - 50, Settings.BOARD_SIZE - 100);

        //画棋盘、棋子
        board.repaint(graphics);
    }

    class StartAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if((board.getStatus() == BoardStatus.initial))
            {
                messageBox.setText("");
                //确定玩家人数
                if(playerNum.getText().equals(""))
                {
                    messageBox.append("请输入玩家人数 (2, 3, 6人)\n");
                    repaint();
                    return;
                }
                int num = Integer.parseInt(playerNum.getText());

                if((num!=2)&&(num!=3)&&(num!=6))
                {
                    messageBox.append("人数输入无效！\n");
                    repaint();
                    return;
                }
                else
                {
                    board = new Board(num);
                    lastBoard = new Board(num);
                }
                messageBox.append("游戏开始！\n");
                startButton.setText("认输");
                board.setStatus(BoardStatus.started);
                lastBoard.setStatus(BoardStatus.started);

                repaint();
            }

            else if((board.getStatus() == BoardStatus.started))
            {
                messageBox.append("认输！\n");
                startButton.setText("开始");
                board.setStatus(BoardStatus.finished);
                lastBoard.setStatus(BoardStatus.finished);

                repaint();
            }

            else if((board.getStatus() == BoardStatus.finished))
            {
                board.setStatus(BoardStatus.initial);
                lastBoard.setStatus(BoardStatus.initial);
                actionPerformed(e);
            }
        }
    }

    class RestartAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int confirm = JOptionPane.showConfirmDialog(null, "请确认是否重新开始！","请确认", JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION)
                return;

            board.setStatus(BoardStatus.finished);
            lastBoard.setStatus(BoardStatus.finished);
            new StartAction().actionPerformed(e);
            repaint();
        }
    }

    class PauseAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(board.getStatus() == BoardStatus.paused)
            {
                messageBox.append("游戏恢复！\n");
                pauseButton.setText("暂停");
                board.setStatus(BoardStatus.started);
                repaint();
            }

            else if(board.getStatus() == BoardStatus.started)
            {
                messageBox.append("游戏暂停！\n");
                pauseButton.setText("恢复");
                board.setStatus(BoardStatus.paused);
                repaint();
            }
        }
    }

    class RegretAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            boolean equal = true;
            for(char i = '0'; i <= 'F'; i++)
            {
                for(int j = 0; j < 9; j++)
                {
                    lastBoard.at(new Pos(i, j)).setStatus(ChequerStatus.unselected);
                    lastBoard.nowSelected = new Pos((char)0, 0);
                    if(lastBoard.at(new Pos(i, j)).getTeam() != board.at(new Pos(i, j)).getTeam())
                    {
                        equal = false;
                    }
                }
            }

            if(board.getStatus() != BoardStatus.started || equal)
            {
                String a = "悔棋无效！\n";
                messageBox.append(a);
                messageBox.repaint();
                return;
            }
            else
            {
                messageBox.append("悔棋成功！\n");
            }

            board = new Board(lastBoard);
            repaint();
        }
    }

    class BackAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // TODO: fix after designing main window
        }
    }

    class MouseAction implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent mouse)
        {
            //计算棋盘上之前有没有被选中的棋子数目
            int cnt_selected = 0;
            if (board.nowSelected.group != 0 || board.nowSelected.num != 0)
                cnt_selected++;

            //如果是有效点击
            if (mouse.getButton() == MouseEvent.BUTTON1 && !(new Coor(mouse.getX(), mouse.getY()).toPos().equalsTo(new Pos((char) 0, 0)))
                    && board.getStatus() == BoardStatus.started &&
                    (board.at(new Coor(mouse.getX(), mouse.getY()).toPos()).getTeam() == board.nowTeam ||
                            board.at(new Coor(mouse.getX(), mouse.getY()).toPos()).getTeam() == Team.noTeam)
            )
            {
                //目前点击的位置
                Chequer nowClicked = board.at(new Coor(mouse.getX(), mouse.getY()).toPos());
                Pos nowPos = new Coor(mouse.getX(), mouse.getY()).toPos();
                lastBoard = new Board(board);
                /*如果这个地方是棋子*/
                if (nowClicked.getTeam() != Team.noTeam)
                {
                    /*如果这个棋子之前是被选中的状态*/
                    if (nowClicked.getStatus() == ChequerStatus.selected)
                    {
                        nowClicked.setStatus(ChequerStatus.unselected);
                        Vector<Pos> originalAccess = board.canAccess(nowPos);
                        while (!originalAccess.isEmpty())
                        {
                            Pos temp = originalAccess.firstElement();
                            originalAccess.remove(0);
                            board.at(temp).setStatus(ChequerStatus.unselected);
                        }
                        board.nowSelected = new Pos((char) 0, 0);
                    }
                    /*如果这个棋子之前是非选中状态*/
                    else if (nowClicked.getStatus() == ChequerStatus.unselected)
                    {
                        /*如果棋盘上之前没有选中的棋子*/
                        if (cnt_selected == 0)
                        {
                            nowClicked.setStatus(ChequerStatus.selected);
                            board.nowSelected = new Coor(mouse.getX(), mouse.getY()).toPos();
                        }
                        /*如果棋盘上之前有被选中的棋子*/
                        else
                        {
                            board.at(board.nowSelected).setStatus(ChequerStatus.unselected);

                            Vector<Pos> originalAccess = board.canAccess(board.nowSelected);
                            while (!originalAccess.isEmpty())
                            {
                                Pos temp = originalAccess.firstElement();
                                originalAccess.remove(0);
                                board.at(temp).setStatus(ChequerStatus.unselected);
                            }

                            nowClicked.setStatus(ChequerStatus.selected);
                            board.nowSelected = new Coor(mouse.getX(), mouse.getY()).toPos();
                        }

                        // 确认能走的
                        Vector<Pos> can = board.canAccess(board.nowSelected);
                        while (!can.isEmpty())
                        {
                            Pos temp = can.firstElement();
                            can.remove(0);
                            board.at(temp).setStatus(ChequerStatus.accessing);
                        }
                    }
                    repaint();
                }
                /*如果这个地方是非棋子*/
                else
                {
                    /*如果棋盘上之前没有选中的棋子*/
                    // NULL
                    /*如果棋盘上之前有被选中的棋子*/
                    if (cnt_selected != 0)
                    {
                        /*如果移动正确*/
                        Coor temp = new Coor(mouse.getX(), mouse.getY());
                        Pos pos = temp.toPos();
                        Vector<Pos> tempPath = board.move(board.nowSelected, pos);
                        board.at(pos).setStatus(ChequerStatus.unselected);
                        if (!tempPath.isEmpty())
                        {
                            board.circulateTeam();

                            board.nowPath = tempPath;
                            board.nowPath.add(0, board.nowSelected);

                            for (int i = '0'; i <= 'F'; i++)
                            {
                                for (int j = 0; j < 10; j++)
                                {
                                    board.canTo[i][j] = false;
                                    if (board.chequers[i][j].getStatus() == ChequerStatus.accessing)
                                        board.chequers[i][j].setStatus(ChequerStatus.unselected);
                                }
                            }

                            board.nowSelected = new Pos((char) 0, 0);
                            board.at(board.nowSelected).setStatus(ChequerStatus.unselected);
                            nowClicked.setStatus(ChequerStatus.unselected);

                            if (board.nowTeam == Team.one)
                            {
                                Team winner = board.checkSituation();
                                if (winner != Team.noTeam)
                                {
                                    board.setStatus(BoardStatus.finished);
                                    startButton.setText("开始");
                                    if (winner == Team.draw)
                                    {
                                        messageBox.append("平局");
                                    } else
                                    {
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
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private Board board, lastBoard;
}
