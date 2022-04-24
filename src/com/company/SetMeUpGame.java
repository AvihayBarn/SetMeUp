package com.company;

import java.io.*;
import java.util.Arrays;

public class SetMeUpGame
{
	

	private boolean m_with_opean;
	private AlgorithmDetails m_detials;



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

			SetMeUpState start = new SetMeUpState(Start_State);
			SetMeUpState goal = new SetMeUpState(Finite_State);

			AlgorithmsProvider algorithmsProvider = AlgorithmsProvider.GetInstance();
			algorithmsProvider.Setup(algorithm);

			m_detials = algorithmsProvider.Run(start,goal);
			m_detials.saveOutput();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	


}
