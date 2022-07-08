package ChineseChequers;

import javax.swing.*;
import java.awt.*;

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

        add(board, BorderLayout.CENTER);
        add(metaArea, BorderLayout.EAST);
        setSize(Settings.WIDTH, Settings.HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private Board board;
}
