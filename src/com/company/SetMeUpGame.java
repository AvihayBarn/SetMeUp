package com.company;

import java.io.*;
import java.util.Arrays;

public class SetMeUpGame
{
	



	private AlgorithmDetails m_detials;



	public SetMeUpGame(String input_file)
	{
		try {

			File file = new File(input_file);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();

			AlgorithmsProvider.Algorithm algorithm = AlgorithmsProvider.Algorithm.valueOf(line);
			boolean with_open;
			line = reader.readLine();

			if(line.equals("no open"))
			{
				with_open = false;
			}
			else
			{
				with_open = true;
			}

			line = reader.readLine();
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
				String[] row = line.split(",");
				Start_State[i] = row;
			}
			line = reader.readLine();


			for(int i = 0; i < table_size ;i++)
			{
				line = reader.readLine();
				String[] row = line.split(",");
				Finite_State[i] = row;
			}

			SetMeUpState start = new SetMeUpState(Start_State);
			SetMeUpState goal = new SetMeUpState(Finite_State);

			AlgorithmsProvider algorithmsProvider = AlgorithmsProvider.GetInstance();
			algorithmsProvider.Setup(algorithm,with_open);

			m_detials = algorithmsProvider.Run(start,goal);
			m_detials.saveOutput();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	


}
