package com.company;

public class SetMeUpGame
{
	
	
	
	
	public enum TableSize
	{
		BIG(5),
		SMALL(3);
		private int m_value;
		TableSize(int i_value)
		{
			m_value = i_value;
		}
		public int GetValue()
		{
			return m_value;
		}
		
	}
	public enum MarbleColor
	{
		GREEN,
		BLUE,
		YELLOW,
		NONE
	}
	
	
	
	
	
	
	private MarbleColor[][] m_board;
	private AlgorithmsProvider m_algorithms_provider;
	
	public SetMeUpGame(String input_file)
	{
		
	}
	
	
	@Override
	public String toString()
	{
		return null;
	}

}
