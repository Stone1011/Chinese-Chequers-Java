package ChineseChequers.Server;

import javax.swing.*;

public class Main
{
    public static void main(String [] args)
    {
        String portStr = JOptionPane.showInputDialog(null, "请输入服务器端口", "端口设置", JOptionPane.INFORMATION_MESSAGE);
        int port = 6666;
        try
        {
            port = Integer.parseInt(portStr);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "无法监听指定端口，请检查", "注意", JOptionPane.WARNING_MESSAGE);
            System.exit(1);
        }
        new Server(port);
    }
}
