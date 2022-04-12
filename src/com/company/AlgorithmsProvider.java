package com.company;

public class AlgorithmsProvider
{
 
	public enum Algorithm{
		BFS,
		AStar,
		IDAStar,
		DFBnb,
		DFID
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
	public void SetUpAndRun() {
		
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
