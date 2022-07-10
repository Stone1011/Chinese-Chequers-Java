package ChineseChequers.Client;

public class Utils
{
    static public Pos intToPos(int num)
    {
        int _num = num & 0xFFFF;
        char _group = (char)(num >> 16);

        return new Pos(_group, _num);
    }

    static public Coor posToCoor(Pos pos)
    {
        double x, y;

        double l = 1 / 2.0;
        double h = 1 * Math.sqrt(3) / 2.0;
        x = Settings.COOR_X;
        y = Settings.COOR_Y;

        if (pos.group == 'A')
        {
            if (pos.num == 0)
            {
                x += 0.0;
                y += 0.0;
            }
            else if (pos.num == 1)
            {
                x += (-1.0) * l * Settings.SCALE;
                y += (1.0) * h * Settings.SCALE;
            }
            else if (pos.num == 2)
            {
                x += (1.0) * l * Settings.SCALE;
                y += (1.0) * h * Settings.SCALE;
            }
            else if (pos.num == 3)
            {
                x += (-2.0) * l * Settings.SCALE;
                y += (2.0) * h * Settings.SCALE;
            }
            else if (pos.num == 4)
            {
                x += 0.0;
                y += (2.0) * h * Settings.SCALE;
            }
            else if (pos.num == 5)
            {
                x += (2.0) * l * Settings.SCALE;
                y += (2.0) * h * Settings.SCALE;
            }
            else if (pos.num == 6)
            {
                x += (-3.0) * l * Settings.SCALE;
                y += (3.0) * h * Settings.SCALE;
            }
            else if (pos.num == 7)
            {
                x += (-1.0) * l * Settings.SCALE;
                y += (3.0) * h * Settings.SCALE;
            }
            else if (pos.num == 8)
            {
                x += (1.0) * l * Settings.SCALE;
                y += (3.0) * h * Settings.SCALE;
            }
            else if (pos.num == 9)
            {
                x += (3.0) * l * Settings.SCALE;
                y += (3.0) * h * Settings.SCALE;
            }
        }
        else if (pos.group == 'B')
        {
            if (pos.num == 0)
            {
                x += 0.0;
                y += (16.0) * h * Settings.SCALE;
            }
            else if (pos.num == 1)
            {
                x += (1.0) * l * Settings.SCALE;
                y += (15.0) * h * Settings.SCALE;
            }
            else if (pos.num == 2)
            {
                x += (-1.0) * l * Settings.SCALE;
                y += (15.0) * h * Settings.SCALE;
            }
            else if (pos.num == 3)
            {
                x += (2.0) * l * Settings.SCALE;
                y += (14.0) * h * Settings.SCALE;
            }
            else if (pos.num == 4)
            {
                x += 0.0;
                y += (14.0) * h * Settings.SCALE;
            }
            else if (pos.num == 5)
            {
                x += (-2.0) * l * Settings.SCALE;
                y += (14.0) * h * Settings.SCALE;
            }
            else if (pos.num == 6)
            {
                x += (3.0) * l * Settings.SCALE;
                y += (13.0) * h * Settings.SCALE;
            }
            else if (pos.num == 7)
            {
                x += (1.0) * l * Settings.SCALE;
                y += (13.0) * h * Settings.SCALE;
            }
            else if (pos.num == 8)
            {
                x += (-1.0) * l * Settings.SCALE;
                y += (13.0) * h * Settings.SCALE;
            }
            else if (pos.num == 9)
            {
                x += (-3.0) * l * Settings.SCALE;
                y += (13.0) * h * Settings.SCALE;
            }
        }
        else if (pos.group == 'C')
        {
            if (pos.num == 0)
            {
                x += (12.0) * l * Settings.SCALE;
                y += (4.0) * h * Settings.SCALE;
            }
            else if (pos.num == 1)
            {
                x += (10.0) * l * Settings.SCALE;
                y += (4.0) * h * Settings.SCALE;
            }
            else if (pos.num == 2)
            {
                x += (11.0) * l * Settings.SCALE;
                y += (5.0) * h * Settings.SCALE;
            }
            else if (pos.num == 3)
            {
                x += (8.0) * l * Settings.SCALE;
                y += (4.0) * h * Settings.SCALE;
            }
            else if (pos.num == 4)
            {
                x += (9.0) * l * Settings.SCALE;;
                y += (5.0) * h * Settings.SCALE;
            }
            else if (pos.num == 5)
            {
                x += (10.0) * l * Settings.SCALE;
                y += (6.0) * h * Settings.SCALE;
            }
            else if (pos.num == 6)
            {
                x += (6.0) * l * Settings.SCALE;
                y += (4.0) * h * Settings.SCALE;
            }
            else if (pos.num == 7)
            {
                x += (7.0) * l * Settings.SCALE;
                y += (5.0) * h * Settings.SCALE;
            }
            else if (pos.num == 8)
            {
                x += (8.0) * l * Settings.SCALE;
                y += (6.0) * h * Settings.SCALE;
            }
            else if (pos.num == 9)
            {
                x += (9.0) * l * Settings.SCALE;
                y += (7.0) * h * Settings.SCALE;
            }
        }
        else if (pos.group == 'D')
        {
            if (pos.num == 0)
            {
                x += (-12.0) * l * Settings.SCALE;
                y += (12.0) * h * Settings.SCALE;
            }
            else if (pos.num == 1)
            {
                x += (-10.0) * l * Settings.SCALE;
                y += (12.0) * h * Settings.SCALE;
            }
            else if (pos.num == 2)
            {
                x += (-11.0) * l * Settings.SCALE;
                y += (11.0) * h * Settings.SCALE;
            }
            else if (pos.num == 3)
            {
                x += (-8.0) * l * Settings.SCALE;
                y += (12.0) * h * Settings.SCALE;
            }
            else if (pos.num == 4)
            {
                x += (-9.0) * l * Settings.SCALE;;
                y += (11.0) * h * Settings.SCALE;
            }
            else if (pos.num == 5)
            {
                x += (-10.0) * l * Settings.SCALE;
                y += (10.0) * h * Settings.SCALE;
            }
            else if (pos.num == 6)
            {
                x += (-6.0) * l * Settings.SCALE;
                y += (12.0) * h * Settings.SCALE;
            }
            else if (pos.num == 7)
            {
                x += (-7.0) * l * Settings.SCALE;
                y += (11.0) * h * Settings.SCALE;
            }
            else if (pos.num == 8)
            {
                x += (-8.0) * l * Settings.SCALE;
                y += (10.0) * h * Settings.SCALE;
            }
            else if (pos.num == 9)
            {
                x += (-9.0) * l * Settings.SCALE;
                y += (9.0) * h * Settings.SCALE;
            }
        }
        else if (pos.group == 'E')
        {
            if (pos.num == 0)
            {
                x += (12.0) * l * Settings.SCALE;
                y += (12.0) * h * Settings.SCALE;
            }
            else if (pos.num == 1)
            {
                x += (11.0) * l * Settings.SCALE;
                y += (11.0) * h * Settings.SCALE;
            }
            else if (pos.num == 2)
            {
                x += (10.0) * l * Settings.SCALE;
                y += (12.0) * h * Settings.SCALE;
            }
            else if (pos.num == 3)
            {
                x += (10.0) * l * Settings.SCALE;
                y += (10.0) * h * Settings.SCALE;
            }
            else if (pos.num == 4)
            {
                x += (9.0) * l * Settings.SCALE;;
                y += (11.0) * h * Settings.SCALE;
            }
            else if (pos.num == 5)
            {
                x += (8.0) * l * Settings.SCALE;
                y += (12.0) * h * Settings.SCALE;
            }
            else if (pos.num == 6)
            {
                x += (9.0) * l * Settings.SCALE;
                y += (9.0) * h * Settings.SCALE;
            }
            else if (pos.num == 7)
            {
                x += (8.0) * l * Settings.SCALE;
                y += (10.0) * h * Settings.SCALE;
            }
            else if (pos.num == 8)
            {
                x += (7.0) * l * Settings.SCALE;
                y += (11.0) * h * Settings.SCALE;
            }
            else if (pos.num == 9)
            {
                x += (6.0) * l * Settings.SCALE;
                y += (12.0) * h * Settings.SCALE;
            }
        }
        else if (pos.group == 'F')
        {
            if (pos.num == 0)
            {
                x += (-12.0) * l * Settings.SCALE;
                y += (4.0) * h * Settings.SCALE;
            }
            else if (pos.num == 1)
            {
                x += (-11.0) * l * Settings.SCALE;
                y += (5.0) * h * Settings.SCALE;
            }
            else if (pos.num == 2)
            {
                x += (-10.0) * l * Settings.SCALE;
                y += (4.0) * h * Settings.SCALE;
            }
            else if (pos.num == 3)
            {
                x += (-10.0) * l * Settings.SCALE;
                y += (6.0) * h * Settings.SCALE;
            }
            else if (pos.num == 4)
            {
                x += (-9.0) * l * Settings.SCALE;;
                y += (5.0) * h * Settings.SCALE;
            }
            else if (pos.num == 5)
            {
                x += (-8.0) * l * Settings.SCALE;
                y += (4.0) * h * Settings.SCALE;
            }
            else if (pos.num == 6)
            {
                x += (-9.0) * l * Settings.SCALE;
                y += (7.0) * h * Settings.SCALE;
            }
            else if (pos.num == 7)
            {
                x += (-8.0) * l * Settings.SCALE;
                y += (6.0) * h * Settings.SCALE;
            }
            else if (pos.num == 8)
            {
                x += (-7.0) * l * Settings.SCALE;
                y += (5.0) * h * Settings.SCALE;
            }
            else if (pos.num == 9)
            {
                x += (-6.0) * l * Settings.SCALE;
                y += (4.0) * h * Settings.SCALE;
            }
        }
        else if(pos.group >= '0' && pos.group <= '9')
        {
            y += (4) * h * Settings.SCALE + (pos.group - '0') * h * Settings.SCALE;

            if (pos.group <= '4')
            {
                x += (-4) * l * Settings.SCALE - (pos.group - '0') * l * Settings.SCALE + (pos.num) * 2 * l * Settings.SCALE;
            }
            else
            {
                x += (-12) * l * Settings.SCALE + (pos.group - '0') * l * Settings.SCALE + (pos.num) * 2 * l * Settings.SCALE;
            }
        }
    else
        return new Coor(-1, -1);

        return new Coor((int)Math.round(x), (int)Math.round(y));
    }

    static public int posToInt(Pos pos)
    {
        return (pos.group << 16) + pos.num;
    }

    static public Pos coorToPos(Coor coor)
    {
        for(char i = 'A'; i <= 'F'; i++)
        {
            for(int j = 0; j <= 9; j++)
            {
                Pos temp = new Pos(i, j);
                Coor tempCoor = temp.toCoor();
                if(coor.equalsTo(tempCoor))
                    return temp;
            }
        }

        for(char i = '0'; i <= '8'; i++)
        {
            for(int j = 0; j <= - Math.abs(i - '0' - 4) + 8; j++)
            {
                Pos temp = new Pos(i, j);
                Coor tempCoor = temp.toCoor();
                if(coor.equalsTo(tempCoor))
                    return temp;
            }
        }

        return new Pos((char)0, 0);
    }

    static int distance(Coor a, Coor b)
    {
        return (int)Math.round(Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)) / Settings.SCALE);
    }

    static int distance(Pos a, Pos b)
    {
        return distance(new Coor(a), new Coor(b));
    }
}
