package com.company;

public class AlgorithmsProvider
{
 
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
	
	
	public static AlgorithmsProvider m_algorihtms_provider;
	private Algorithm m_algorithm;
	public SetMeUpState m_goal;
	private int m_num_of_nodes;
	
	private AlgorithmsProvider()
	{
		
	}
	
	public static AlgorithmsProvider GetInstance()
	{
		
		if(m_algorihtms_provider == null)
		{
			m_algorihtms_provider = new AlgorithmsProvider();
		}
		
		return m_algorihtms_provider;
	}
	public void Setup(Algorithm algorithm)
	{

		String details;

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
		return null;
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
	private static AlgorithmDetails DFID(SetMeUpState start)
	{
		return null;
	}
	
}
