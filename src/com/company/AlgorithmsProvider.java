package com.company;

public class AlgorithmsProvider
{
 
	public enum Algorithm{
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
	public void SetUpAndRun(Algorithm algorithm) {

		String details;

		switch (algorithm)
		{
			case BFS:     break;
			case AStar:   break;
			case DFID:    break;
			case IDAStar: break;
			case DFBnB:   break;

		}
	}
	
	private String BFS()
	{
		return null;
	}
	private String ASTAR()
	{
		return null;
	}
	private String IDAStar()
	{
		return null;
	}
	private String DFBnB()
	{
		return null;
	}
	private String DFID()
	{
		return null;
	}
	
}
