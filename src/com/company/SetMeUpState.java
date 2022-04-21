package com.company;

public class SetMeUpState {

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
		G(10),
		B(2),
		Y(1),
		R(1),
		N(0);
		private int m_value;
		MarbleColor(int i_value)
		{
			m_value = i_value;
		}

		public int GetValue()
		{
			return m_value;
		}
	}


	private MarbleColor[][] m_board;
	private SetMeUpState m_parnet;
	private Operator m_last_operator;

	public SetMeUpState(String[][] i_board)
	{
		m_board = new MarbleColor[i_board.length][i_board.length];


	}
	private void InitializeBoard(String[][] i_board)
	{
		int len = i_board.length;
		for(int i = 0 ; i < len ; i++)
		{
			for(int j = 0 ; j < len ; j++)
			{

			}
		}
	}
}
