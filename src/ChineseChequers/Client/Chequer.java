package ChineseChequers.Client;

import ChineseChequers.Client.Settings.*;

import java.awt.*;

// 棋子类
public class Chequer
{
    // 构造函数
    public Chequer()
    {
        team = Team.noTeam;
        status = ChequerStatus.unselected;
    }

    public Chequer(Team t)
    {
        team = t;
        status = ChequerStatus.unselected;
    }

    // 信息函数
    public void setTeam(Team t)
    {
        team = t;
    }

    public void setStatus(ChequerStatus cs)
    {
        status = cs;
    }

    public Team getTeam()
    {
        return team;
    }

    public ChequerStatus getStatus()
    {
        return status;
    }

    // 绘图函数
    public void paint(Graphics2D graphics, Pos pos)
    {
        // 画棋子 (一个)
        if(team == Team.noTeam)
        {
            if(status == ChequerStatus.accessing)
            {
                Coor coor = Utils.posToCoor(pos);
                Color nowColor = new Color(Color.gray.getRed(), Color.gray.getGreen(), Color.gray.getBlue(), 75);
                graphics.setColor(nowColor);
                graphics.fillOval(coor.x-Settings.CHEQUER_RADIUS, coor.y-Settings.CHEQUER_RADIUS, 2* Settings.CHEQUER_RADIUS, 2*Settings.CHEQUER_RADIUS);

                float[] dashPattern = {0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f};
                graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10F /*miter limit */, dashPattern, 0));
                graphics.setColor(Color.red);
                graphics.drawOval(coor.x-Settings.CHEQUER_RADIUS, coor.y-Settings.CHEQUER_RADIUS, 2* Settings.CHEQUER_RADIUS, 2*Settings.CHEQUER_RADIUS);

                graphics.setStroke(new BasicStroke(1));
            }
            return;
        }

        else if(status == ChequerStatus.selected)
        {
            Coor coor_circle = Utils.posToCoor(pos);
            float [] portions = {0f, 1f};
            Color colorA = new Color(Settings.CHEQUER_COLOR[team.ordinal()].getRed(), Settings.CHEQUER_COLOR[team.ordinal()].getGreen(),
                    Settings.CHEQUER_COLOR[team.ordinal()].getBlue(), 120);
            Color [] colors = { Settings.CHEQUER_COLOR[team.ordinal()], colorA };
            RadialGradientPaint rgp = new RadialGradientPaint(coor_circle.x, coor_circle.y, Settings.CHEQUER_RADIUS, portions, colors);

            Paint ex = graphics.getPaint();
            graphics.setPaint(rgp);
            graphics.fillOval(coor_circle.x - Settings.CHEQUER_RADIUS, coor_circle.y - Settings.CHEQUER_RADIUS,
                    Settings.CHEQUER_RADIUS*2, Settings.CHEQUER_RADIUS*2);

            graphics.setPaint(ex);
            graphics.setStroke(new BasicStroke(3));
            graphics.setColor(Color.black);
            graphics.drawOval(coor_circle.x - Settings.CHEQUER_RADIUS, coor_circle.y - Settings.CHEQUER_RADIUS,
                    Settings.CHEQUER_RADIUS*2, Settings.CHEQUER_RADIUS*2);

            graphics.setStroke(new BasicStroke(1));
        }

        else if(status == ChequerStatus.unselected)
        {
            Coor coor = Utils.posToCoor(pos);
            graphics.setColor(Color.black);
            graphics.setStroke(new BasicStroke(3));
            graphics.drawOval(coor.x - Settings.CHEQUER_RADIUS, coor.y - Settings.CHEQUER_RADIUS,
                    2*Settings.CHEQUER_RADIUS, 2*Settings.CHEQUER_RADIUS);
            graphics.setColor(Settings.CHEQUER_COLOR[team.ordinal()]);
            graphics.setStroke(new BasicStroke(3));
            graphics.fillOval(coor.x - Settings.CHEQUER_RADIUS, coor.y - Settings.CHEQUER_RADIUS,
                    2*Settings.CHEQUER_RADIUS, 2*Settings.CHEQUER_RADIUS);
        }
    }

    // 数据成员
    private Team team;
    private ChequerStatus status;
}
