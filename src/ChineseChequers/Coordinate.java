package ChineseChequers;

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

    // 类型转换函数
    public Coor toCoor()
    {
        return Utils.posToCoor(this);
    }

    public int toInt()
    {
        return (group << 16) + num;
    }

};


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

    // 类型转换函数
    public Pos toPos()
    {
        return Utils.coorToPos(this);
    }

    public int toInt()
    {
        return Utils.posToInt(toPos());
    }

};
