package ChineseChequers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChineseChequers extends JFrame
{
    private JPanel metaArea;
    private JButton startButton;
    private JButton restartButton;
    private JButton pauseButton;
    private JButton regretButton;
    private JTextField playerNum;
    private JButton backButton;
    private JTextPane messageBox;

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
            // todo
        }
    }

    class RestartAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // todo
        }
    }

    class PauseAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // todo
        }
    }

    class RegretAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // todo
        }
    }

    class BackAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // todo
        }
    }

    class MouseAction implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            // todo
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
