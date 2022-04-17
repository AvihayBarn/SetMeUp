package com.company;

import java.io.*;
import java.util.Arrays;

public class SetMeUpGame
{
	
	
	
	
	public enum TableSize
	{
		BIG(5),
		SMALL(3);
		private int m_value;
		private TableSize(int i_value)
		{
			m_value = i_value;
		}
		public int GetValue()
		{
			return m_value;
		}
		
	}
	public enum MarbleColor
	{
		GREEN,
		BLUE,
		YELLOW,
		NONE
	}
	
	
	
	
	
	
	private MarbleColor[][] m_board;
	private AlgorithmsProvider m_algorithms_provider;
	private AlgorithmsProvider.Algorithm m_algorithm;

	private TableSize m_table_size;
	private boolean m_with_opean;




	public SetMeUpGame(String input_file)
	{
		try {

			File file = new File(input_file);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			System.out.println("line = "+line);
			m_algorithm = AlgorithmsProvider.Algorithm.valueOf(line);

			line = reader.readLine();
			System.out.println("line = "+line);
			if(line.equals("no open"))
			{
				m_with_opean = false;
			}
			else
			{
				m_with_opean = true;
			}
			System.out.println(m_algorithm.GetValue());
			System.out.println(m_with_opean);

			line = reader.readLine();
			System.out.println("line = "+line);
			String[][] Start_State;
			String[][] Finite_State;
			if(line.equals("small"))
			{
				m_table_size = TableSize.SMALL;
				Start_State = new String[TableSize.SMALL.GetValue()][TableSize.SMALL.GetValue()];
				Finite_State = new String[TableSize.SMALL.GetValue()][TableSize.SMALL.GetValue()];
			}
			else
			{
				m_table_size = TableSize.BIG;
				Start_State = new String[TableSize.BIG.GetValue()][TableSize.BIG.GetValue()];
				Finite_State = new String[TableSize.BIG.GetValue()][TableSize.BIG.GetValue()];
			}

			for(int i = 0; i < m_table_size.GetValue() ;i++)
			{
				line = reader.readLine();
				System.out.println("line = "+line);
				String[] row = line.split(",");
				Start_State[i] = row;
			}
			line = reader.readLine();
			System.out.println("line = "+line);

			for(int i = 0; i < m_table_size.GetValue() ;i++)
			{
				line = reader.readLine();
				System.out.println("line = "+line);
				String[] row = line.split(",");
				Finite_State[i] = row;
			}

			Print2DArray(Start_State);
			Print2DArray(Finite_State);






		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public String toString()
	{
		return null;
	}

	public static void Print2DArray(Object[][] arr)
	{
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		for (int i=0;i<arr.length;i++){
			for (int j=0;j<arr[0].length;j++){
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
	}
}
