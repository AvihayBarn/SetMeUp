package com.company;

import java.io.*;
import java.util.Arrays;

public class SetMeUpGame
{
	

	private boolean m_with_opean;




	public SetMeUpGame(String input_file)
	{
		try {

			File file = new File(input_file);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			System.out.println("line = "+line);
			AlgorithmsProvider.Algorithm algorithm = AlgorithmsProvider.Algorithm.valueOf(line);

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
			System.out.println(algorithm.GetValue());
			System.out.println(m_with_opean);

			line = reader.readLine();
			System.out.println("line = "+line);
			String[][] Start_State;
			String[][] Finite_State;
			int table_size;
			if(line.equals("small"))
			{
				table_size = 3;
				Start_State = new String[SetMeUpState.TableSize.SMALL.GetValue()][SetMeUpState.TableSize.SMALL.GetValue()];
				Finite_State = new String[SetMeUpState.TableSize.SMALL.GetValue()][SetMeUpState.TableSize.SMALL.GetValue()];
			}
			else
			{
				table_size = 5;
				Start_State = new String[SetMeUpState.TableSize.BIG.GetValue()][SetMeUpState.TableSize.BIG.GetValue()];
				Finite_State = new String[SetMeUpState.TableSize.BIG.GetValue()][SetMeUpState.TableSize.BIG.GetValue()];
			}

			for(int i = 0; i < table_size ;i++)
			{
				line = reader.readLine();
				System.out.println("line = "+line);
				String[] row = line.split(",");
				Start_State[i] = row;
			}
			line = reader.readLine();
			System.out.println("line = "+line);

			for(int i = 0; i < table_size ;i++)
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
