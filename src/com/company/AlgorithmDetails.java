package com.company;

import java.io.FileWriter;
import java.io.IOException;

public class AlgorithmDetails
{

    private double m_time;
    private int m_cost;
    private int m_nodes_num;
    private String m_path;

    public AlgorithmDetails(double i_time, int i_cost,int i_nodes_num ,String i_path)
    {
        m_time = i_time;
        m_cost = i_cost;
        m_nodes_num = i_nodes_num;
        m_path = i_path;

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
