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

    @Override
    public String toString()
    {
        return m_start_point.toString()+":"+m_color+":"+m_end_point.toString();

    }

}
