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

			SetMeUpState current_state = open_list.remove();

			List<Operator> next_SetMeUpStates = current_state.GetOprators();
			for(Operator operator : next_SetMeUpStates)
			{
				SetMeUpState next_SetMeUpState = current_state.ActivateOperator(operator);

				if(m_with_open)
				{
					display(next_SetMeUpState,operator);

				}

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
				display(new_state,operator);
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
						display(new_state,operator);
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
		return null;
	/*	long startTime = System.currentTimeMillis() , end_time;

		Stack<SetMeUpState> stack = new Stack<SetMeUpState>();
		Hashtable<String, SetMeUpState> open_list = new Hashtable<String, SetMeUpState>();

		stack.add(start);
		open_list.put(start.toString(),start);

		List<SetMeUpState> resultPath = null;
		SetMeUpState goal = null;
		int threshold = initialState.maxThreshold();
		List<Operator> operators

		while(!stack.isEmpty()) {

			SetMeUpState curernt_state = stack.pop();

			if(curernt_state.IsOutOfopen_list())
			{
				open_list.remove(curernt_state.toString());
			}
			else {
				 curernt_state.SetIsOutOfopen_list(true);
				stack.add(curernt_state);
				LinkedList<SetMeUpState> operators = new LinkedList<SetMeUpState>();

				for(int i=1; i<=numOperators; i++) {
					SetMeUpState new_state = curre.getOperator(i);
					if(operator != null) operators.add(operator);
				}
				operators.sort(new StateComparator());
				// Cuts all states from the list whose evaluation is greater than the threshold
				int i = 0;
				while (i < operators.size()) {
					//for(int i=0; i<operators.size(); i++) {
					State operator = operators.get(i);
					String new_state_str = operator.toString();
					if(f(operator) >= threshold) {
						while(i < operators.size())
							operators.remove(i);
					}
					else if(open_list.containsKey(new_state_str)) {
						State existOp = open_list.get(new_state_str);
						if(existOp.isOut())
							operators.remove(i);
						else {
							// If its not marked as "out" and with greater evaluation, remove from stack and add later
							if(f(existOp) <= f(operator))
								operators.remove(i);
							else {
								stack.remove(existOp);
								open_list.remove(new_state_str);
							}
						}
					}
					// If we reached here, f(operator)<threshold
					else if(operator.IsGoal()) {
						threshold = f(operator);
						resultPath = findPath(operator);
						goal = operator;
						// Cuts all states from the list whose evaluation is greater than the goal
						while(i < operators.size())
							operators.remove(i);
					}
					else i++;
				}
				// Add the entire remaining list to the stack in reverse order
				for(int j=operators.size()-1; j>=0; j--) {
					stack.add(operators.get(j));
					open_list.put(operators.get(j).toString(), operators.get(j));
				}
			}
		}
		// Stop time and return all best solution found
		double time = (double) (System.currentTimeMillis() - startTime) / 1000;
		return new SearchInfo(resultPath, goal.getCost(), time);*/
	}





	private static PriorityQueue<SetMeUpState> GetNewStatesQueue()
	{
		return new PriorityQueue<SetMeUpState>(new Comparator<SetMeUpState>() {
			@Override
			public int compare(SetMeUpState state1, SetMeUpState state2) {
				int dif = (Evaluation(state1)  -  Evaluation(state2));

				if(dif == 0)
				{
					dif = (state1.GetId() - state2.GetId());
				}

				return dif;
			}
		});
	}
	private static int Evaluation(SetMeUpState state)
	{
		int heurisitc = state.Heuristic(m_goal);
		int current_cost = state.GetCost();
		return (current_cost+heurisitc);
	}
	private static void display(SetMeUpState state , Operator operator)
	{
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(state.toString());
		System.out.println("id = "+state.GetId());
		System.out.println("parent id = "+state.GetParentId());
		System.out.println(operator.toString());
		System.out.println();
		System.out.println();
		System.out.println("__________________________________________________");
	}


	
}
