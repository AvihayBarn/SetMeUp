package com.company;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AlgorithmsProvider
{

	//Enum of algorithm
 
	public enum Algorithm
	{
		BFS ("BFS"),
		AStar ("A*"),
		IDAStar ("IDA*"),
		DFBnB ("DFBnB"),
		DFID  ("DFID");
		private String m_value;
		private Algorithm(String i_value)
		{
			m_value = i_value;
		}
		public String GetValue()
		{
			return m_value;
		}
	}
	


	//Fields

	private static AlgorithmsProvider m_algorithms_provider;
	private static boolean m_with_open;
	private static SetMeUpState m_goal;
	private Algorithm m_algorithm;
	private static int m_nodes_num;




	private AlgorithmsProvider()
	{
		m_nodes_num = 0;
	}



	public static AlgorithmsProvider GetInstance()
	{
		
		if(m_algorithms_provider == null)
		{
			m_algorithms_provider = new AlgorithmsProvider();
		}
		
		return m_algorithms_provider;
	}


	//Set up the algorithm that will find the path

	public void Setup(Algorithm algorithm , boolean i_with_open)
	{
		m_with_open = i_with_open;
		m_nodes_num = 0;
		switch (algorithm)
		{
			case BFS:     m_algorithm = Algorithm.BFS;   break;
			case AStar:   m_algorithm = Algorithm.AStar;   break;
			case DFID:    m_algorithm = Algorithm.DFID;   break;
			case IDAStar: m_algorithm = Algorithm.IDAStar;  break;
			case DFBnB:   m_algorithm = Algorithm.DFBnB;   break;

		}
	}



	public AlgorithmDetails Run(SetMeUpState start , SetMeUpState goal) throws Exception {

		m_goal = goal;
		switch (m_algorithm)
		{
			case BFS:
				return BFS(start);
			case AStar:
				return ASTAR(start);
			case DFID:
				return DFID(start);
			case IDAStar:
				return IDAStar(start);
			case DFBnB:
				return DFBnB(start);
			default:
				throw new Exception("The algorithm that you chose is invalid");

		}
	}




	private static AlgorithmDetails BFS(SetMeUpState start)
	{

		long start_time = System.currentTimeMillis() , end_time;

		Hashtable<String,SetMeUpState> closed_list = new Hashtable<String,SetMeUpState>();
		Queue<SetMeUpState> open_list = new LinkedList<SetMeUpState>();

		open_list.add(start);
		closed_list.put(start.toString(),start);

		while(!open_list.isEmpty())
		{

			SetMeUpState current_state = open_list.remove();

			List<Operator> next_states = current_state.GetOprators();
			for(Operator operator : next_states)
			{
				SetMeUpState next_state = current_state.ActivateOperator(operator);

				if(m_with_open)
				{
					display(next_state,operator);

				}

				m_nodes_num++;

				if(!closed_list.containsKey(next_state.toString()))
				{
					if(next_state.equals(m_goal))
					{
						end_time = System.currentTimeMillis();
						return new AlgorithmDetails((double) (end_time - start_time)/1000 , next_state.GetCost(),m_nodes_num,next_state.GetPath());
					}
					open_list.add(next_state);
					closed_list.put(next_state.toString(),next_state);

				}
			}

		}

		end_time = System.currentTimeMillis();
		return AlgorithmDetails.NoPathResult((double) (end_time - start_time)/1000  , m_nodes_num , false);
	}



	private static AlgorithmDetails DFID(SetMeUpState start)
	{
		long start_time = System.currentTimeMillis() , end_time;
		int nodes_num = 0;


		for(int i = 1; i < Integer.MAX_VALUE ; i++)
		{
			Hashtable<String,SetMeUpState> open_list = new Hashtable<String,SetMeUpState>();
			AlgorithmDetails details = LimitedDFS(start,i,open_list);


			if(!details.GetIsCutOff())
			{
				end_time = System.currentTimeMillis();
				details.SetNodesNum(m_nodes_num);
				details.SetRuntime((double) (end_time - start_time)/1000);

				return details;
			}
		}
		end_time = System.currentTimeMillis();
		return AlgorithmDetails.NoPathResult((double) (end_time - start_time)/1000 ,m_nodes_num , false);

	}

	private static AlgorithmDetails LimitedDFS(SetMeUpState state , int depth , Hashtable<String,SetMeUpState> open_list)
	{
		if(state.equals(m_goal))
		{
			return new AlgorithmDetails(0,state.GetCost(),m_nodes_num,state.GetPath());
		}

		if(depth == 0)
		{
			AlgorithmDetails cut_off_state = AlgorithmDetails.NoPathResult(0 ,m_nodes_num , true);
			return cut_off_state;
		}


		open_list.put(state.toString(),state);
		boolean is_cut_off = false;

		List<Operator> operators = state.GetOprators();

		for(Operator operator : operators)
		{
			SetMeUpState new_state = state.ActivateOperator(operator);
			m_nodes_num++;
			if(!open_list.containsKey(new_state.toString()))
			{
				AlgorithmDetails details = LimitedDFS(new_state ,depth-1 , open_list);

				if(details.GetIsCutOff())
				{
					is_cut_off = true;
				}
				else if(details.HasPath())
				{
					return details;
				}


			}
		}

		open_list.remove(state);

		if(is_cut_off)
		{
			return AlgorithmDetails.NoPathResult(0,m_nodes_num,true);
		}

		return AlgorithmDetails.NoPathResult(0,m_nodes_num,false);
	}



	private static AlgorithmDetails ASTAR(SetMeUpState start)
	{
		return null;
	}



	private static AlgorithmDetails IDAStar(SetMeUpState start)
	{
		return null;
	}



	private static AlgorithmDetails DFBnB(SetMeUpState start)
	{
		return null;
	}






	private static void display(SetMeUpState state , Operator operator)
	{
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(state.toString());
		System.out.println(operator.toString());
		System.out.println();
		System.out.println();
		System.out.println();
	}


	
}
