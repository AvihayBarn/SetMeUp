package com.company;

import java.io.FileWriter;
import java.io.IOException;

public class AlgorithmDetails
{



    private String m_time;
    private String m_cost;
    private String m_nodes_num;
    private String m_path;
    private boolean m_cut_off;



    private  AlgorithmDetails(double i_time,int i_nodes_num,boolean i_cut_off)
    {
        m_time = String.valueOf(i_time);
        m_nodes_num = String.valueOf(i_nodes_num);
        m_cut_off = i_cut_off;
        m_path = "no path";
        m_cost = "inf";

    }
    public AlgorithmDetails(double i_time, int i_cost,int i_nodes_num ,String i_path)
    {
        m_time = String.valueOf(i_time);
        m_cost = String.valueOf(i_cost);
        m_nodes_num = String.valueOf(i_nodes_num);
        m_path = i_path;

    }


    public static AlgorithmDetails NoPathResult(double i_time , int i_nodes_num ,boolean i_cut_off)
    {

        return new AlgorithmDetails(i_time,i_nodes_num ,i_cut_off);

    }

    public void SetRuntime(double i_time)
    {
        m_time = String.valueOf(i_time);
    }

    public void SetNodesNum(int i_nodes_num)
    {
        m_nodes_num = String.valueOf(i_nodes_num);
    }

    public int GetNodesNum()
    {
        return Integer.parseInt(m_nodes_num);
    }

    public void SetIsCutOff(boolean i_cut_off)
    {
        m_cut_off = i_cut_off;
    }

    public boolean GetIsCutOff()
    {
        return m_cut_off;
    }
    public boolean HasPath()
    {
        if(m_path.equals("no path"))
        {
            return false;
        }

        return true;
    }





    public void saveOutput()
    {


        try {

            FileWriter writer = new FileWriter("output.txt",false);
            String output = m_path+'\n'+"Num: "+m_nodes_num+'\n'+"Cost: "+m_cost+'\n'+m_time+" seconds";
            writer.write(output);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
