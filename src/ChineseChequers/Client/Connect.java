package ChineseChequers.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.io.*;

public class Connect extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton Button1;
    private JButton Button2;
    private JPanel JPanel2;
    private JPanel JPanel1;

    public Connect() {
        // GUI相关
        setTitle("Connect");
        add(JPanel1, BorderLayout.CENTER);
        add(JPanel2, BorderLayout.SOUTH);
        setVisible(true);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Button1.add(new Connect.Button1());
        Button2.addActionListener(new Connect.Button2Action());

        //board = new Board(2);
        //board.setSize(Settings.BOARD_SIZE, Settings.BOARD_SIZE);
    }

    class Button2Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String IP = textField1.getText();
            String port = textField2.getText();
            String room = textField3.getText();

            // TODO: 错误处理
            if (IP.equals(""))
                IP = "localhost";
            int portnum = Integer.parseInt(port);

            try {
                Socket socket = new Socket(IP, portnum);    // 连接失败会返回IOE异常

                dispose();
                // 开启Online
                new Online(socket);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "无法连接至指定服务器，请检查", "注意", JOptionPane.WARNING_MESSAGE);
                System.err.println(ex);
            }

        }
    }


}
