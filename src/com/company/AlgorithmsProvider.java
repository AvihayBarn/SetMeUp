package com.company;

import java.util.*;

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

	public static Algorithm GetAlgorithm(String algorithm)
	{
		switch(algorithm) {
			case("BFS")   : return Algorithm.BFS;
			case("DFID")  : return Algorithm.DFID;
			case("A*")    : return Algorithm.AStar;
			case("IDA*")  : return Algorithm.IDAStar;
			case("DFBnB") : return Algorithm.DFBnB;
			default: return null;
		}
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

		long start_time , end_time;

		Hashtable<String,SetMeUpState> closed_list = new Hashtable<String,SetMeUpState>();
		Queue<SetMeUpState> open_list = new LinkedList<SetMeUpState>();

		start_time = System.currentTimeMillis();

		open_list.add(start);
		closed_list.put(start.toString(),start);

		while(!open_list.isEmpty())
		{
			if(m_with_open)
			{
				display(open_list);

			}
			SetMeUpState current_state = open_list.remove();

			List<Operator> next_SetMeUpStates = current_state.GetOprators();
			for(Operator operator : next_SetMeUpStates)
			{
				SetMeUpState next_SetMeUpState = current_state.ActivateOperator(operator);



				m_nodes_num++;

				if(!closed_list.containsKey(next_SetMeUpState.toString()))
				{
					if(next_SetMeUpState.equals(m_goal))
					{
						end_time = System.currentTimeMillis();
						return new AlgorithmDetails((double) (end_time - start_time)/1000 , next_SetMeUpState.GetCost(),m_nodes_num,next_SetMeUpState.GetPath());
					}
					open_list.add(next_SetMeUpState);
					closed_list.put(next_SetMeUpState.toString(),next_SetMeUpState);

				}
			}

		}

		end_time = System.currentTimeMillis();
		return AlgorithmDetails.NoPathResult((double) (end_time - start_time)/1000  , m_nodes_num , false);
	}



	private static AlgorithmDetails DFID(SetMeUpState start)
	{
		long start_time , end_time;

		start_time = System.currentTimeMillis();

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

	private static AlgorithmDetails LimitedDFS(SetMeUpState SetMeUpState , int depth , Hashtable<String,SetMeUpState> open_list)
	{
		if(SetMeUpState.equals(m_goal))
		{
			return new AlgorithmDetails(0,SetMeUpState.GetCost(),m_nodes_num,SetMeUpState.GetPath());
		}

		if(depth == 0)
		{
			AlgorithmDetails cut_off_SetMeUpState = AlgorithmDetails.NoPathResult(0 ,m_nodes_num , true);
			return cut_off_SetMeUpState;
		}


		open_list.put(SetMeUpState.toString(),SetMeUpState);
		boolean is_cut_off = false;

		List<Operator> operators = SetMeUpState.GetOprators();

		for(Operator operator : operators)
		{
			SetMeUpState new_SetMeUpState = SetMeUpState.ActivateOperator(operator);
			m_nodes_num++;
			if(!open_list.containsKey(new_SetMeUpState.toString()))
			{
				AlgorithmDetails details = LimitedDFS(new_SetMeUpState ,depth-1 , open_list);

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
		if(m_with_open)
		{
			display(open_list.values());

		}
		open_list.remove(SetMeUpState);

		if(is_cut_off)
		{
			return AlgorithmDetails.NoPathResult(0,m_nodes_num,true);
		}

		return AlgorithmDetails.NoPathResult(0,m_nodes_num,false);
	}



	private static AlgorithmDetails ASTAR(SetMeUpState start)
	{
		long start_time , end_time;

		PriorityQueue<SetMeUpState> priority_queue = GetNewStatesQueue();
		Hashtable<String, SetMeUpState> open_list = new Hashtable<String, SetMeUpState>();
		Hashtable<String ,SetMeUpState> closed_list = new Hashtable<String, SetMeUpState>();

		start_time = System.currentTimeMillis();

		priority_queue.add(start);
		open_list.put(start.toString(),start);
		

		while(!priority_queue.isEmpty()) {

			if(m_with_open)
			{
				display(priority_queue);
			}
			SetMeUpState current_state = priority_queue.remove();
			
			open_list.remove(current_state.toString());
			if(current_state.equals(m_goal))
			{

				end_time = System.currentTimeMillis();

				return new AlgorithmDetails((double)(end_time - start_time)/1000 , current_state.GetCost() , m_nodes_num , current_state.GetPath() );
			}

			List<Operator> operators = current_state.GetOprators();
			closed_list.put(current_state.toString(),current_state);

			for(Operator operator : operators)
			{
				SetMeUpState new_state = current_state.ActivateOperator(operator);

				m_nodes_num++;

				String new_state_str = new_state.toString();

				if(!closed_list.containsKey(new_state_str) && !open_list.containsKey(new_state_str)) {
					priority_queue.add(new_state);
					open_list.put(new_state_str, new_state);
				}
				// If its in the the open list but with lower evaluation, switch between them
				else if(open_list.containsKey(new_state_str) && Evaluation(open_list.get(new_state_str))>Evaluation(new_state)) {
					priority_queue.remove(open_list.get(new_state_str));
					priority_queue.add(new_state);
					open_list.replace(new_state_str, new_state);
				}
				//if(!closed_list.containsKey(new_SetMeUpState.toString()) &&)
			}


		}
		end_time = System.currentTimeMillis();
		return AlgorithmDetails.NoPathResult((double)(end_time - start_time)/1000 , m_nodes_num,false);
	}



	private static AlgorithmDetails IDAStar(SetMeUpState start)
	{
		System.out.println(start.toString());
		System.out.println();
		System.out.println(m_goal.toString());
		Stack<SetMeUpState> stack = new Stack<SetMeUpState>();
		Hashtable<String, SetMeUpState> open_list = new Hashtable<String,SetMeUpState>();


		long start_time = System.currentTimeMillis() , end_time;
		

		int i = start.Heuristic(m_goal);

		while(i != Integer.MAX_VALUE) {
			int minF = Integer.MAX_VALUE;
			System.out.println("i = "+i);
			System.out.println("minF = "+minF);
			start.SetIsOutOfOpenList(false);
			stack.add(start);
			open_list.put(start.toString(), start);

			while(!stack.isEmpty()) {

				if(m_with_open)
				{
					display(stack);
				}
				SetMeUpState current_state = stack.pop();

				if(current_state.IsOutOfOpenList()) open_list.remove(current_state.toString());
				else {
					current_state.SetIsOutOfOpenList(true);
					stack.add(current_state);
					List<Operator> operators = current_state.GetOprators();
					for(Operator operator : operators)
					{
						System.out.println(operator.toString());
					}
					for(Operator operator : operators) {

						SetMeUpState new_state = current_state.ActivateOperator(operator);

						m_nodes_num++;

						String new_state_str = new_state.toString();

						if(Evaluation(new_state) > i) {
							minF = Math.min(minF, Evaluation(new_state));
							System.out.println("minF = "+minF);
							continue;
						}
						if(open_list.containsKey(new_state_str) && open_list.get(new_state_str).IsOutOfOpenList())
							continue;
						if(open_list.containsKey(new_state_str) && !open_list.get(new_state_str).IsOutOfOpenList()) {

							if(Evaluation(open_list.get(new_state_str)) > Evaluation(new_state)) {
								stack.remove(open_list.get(new_state_str));
								open_list.remove(new_state_str);
							}
							else continue;
						}
						if(new_state.equals(m_goal)) {

							end_time = System.currentTimeMillis();
							return new AlgorithmDetails((double) (end_time - start_time)/1000 , new_state.GetCost(),m_nodes_num , new_state.GetPath());
						}

						stack.add(new_state);
						open_list.put(new_state_str, new_state);
					}
				}
			}

			i = minF;
			System.out.println("i = "+i);
		}
		end_time = System.currentTimeMillis();
		return AlgorithmDetails.NoPathResult((double)(end_time - start_time)/1000 , m_nodes_num,false);
	}



	private static AlgorithmDetails DFBnB(SetMeUpState start)
	{

		long startTime = System.currentTimeMillis() , end_time;

		Stack<SetMeUpState> stack = new Stack<SetMeUpState>();
		Hashtable<String, SetMeUpState> open_list = new Hashtable<String, SetMeUpState>();

		stack.add(start);
		open_list.put(start.toString(),start);

		SetMeUpState goal = null;
		int threshold = start.GetMaxThreshold();


		while(!stack.isEmpty())
		{
			if(m_with_open)
			{
				display(stack);
			}
			SetMeUpState curernt_state = stack.pop();

			if(curernt_state.IsOutOfOpenList())
			{
				open_list.remove(curernt_state.toString());
			}
			else
			{
				 curernt_state.SetIsOutOfOpenList(true);
				stack.add(curernt_state);
				LinkedList<SetMeUpState> states = new LinkedList<SetMeUpState>();
				List<Operator> operators = curernt_state.GetOprators();
				for(Operator operator : operators) {
					SetMeUpState new_state = curernt_state.ActivateOperator(operator);
					m_nodes_num++;
					states.add(new_state);
				}

				states.sort(GetComprator());

				int i = 0;
				while (i < states.size())
				{

					SetMeUpState state = states.get(i);
					String new_state_str = state.toString();
					if(Evaluation(state) >= threshold)
					{
						while(i < states.size())
						{
							states.remove(i);
						}

					}
					else if(open_list.containsKey(new_state_str))
					{
						SetMeUpState exist_state = open_list.get(new_state_str);
						if(exist_state.IsOutOfOpenList())
							states.remove(i);
						else
						{

							if(Evaluation(exist_state) <= Evaluation(state))
								states.remove(i);
							else
							{
								stack.remove(exist_state);
								open_list.remove(new_state_str);
							}
						}
					}

					else if(state.equals(m_goal))
					{

						threshold = Evaluation(state);
						goal = state;

						while(i < states.size())
						{
							states.remove(i);
						}

					}
					else i++;
				}

				for(int j = states.size() - 1 ;  j >= 0 ; j--)
				{
					stack.add(states.get(j));
					open_list.put(states.get(j).toString(), states.get(j));
				}
			}
		}

		end_time = System.currentTimeMillis();

		if (goal != null)
		{
			return new AlgorithmDetails((double) (end_time - startTime)/1000 , goal.GetCost() ,m_nodes_num , goal.GetPath());
		}
		return AlgorithmDetails.NoPathResult((double) (end_time - startTime)/1000 ,m_nodes_num , false);
	}





	private static PriorityQueue<SetMeUpState> GetNewStatesQueue()
	{
		return new PriorityQueue<SetMeUpState>(GetComprator());
	}

	private static Comparator<SetMeUpState> GetComprator()
	{
		return new Comparator<SetMeUpState>() {
			@Override
			public int compare(SetMeUpState state1, SetMeUpState state2) {
				int dif = (Evaluation(state1)  -  Evaluation(state2));

				if(dif == 0)
				{
					dif = (state1.GetId() - state2.GetId());
				}

				return dif;
			}
		};
	}
	private static int Evaluation(SetMeUpState state)
	{
		int heurisitc = state.Heuristic(m_goal);
		int current_cost = state.GetCost();
		return (current_cost+heurisitc);
	}
	private static void display(Collection<SetMeUpState> states)
	{
		for (SetMeUpState state : states)
		{
			System.out.println();
			System.out.println();
			
			System.out.println(state.toString());
			System.out.println("id = "+state.GetId());
			System.out.println("parent id = "+state.GetParentId());
			if(state.GetLastOperator() != null)
			{
				System.out.println(state.GetLastOperator().toString());
			}
			System.out.println();
			System.out.println();
		}

		System.out.println("__________________________________________________");
	}


	
}
