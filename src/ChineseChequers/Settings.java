package ChineseChequers;

import java.awt.*;

public class Settings
{
    // 定义需要用到的枚举量
    enum Team {one, two, three, four, five, six, noTeam, draw /*, VIP*/};
    enum BoardStatus {locked, started, finished, paused, initial};
    enum ChequerStatus {selected, unselected, accessing};
    enum NetStatus {connecting, connected, unconnected, offline};
    enum NetError {};   // 待完善
    enum NearPointsMode {jump, goAndJump};
    enum OP{SETROOM, SUCCESS, FAIL, START, MOVE, JUDGE, CHAT};

    // 定义需要用到的常量（待完善）
    // 棋盘属性
    static final int WIDTH = 1600; // 窗口宽度
    static final int HEIGHT = 900; // 窗口高度
    static final int SCALE = 50;   // 棋盘缩放系数，指相邻两个棋子的距离
    static final int BOARD_SIZE = 900; // 正方形棋盘的边长
    static final int COOR_X = 400; // 顶部棋子的横坐标
    static final int COOR_Y = 100; // 顶部棋子的纵坐标
    static final int PEN_SIZE = 2; // 棋盘中线的宽度
    // 棋子属性
    static final int CHEQUER_RADIUS = 8;
    static final Color CHEQUER_COLOR[] = {Color.orange, Color.blue, Color.green, Color.gray, Color.cyan, Color.magenta};
    static final Color PATH_COLOR = Color.red;
    static final String COLOR_NAME[] = {"橙色", "蓝色", "绿色", "灰色", "青色", "粉色"};
    // 游戏属性
    static final int MAX_TEAM_NUM = 6;
    static final int MAX_REGRET_NUM = 3;
    static final int EQUAL_LIMIT = CHEQUER_RADIUS * 2;
}
