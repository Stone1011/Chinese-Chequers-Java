package ChineseChequers.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructions extends JFrame
{
    private JPanel instructionsForm;
    private JButton backButton;
    private JTextArea textContent;

    public Instructions()
    {
        // 设置文字
        textContent.setText("""
                跳棋是黑白棋的一种。跳棋是一种可以由二至六人同时进行的棋，棋盘为六角星形，棋子分为六种颜色，每种颜色有6个或10个或15个棋子，每一位玩家使用跳棋一个角，拥有一种颜色的棋子。跳棋是一项老少皆宜、流传广泛的益智型棋类游戏。

                跳棋的游戏规则很简单，棋子的移动可以一步步在有直线连接的相邻六个方向进行，如果相邻位置上有任何方的一个棋子，该位置直线方向下一个位置是空的，则可以直接“跳”到该空位上，“跳”的过程中，只要相同条件满足就可以连续进行。谁最先把正对面的阵地全部占领，谁就取得胜利。一玩就懂，所以几乎每个人都下过跳棋。在香港跳棋被称为“玻子棋”和“玻珠棋”。

                “相邻跳”：则可以直接跳到该空位上，跳的过程中，只要相同条件满足就可以连续进行。

                “等距跳”：棋子的移动可以一步步在有直线连接的相邻六个方向进行，如果在和同一直线上的任意一个空位所构成的线段中，只有一个并且位于该线段中间的任何方的棋子，则可以直接跳到那个空位上，跳的过程中，只要相同条件满足就可以连续进行。

                一局跳棋，可以分为开局、中盘、收官（借用围棋术语）三个阶段。开局一般指的是从双方棋子的出动到子的初步相互接触为止的过程，一般在10步棋以内；中盘是指双方的子力纠缠在一起，争夺出路，同时又给对方设置障碍的阶段；收官则是双方的棋子基本分开，各自按自己的方式尽快进入对面的阵地。当然，这三个阶段不是截然分开的，就像一场短跑比赛中起跑、中途跑、冲刺，要求起跑不能落后，中途跑要能跟上，冲刺的时候一鼓作气，才可能取得胜利，任何一项落后，就会与胜利失之交臂。在一局跳棋中，开局的时候就要看清中盘的发展方向，为中盘做准备；而中盘快要结束的时候，又要提前为自己的收官设计最佳方案。如果这些都能成竹在胸，那么你也就迈入跳棋高手的行列了。

                游戏参与人数不能是1个或5个人。4人或6人时，一方的对角必须是另一方。3人时，一方的对角不能是另一方。以完全占领对角阵地的走子次数决定胜负、名次。""");
        textContent.setLineWrap(true);
        textContent.setBackground(new Color(236, 236, 236));

        // 注册按钮点击事件
        backButton.addActionListener(new BackAction());

        // 设置窗体
        add(instructionsForm);
        setSize(700, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void closeWindow()
    {
        this.setVisible(false);
    }

    class BackAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            closeWindow();
            new MainWindow();
        }
    }
}
