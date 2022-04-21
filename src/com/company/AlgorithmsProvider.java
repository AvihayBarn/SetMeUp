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
	private int m_num_of_nodes;
	
	private AlgorithmsProvider()
	{
		
	}
	
	public AlgorithmsProvider GetInstance()
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

	public void Run()
	{
		switch (m_algorithm)
		{
			case BFS:     BFS();   break;
			case AStar:   ASTAR();   break;
			case DFID:    DFID();   break;
			case IDAStar: IDAStar();  break;
			case DFBnB:   DFBnB();   break;

		}
	}

	private static AlgorithmDetails BFS()
	{
		return null;
	}
	private static AlgorithmDetails ASTAR()
	{
		return null;
	}
	private static AlgorithmDetails IDAStar()
	{
		return null;
	}
	private static AlgorithmDetails DFBnB()
	{
		return null;
	}
	private static AlgorithmDetails DFID()
	{
		return null;
	}
	
}
