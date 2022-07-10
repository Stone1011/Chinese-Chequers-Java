package ChineseChequers.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame
{
    public MainWindow()
    {
        // 注册按钮点击事件
        offlineButton.addActionListener(new OfflineAction());
        onlineButton.addActionListener(new OnlineAction());
        instructionsButton.addActionListener(new InstructionsAction());

        // 设置窗体
        add(mainWindowForm);
        setSize(600, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JButton offlineButton;
    private JButton onlineButton;
    private JButton instructionsButton;
    private JPanel mainWindowForm;

    void closeWindow()
    {
        this.setVisible(false);
    }

    class OfflineAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int confirm = JOptionPane.showConfirmDialog(null, "请确认是否开始离线热座模式！","请确认", JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION)
                return;

            closeWindow();
            new ChineseChequers();
        }
    }

    class OnlineAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int confirm = JOptionPane.showConfirmDialog(null, "请确认是否开始在线对抗模式！","请确认", JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION)
                return;

            closeWindow();
            // Todo
        }
    }

    class InstructionsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            closeWindow();
            new Instructions();
        }
    }
}
