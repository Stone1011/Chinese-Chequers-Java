package ChineseChequers.Client;

// Pos类
class Pos
{
    // 数据成员
    public char group;
    public int num;

    // 构造函数
    public Pos()
    {
        group = 0;
        num = 0;
    }

    public Pos(char c, int i)
    {
        group = c;
        num = i;
    }

    public Pos(Coor t)
    {
        Pos x = Utils.coorToPos(t);
        group = x.group;
        num = x.num;
    }

    // 类型转换函数
    public Coor toCoor()
    {
        return Utils.posToCoor(this);
    }

    public int toInt()
    {
        return (group << 16) + num;
    }

    public boolean equalsTo(Pos other)
    {
        return group == other.group && num == other.num;
    }
}


// Coor类
class Coor
{
    // 数据成员
    public int x;
    public int y;

    // 构造函数
    public Coor()
    {
        x = 0;
        y = 0;
    }

    public Coor(int a, int b)
    {
        x = a;
        y = b;
    }

    public Coor(Pos from)
    {
        Coor t = Utils.posToCoor(from);
        x = t.x;
        y = t.y;
    }

    // 类型转换函数
    public Pos toPos()
    {
        return Utils.coorToPos(this);
    }

    public int toInt()
    {
        return Utils.posToInt(toPos());
    }

    // 操作函数
    public Pos add(Coor other)
    {
        Coor t = new Coor(x + other.x, y + other.y);
        return new Pos(t);
    }

    public boolean equalsTo(Coor coor)
    {
        return Math.sqrt((x - coor.x) * (x - coor.x) + (y - coor.y) * (y - coor.y)) <= Settings.EQUAL_LIMIT;
    }
}
