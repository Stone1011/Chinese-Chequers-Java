package ChineseChequers.Client;

import javax.swing.*;
import java.awt.*;
import java.util.*;

// 棋盘类
public class Board extends JPanel
{
    // 构造函数和析构函数
    public Board()
    {
        status = Settings.BoardStatus.initial;
        teamNum = 0;
        nowTeam = Settings.Team.one;
    }

    public Board(Board newBoard)
    {
        status = newBoard.status;
        teamNum = newBoard.teamNum;
        nowTeam = newBoard.nowTeam;
        nowPath = newBoard.nowPath;
        nowSelected = newBoard.nowSelected;
        shortestNowPath = newBoard.shortestNowPath;
        shortestLength = newBoard.shortestLength;

        for(int i = 0; i < 256; i++)
        {
            for(int j = 0; j < 20; j++)
            {
                chequers[i][j] = newBoard.chequers[i][j];
            }
        }
    }
    
    public Board(int _teamNum)
    {
        status = Settings.BoardStatus.initial;
        nowTeam = Settings.Team.one;
        teamNum = _teamNum;

        for(int i = 0; i < 255; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                canTo[i][j] = false;
            }
        }

        for(char i = 0; i < 255; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                chequers[i][j] = new Chequer();
            }
        }

        if(teamNum == 2)
        {
            for(int i = 0; i < 10; i++)
            {
                chequers['A'][i].setTeam(Settings.Team.one);
            }
            for(int i = 0; i < 10; i++)
            {
                chequers['B'][i].setTeam(Settings.Team.two);
            }
        }
        else if(teamNum == 3)
        {
            for(int i = 0; i < 10; i++)
            {
                chequers['A'][i].setTeam(Settings.Team.one);
            }
            for(int i = 0; i < 10; i++)
            {
                chequers['D'][i].setTeam(Settings.Team.two);
            }
            for(int i = 0; i < 10; i++)
            {
                chequers['E'][i].setTeam(Settings.Team.three);
            }
        }
        else if(teamNum == 6)
        {
            for(int i = 0; i < 10; i++)
            {
                chequers['A'][i].setTeam(Settings.Team.one);
            }
            for(int i = 0; i < 10; i++)
            {
                chequers['F'][i].setTeam(Settings.Team.two);
            }
            for(int i = 0; i < 10; i++)
            {
                chequers['D'][i].setTeam(Settings.Team.three);
            }
            for(int i = 0; i < 10; i++)
            {
                chequers['B'][i].setTeam(Settings.Team.four);
            }
            for(int i = 0; i < 10; i++)
            {
                chequers['E'][i].setTeam(Settings.Team.five);
            }
            for(int i = 0; i < 10; i++)
            {
                chequers['C'][i].setTeam(Settings.Team.six);
            }
        }
        else
            teamNum = 0;
    }

    // 队伍
    public Board addTeam()
    {
        if(teamNum == 0)
        {
            return new Board(2);
        }
        else if(teamNum == 2)
        {
            return new Board(3);
        }
        else if(teamNum == 3)
        {
            return new Board(6);
        }
        else return null;
    }

    public Board delTeam()
    {
        if(teamNum == 6)
            return new Board(3);
        else if(teamNum == 3)
            return new Board(2);
        else if(teamNum == 2)
            return new Board();
        else return null;
    }

    public Settings.Team circulateTeam()
    {
        int nTeam = nowTeam.ordinal();
        nTeam++;
        if(nTeam > teamNum - 1)
            nTeam = 0;

        nowTeam = Settings.Team.values()[nTeam];
        return nowTeam;
    }

    // 状态
    public void setStatus(Settings.BoardStatus boardStatus)
    {
        status = boardStatus;
    }

    public Settings.BoardStatus getStatus()
    {
        return status;
    }

    // 绘图
    public void repaint(Graphics2D graphics)
    {
        // 绘制棋盘
        // 画线
        graphics.setColor(Color.black);
        Pos[] pos_arry1 =
                {
                        new Pos('F', 0), new Pos('F', 2), new Pos('F', 5), new Pos('F', 9),
                        new Pos('A', 0), new Pos('A', 2), new Pos('A', 5), new Pos('A', 9),
                        new Pos('0', 4), new Pos('C', 6), new Pos('C', 3), new Pos('C', 1),
                        new Pos('C', 0), new Pos('E', 6), new Pos('E', 3), new Pos('E', 1),
                        new Pos('E', 0)
                };
        Pos[] pos_arry2 =
                {
                        new Pos('F', 0), new Pos('F', 1), new Pos('F', 3), new Pos('F', 6),
                        new Pos('D', 0), new Pos('D', 1), new Pos('D', 3), new Pos('D', 6),
                        new Pos('8', 0),
                        new Pos('B', 9), new Pos('B', 5), new Pos('B', 2), new Pos('B', 0),
                        new Pos('E', 9), new Pos('E', 5), new Pos('E', 2), new Pos('E', 0)
                };

        Pos[] pos_arry3 =
                {
                        new Pos('D', 0), new Pos('D', 2), new Pos('D', 5), new Pos('D', 9),
                        new Pos('F', 0), new Pos('F', 2), new Pos('F', 5), new Pos('F', 9),
                        new Pos('0', 0),
                        new Pos('A', 6), new Pos('A', 3), new Pos('A', 1), new Pos('A', 0),
                        new Pos('C', 6), new Pos('C', 3), new Pos('C', 1), new Pos('C', 0)
                };

        Pos[] pos_arry4 =
                {
                        new Pos('D', 0), new Pos('D', 1), new Pos('D', 3), new Pos('D', 6),
                        new Pos('B', 0), new Pos('B', 1), new Pos('B', 3), new Pos('B', 6),
                        new Pos('8', 4),
                        new Pos('E', 9), new Pos('E', 5), new Pos('E', 2), new Pos('E', 0),
                        new Pos('C', 9), new Pos('C', 5), new Pos('C', 2), new Pos('C', 0)
                };

        Pos[] pos_arry5 =
                {
                        new Pos('A', 0), new Pos('A', 1), new Pos('A', 3), new Pos('A', 6),
                        new Pos('F', 0), new Pos('F', 1), new Pos('F', 3), new Pos('F', 6),
                        new Pos('4', 0),
                        new Pos('D', 9), new Pos('D', 5), new Pos('D', 2), new Pos('D', 0),
                        new Pos('B', 9), new Pos('B', 5), new Pos('B', 2), new Pos('B', 0)
                };

        Pos[] pos_arry6 =
                {
                        new Pos('A', 0), new Pos('A', 2), new Pos('A', 5), new Pos('A', 9),
                        new Pos('C', 0), new Pos('C', 2), new Pos('C', 5), new Pos('C', 9),
                        new Pos('4', 8),
                        new Pos('E', 6), new Pos('E', 3), new Pos('E', 1), new Pos('E', 0),
                        new Pos('B', 6), new Pos('B', 3), new Pos('B', 1), new Pos('B', 6)
                };

        Coor [] coor1 = new Coor[20], coor2 = new Coor[20], coor3 = new Coor[20],
                coor4 = new Coor[20], coor5 = new Coor[20], coor6 = new Coor[20];

        for(int i = 0; i < 17; i++)
        {
            coor1[i] = Utils.posToCoor(pos_arry1[i]);
            coor2[i] = Utils.posToCoor(pos_arry2[i]);
            coor3[i] = Utils.posToCoor(pos_arry3[i]);
            coor4[i] = Utils.posToCoor(pos_arry4[i]);
            coor5[i] = Utils.posToCoor(pos_arry5[i]);
            coor6[i] = Utils.posToCoor(pos_arry6[i]);
        }

        for(int i = 0; i < 17; i++)
        {
            graphics.drawLine(coor1[i].x, coor1[i].y, coor2[i].x, coor2[i].y);
            graphics.drawLine(coor3[i].x, coor3[i].y, coor4[i].x, coor4[i].y);
            graphics.drawLine(coor5[i].x, coor5[i].y, coor6[i].x, coor6[i].y);
        }

        // 画底色
        // A B
        Coor coor_A1 = Utils.posToCoor(new Pos('A', 0));
        Coor coor_A2 = Utils.posToCoor(new Pos('0', 0));
        Coor coor_A3 = Utils.posToCoor(new Pos('0', 4));
        Coor coor_B1 = Utils.posToCoor(new Pos('B', 0));
        Coor coor_B2 = Utils.posToCoor(new Pos('8', 0));
        Coor coor_B3 = Utils.posToCoor(new Pos('8', 4));

        Color colorAB = new Color(255, 0, 0, 25);
        graphics.setColor(colorAB);
        int [] xCoorA = {coor_A1.x, coor_A2.x, coor_A3.x};
        int [] yCoorA = {coor_A1.y, coor_A2.y, coor_A3.y};
        int [] xCoorB = {coor_B1.x, coor_B2.x, coor_B3.x};
        int [] yCoorB = {coor_B1.y, coor_B2.y, coor_B3.y};
        graphics.fillPolygon(xCoorA, yCoorA, 3);
        graphics.fillPolygon(xCoorB, yCoorB, 3);

        // C D
        Coor coor_C1 = Utils.posToCoor(new Pos('C', 0));
        Coor coor_C2 = Utils.posToCoor(new Pos('0', 4));
        Coor coor_C3 = Utils.posToCoor(new Pos('4', 8));
        Coor coor_D1 = Utils.posToCoor(new Pos('D', 0));
        Coor coor_D2 = Utils.posToCoor(new Pos('8', 0));
        Coor coor_D3 = Utils.posToCoor(new Pos('4', 0));

        Color colorCD = new Color(255, 255, 0, 25);
        graphics.setColor(colorCD);
        int [] xCoorC = {coor_C1.x, coor_C2.x, coor_C3.x};
        int [] yCoorC = {coor_C1.y, coor_C2.y, coor_C3.y};
        int [] xCoorD = {coor_D1.x, coor_D2.x, coor_D3.x};
        int [] yCoorD = {coor_D1.y, coor_D2.y, coor_D3.y};
        graphics.fillPolygon(xCoorC, yCoorC, 3);
        graphics.fillPolygon(xCoorD, yCoorD, 3);

        // E F
        Coor coor_E1 = Utils.posToCoor(new Pos('E', 0));
        Coor coor_E2 = Utils.posToCoor(new Pos('4', 8));
        Coor coor_E3 = Utils.posToCoor(new Pos('8', 4));
        Coor coor_F1 = Utils.posToCoor(new Pos('F', 0));
        Coor coor_F2 = Utils.posToCoor(new Pos('0', 0));
        Coor coor_F3 = Utils.posToCoor(new Pos('4', 0));

        Color colorEF = new Color(0, 255, 0, 25);
        graphics.setColor(colorEF);
        int [] xCoorE = {coor_E1.x, coor_E2.x, coor_E3.x};
        int [] yCoorE = {coor_E1.y, coor_E2.y, coor_E3.y};
        int [] xCoorF = {coor_F1.x, coor_F2.x, coor_F3.x};
        int [] yCoorF = {coor_F1.y, coor_F2.y, coor_F3.y};
        graphics.fillPolygon(xCoorE, yCoorE, 3);
        graphics.fillPolygon(xCoorF, yCoorF, 3);

        // 画路径
        float[] dashPattern = {10, 10, 10, 10, 10, 10, 10, 10, 10};
        graphics.setStroke(new BasicStroke(2.5F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F /*miter limit */, dashPattern, 0));
        graphics.setColor(Settings.PATH_COLOR);

        for(int i = 0; i < nowPath.size() - 1; i++)
        {
            Coor a, b;
            a = Utils.posToCoor(nowPath.get(i));
            b = Utils.posToCoor(nowPath.get(i+1));
            graphics.drawLine(a.x, a.y, b.x, b.y);
        }

        // 画圆格子
        graphics.setColor(Color.white);
        graphics.setStroke(new BasicStroke(1));
        for(char i = 'A'; i <= 'F'; i++)
        {
            for(int j = 0; j <= 9; j++)
            {
                Pos pos = new Pos(i, j);
                Coor coor = Utils.posToCoor(pos);
                graphics.fillOval(coor.x - Settings.CHEQUER_RADIUS, coor.y - Settings.CHEQUER_RADIUS,
                        2*Settings.CHEQUER_RADIUS, 2*Settings.CHEQUER_RADIUS);
            }
        }
        for(char i = '0'; i <= '8'; i++)
        {
            for(int j = 0; j <= -Math.abs(i - '0' - 4) + 8; j++)
            {
                Pos pos = new Pos(i, j);
                Coor coor = Utils.posToCoor(pos);
                graphics.fillOval(coor.x - Settings.CHEQUER_RADIUS, coor.y - Settings.CHEQUER_RADIUS,
                        2*Settings.CHEQUER_RADIUS, 2*Settings.CHEQUER_RADIUS);
            }
        }
        graphics.setColor(Color.black);
        graphics.setStroke(new BasicStroke(1));
        for(char i = 'A'; i <= 'F'; i++)
        {
            for(int j = 0; j <= 9; j++)
            {
                Pos pos = new Pos(i, j);
                Coor coor = Utils.posToCoor(pos);
                graphics.drawOval(coor.x - Settings.CHEQUER_RADIUS, coor.y - Settings.CHEQUER_RADIUS,
                        2*Settings.CHEQUER_RADIUS, 2*Settings.CHEQUER_RADIUS);
            }
        }
        for(char i = '0'; i <= '8'; i++)
        {
            for(int j = 0; j <= -Math.abs(i - '0' - 4) + 8; j++)
            {
                Pos pos = new Pos(i, j);
                Coor coor = Utils.posToCoor(pos);
                graphics.drawOval(coor.x - Settings.CHEQUER_RADIUS, coor.y - Settings.CHEQUER_RADIUS,
                        2*Settings.CHEQUER_RADIUS, 2*Settings.CHEQUER_RADIUS);
            }
        }

        // 画提示
        if(getStatus() == Settings.BoardStatus.started)
        {
            graphics.drawString("是现在该走的一方", 655 * Settings.BOARD_SIZE / 900,120 * Settings.BOARD_SIZE / 900);
            graphics.setColor(Settings.CHEQUER_COLOR[nowTeam.ordinal()]);
            graphics.fillOval(620 * Settings.BOARD_SIZE / 900,105 * Settings.BOARD_SIZE / 900, 2*Settings.CHEQUER_RADIUS, 2*Settings.CHEQUER_RADIUS);
        }
        else if(getStatus() == Settings.BoardStatus.paused)
        {
            graphics.drawString("暂停中", 655 * Settings.BOARD_SIZE / 900,120 * Settings.BOARD_SIZE / 900);
        }

        // 画棋子
        if(getStatus() != Settings.BoardStatus.initial)
        {
            for(char i = '0'; i <= 'F'; i++)
            {
                if(i > '9'&& i < 'A')
                    continue;
                for(int j = 0; j <= 9; j++)
                {
                    chequers[i][j].paint(graphics, new Pos(i,j));
                }
            }
        }

    }

    // 棋盘方法
    public Chequer at(Pos pos)
    {
        return chequers[pos.group][pos.num];
    }

    public Settings.Team checkSituation()  // 检查状态，返回赢的队伍或者或者draw或者Team.noTeam
    {
        Settings.Team result = Settings.Team.noTeam;

        if(teamNum == 2)
        {
            Settings.Team[] alphaTeam = {Settings.Team.two, Settings.Team.one};
            boolean [] teamWin = new boolean[2];

            for(int i = 0; i < 2; i++)
                teamWin[i] = true;

            for(int i = 'A'; i <= 'B'; i++)
            {
                for(int j = 0; teamWin[alphaTeam[i - 'A'].ordinal()] && j < 10; j++)
                {
                    Pos pos = new Pos((char)i, j);
                    if(at(pos).getTeam() != alphaTeam[i - 'A'])
                        teamWin[alphaTeam[i - 'A'].ordinal()] = false;
                }
            }

            int cnt = 0;
            for(int i = 'A'; i <= 'B'; i++)
                if(teamWin[alphaTeam[i - 'A'].ordinal()])
                    cnt++;

            if(cnt == 0)
            {
                result = Settings.Team.noTeam;
            }
            else if(cnt == 1)
            {
                for(int i = 'A'; i <= 'B'; i++)
                    if(teamWin[alphaTeam[i - 'A'].ordinal()])
                        result = alphaTeam[i - 'A'];
            }
            else
            {
                result = Settings.Team.draw;
            }
        }
        else if(teamNum == 3)
        {
            Settings.Team[] alphaTeam = {Settings.Team.noTeam, Settings.Team.one, Settings.Team.two, Settings.Team.noTeam, Settings.Team.noTeam, Settings.Team.three};
            boolean [] teamWin = new boolean[6];

            for(int i = 0; i < 6; i++)
                teamWin[i] = true;

            for(int i = 'A'; i <= 'F'; i++)
            {
                if(alphaTeam[i - 'A'] == Settings.Team.noTeam)
                    continue;
                for(int j = 0; teamWin[alphaTeam[i - 'A'].ordinal()] && j < 10; j++)
                {
                    Pos pos = new Pos((char)i, j);
                    if(at(pos).getTeam() != alphaTeam[i - 'A'])
                        teamWin[alphaTeam[i - 'A'].ordinal()] = false;
                }
            }

            int cnt = 0;
            for(int i = 'A'; i <= 'F'; i++)
            {
                if(alphaTeam[i - 'A'] == Settings.Team.noTeam)
                    continue;
                if(teamWin[alphaTeam[i - 'A'].ordinal()])
                    cnt++;
            }

            if(cnt == 0)
            {
                result = Settings.Team.noTeam;
            }
            else if(cnt == 1)
            {
                for(int i = 'A'; i <= 'F'; i++)
                {
                    if(alphaTeam[i - 'A'] == Settings.Team.noTeam)
                        continue;
                    if(teamWin[alphaTeam[i - 'A'].ordinal()])
                        result = alphaTeam[i - 'A'];
                }
            }
            else
            {
                result = Settings.Team.draw;
            }
        }
        else if (teamNum == 6)
        {
            //char teamAlpha[] = {'A', 'F', 'D', 'B', 'E', 'C'};
            Settings.Team[] alphaTeam = {Settings.Team.one, Settings.Team.four, Settings.Team.six, Settings.Team.three, Settings.Team.five, Settings.Team.two};
            boolean [] teamWin = new boolean[6];

            for(int i = 0; i < 6; i++)
                teamWin[i] = true;

            for(int i = 'A'; i <= 'F'; i++)
            {
                for(int j = 0; teamWin[alphaTeam[i - 'A'].ordinal()] && j < 10; j++)
                {
                    Pos pos = new Pos((char)i, j);
                    if(at(pos).getTeam() != alphaTeam[i - 'A'])
                        teamWin[alphaTeam[i - 'A'].ordinal()] = false;
                }
            }

            int cnt = 0;
            for(int i = 'A'; i <= 'F'; i++)
                if(teamWin[alphaTeam[i - 'A'].ordinal()])
                    cnt++;

            if(cnt == 0)
            {
                result = Settings.Team.noTeam;
            }
            else if(cnt == 1)
            {
                for(int i = 'A'; i <= 'F'; i++)
                    if(teamWin[alphaTeam[i - 'A'].ordinal()])
                        result = alphaTeam[i - 'A'];
            }
            else
            {
                result = Settings.Team.draw;
            }
        }

        return result;
    }

    public Vector <Pos> move(Pos from, Pos to)  // 移动棋子，返回移动路径
    {
        Vector <Pos> result = isValidMove(from, to);
        if(!result.isEmpty())
        {
            Chequer temp = chequers[from.group][from.num];
            chequers[from.group][from.num] = chequers[to.group][to.num];
            chequers[to.group][to.num] = temp;
        }
        return result;
    }

    public Vector <Pos> isValidMove(Pos from, Pos to)
    {
        nowPath = new Vector<Pos>();
        shortestLength = 99999;
        shortestNowPath = new Vector <Pos> ();
        nowJumpLength = -1;

        for(char i = '0'; i <= 'F'; i++)
            for(int j = 0; j <= 9; j++)
                visited[i][j] = false;
        go(from, to);
        return shortestNowPath;
    }

    // 搜索
    public Vector <Pos> nearPoints(Pos from, Settings.NearPointsMode mode, int jumpLength)  // 寻找附近能跳的点
    {
        Vector <Pos> to = new Vector<Pos>();

        //第一次跳
        if(mode == Settings.NearPointsMode.goAndJump)
        {
            // 相邻点
            Pos temp;

            temp = new Coor(from).add(new Coor(Settings.SCALE, 0));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor(-Settings.SCALE, 0));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor((int)Math.round(Settings.SCALE * 0.5), (int)Math.round(Settings.SCALE * Math.sqrt(3) / 2.0)));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor((int)Math.round(-Settings.SCALE * 0.5), (int)Math.round(-Settings.SCALE * Math.sqrt(3) / 2.0)));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor((int)Math.round(-Settings.SCALE * 0.5), (int)Math.round(Settings.SCALE * Math.sqrt(3) / 2.0)));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor((int)Math.round(Settings.SCALE * 0.5), (int)Math.round(-Settings.SCALE * Math.sqrt(3) / 2.0)));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);

            // 不相邻点
            int nowPoints, mid;
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor(i * Settings.SCALE, 0));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor(-i * Settings.SCALE, 0));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor((int)Math.round(i * Settings.SCALE * 0.5), (int)Math.round(i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor((int)Math.round(-i * Settings.SCALE * 0.5), (int)Math.round(-i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor((int)Math.round(-i * Settings.SCALE * 0.5), (int)Math.round(i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor((int)Math.round(i * Settings.SCALE * 0.5), (int)Math.round(-i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
        }

        // 多次跳
        else
        {
            int nowPoints, mid;
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor(i * Settings.SCALE, 0));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor(-i * Settings.SCALE, 0));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor((int)Math.round(i * Settings.SCALE * 0.5), (int)Math.round(i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor((int)Math.round(-i * Settings.SCALE * 0.5), (int)Math.round(-i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor((int)Math.round(-i * Settings.SCALE * 0.5), (int)Math.round(i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor((int)Math.round(i * Settings.SCALE * 0.5), (int)Math.round(-i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
        }

        return to;
    }

    public Vector <Pos> nearPoints(Pos from, Settings.NearPointsMode mode)  // 寻找附近能跳的点
    {
        int jumpLength = 0;
        Vector <Pos> to = new Vector<Pos>();

        //第一次跳
        if(mode == Settings.NearPointsMode.goAndJump)
        {
            // 相邻点
            Pos temp;

            temp = new Coor(from).add(new Coor(Settings.SCALE, 0));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor(-Settings.SCALE, 0));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor((int)Math.round(Settings.SCALE * 0.5), (int)Math.round(Settings.SCALE * Math.sqrt(3) / 2.0)));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor((int)Math.round(-Settings.SCALE * 0.5), (int)Math.round(-Settings.SCALE * Math.sqrt(3) / 2.0)));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor((int)Math.round(-Settings.SCALE * 0.5), (int)Math.round(Settings.SCALE * Math.sqrt(3) / 2.0)));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);
            temp = new Coor(from).add(new Coor((int)Math.round(Settings.SCALE * 0.5), (int)Math.round(-Settings.SCALE * Math.sqrt(3) / 2.0)));
            if(at(temp).getTeam() == Settings.Team.noTeam && temp.group != 0)
                to.add(temp);

            // 不相邻点
            int nowPoints, mid;
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor(i * Settings.SCALE, 0));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor(-i * Settings.SCALE, 0));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor((int)Math.round(i * Settings.SCALE * 0.5), (int)Math.round(i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor((int)Math.round(-i * Settings.SCALE * 0.5), (int)Math.round(-i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor((int)Math.round(-i * Settings.SCALE * 0.5), (int)Math.round(i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; ; i ++)
            {
                temp = new Coor(from).add(new Coor((int)Math.round(i * Settings.SCALE * 0.5), (int)Math.round(-i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    continue;
                }
                if(nowPoints == 1 && i == 2 * mid)
                    to.add(temp);
            }
        }

        // 多次跳
        else
        {
            int nowPoints, mid;
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor(i * Settings.SCALE, 0));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor(-i * Settings.SCALE, 0));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor((int)Math.round(i * Settings.SCALE * 0.5), (int)Math.round(i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor((int)Math.round(-i * Settings.SCALE * 0.5), (int)Math.round(-i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor((int)Math.round(-i * Settings.SCALE * 0.5), (int)Math.round(i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
            mid = nowPoints = 0;
            for(int i = 1; i <= jumpLength; i ++)
            {
                Pos temp = new Coor(from).add(new Coor((int)Math.round(i * Settings.SCALE * 0.5), (int)Math.round(-i * Settings.SCALE * Math.sqrt(3) / 2)));
                if(temp.group == 0 || nowPoints > 1)
                    break;
                if(at(temp).getTeam() != Settings.Team.noTeam)
                {
                    nowPoints++;
                    mid = i;
                    if(mid != jumpLength / 2)
                        break;
                    continue;
                }
                if(nowPoints == 1 && i == jumpLength)
                    to.add(temp);
            }
        }

        return to;
    }

    public void go(Pos from, Pos to)
    {
        if(!canTo[from.group][from.num])
        {
            canTo[from.group][from.num] = true;
        }

        if(from.group == to.group && from.num == to.num)
        {
            if(shortestLength > nowPath.size())
            {
                shortestLength = nowPath.size();
                shortestNowPath = new Vector<Pos>();
                shortestNowPath.addAll(nowPath);
            }
            return;
        }

        Vector <Pos> nowPoints = new Vector<Pos>();
        if(nowJumpLength == -1)
            nowPoints = nearPoints(from, Settings.NearPointsMode.goAndJump);
        else
            nowPoints = nearPoints(from, Settings.NearPointsMode.jump, nowJumpLength);

        while(!nowPoints.isEmpty())
        {
            Pos newFrom = nowPoints.firstElement();
            nowPoints.remove(0);
            if(visited[newFrom.group][newFrom.num])
                continue;

            if(nowJumpLength == -1)
            {
                nowJumpLength = Utils.distance(from, newFrom);

                visited[newFrom.group][newFrom.num] = true;
                nowPath.add(newFrom);
                go(newFrom, to);
                nowPath.remove(nowPath.size() - 1);
                visited[newFrom.group][newFrom.num] = false;

                nowJumpLength = -1;
            }
            else
            {
                visited[newFrom.group][newFrom.num] = true;
                nowPath.add(newFrom);
                go(newFrom, to);
                nowPath.remove(nowPath.size() - 1);
                visited[newFrom.group][newFrom.num] = false;
            }
        }

        return;
    }

    public Vector <Pos> canAccess(Pos pos)
    {
        for(int i = 0; i < 255; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                canTo[i][j] = false;
            }
        }

        Vector <Pos> result = new Vector <Pos> ();
        Pos outside = new Pos((char)0, 0);
        move(pos, outside);

        for(int i = '0'; i <= 'F'; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(canTo[i][j] && (pos.group != i|| pos.num != j))
                    result.add(new Pos((char)i, j));
            }
        }

        return result;
    }

    // 普通数据成员
    final Chequer [][] chequers = new Chequer[256][20];
    private Settings.BoardStatus status = Settings.BoardStatus.initial;
    private int teamNum;
    Settings.Team nowTeam = Settings.Team.noTeam;
    Pos nowSelected = new Pos();

    // 路径数据成员
    Vector <Pos> nowPath = new Vector<Pos>();  // 当前的跳棋跳的路径
    private Vector <Pos> shortestNowPath = new Vector<Pos>();  // 当前的最短路径
    final boolean [][] canTo = new boolean[256][20];
    private int shortestLength;
    private int nowJumpLength;
    private final boolean [][] visited = new boolean[256][20];  // 是否访问过节点
};