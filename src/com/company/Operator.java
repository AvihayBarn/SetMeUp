package com.company;

import java.awt.*;

public class Operator
{



    private Point m_start_point;
    private Point m_end_point;
    private SetMeUpState.MarbleColor m_color;



    public Operator(Point i_start_point , Point i_end_point , SetMeUpState.MarbleColor i_color)
    {
        m_start_point = i_start_point;
        m_end_point = i_end_point;
        m_color = i_color;

    }




    public Point GetStartPoint()
    {
        return m_start_point;
    }




    public Point GetEndPoint()
    {
        return m_end_point;
    }




    public int GetCost()
    {
        return m_color.GetValue();
    }



    public SetMeUpState.MarbleColor GetMarbleColor()
    {
        return m_color;
    }



    public boolean isReverseOpertorOf(Operator operator)
    {
        boolean first_cond = this.m_start_point.equals(operator.m_end_point);
        boolean second_cond = this.m_end_point.equals(operator.m_start_point);

        if(first_cond && second_cond)
        {
            return true;
        }

        return false;

    }


    @Override
    public String toString()
    {
        return "("+m_start_point.x+","+m_start_point.y+")"+":"+m_color+":"+"("+m_end_point.x+","+m_end_point.y+")";

    }

}
