package com.company;

public class SetMeUpState implements GameState
{
	enum Costs
	{
		Green(10),
		Blue(2),
		Yellow(1),
		Red(1);
		private int m_value;
		Costs(int i_value)
		{
			m_value = i_value;
		}

		public int GetValue()
		{
			return m_value;
		}
	}
	private String[][] board;

	public SetMeUpState()
	{

	}

}
