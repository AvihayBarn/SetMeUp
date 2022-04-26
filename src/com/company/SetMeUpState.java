package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetMeUpState
{



	public enum TableSize
	{
		BIG(5),
		SMALL(3);
		private final int m_value;
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
		private final int m_value;
		MarbleColor(int i_value)
		{
			m_value = i_value;
		}

		public int GetValue()
		{
			return m_value;
		}
	}

	//Fields

	private MarbleColor[][] m_board;
	private SetMeUpState m_parnet;
	private int m_cost;
	private String m_path;
	private Operator m_last_operator;
	


	public SetMeUpState(String[][] i_board)
	{
		InitializeBoard(i_board);
		m_parnet = null;
		m_cost = 0;
		m_path = "";
		m_last_operator = null;
	}



	public SetMeUpState(MarbleColor[][] i_board , int i_cost , String i_path , SetMeUpState i_parent , Operator i_last_operator)
	{
		m_parnet = i_parent;
		m_last_operator = i_last_operator;
		m_board = i_board;
		//System.arraycopy(i_board,0,m_board,0,i_board.length);
		m_cost = i_cost;
		m_path = i_path;
	}



	public List<Operator> GetOprators()
	{
		List<Operator> operators = new ArrayList<Operator>();
		for(int i = 1 ; i < m_board.length ; i++)
		{
			for(int j = 1 ; j < m_board.length ; j++)
			{
				if(m_board[i][j] != MarbleColor.N)
				{
					Point start_point = new Point(i,j);

					if((j - 1 > 0) && m_board[i][j-1] != null && m_board[i][j-1].equals(MarbleColor.N))
					{
						Operator operator = new Operator(start_point , new Point(i,j-1),m_board[i][j]);

						if(isValidOperator(operator)) operators.add(operator);
					}

					if((i - 1 > 0) && m_board[i-1][j] != null && m_board[i-1][j].equals(MarbleColor.N) )
					{
						Operator operator = new Operator(start_point , new Point(i-1,j),m_board[i][j]);

						if(isValidOperator(operator)) operators.add(operator);
					}
					if((j < m_board.length-1) && m_board[i][j+1] != null && m_board[i][j+1].equals(MarbleColor.N))
					{
						Operator operator = new Operator(start_point , new Point(i,j+1),m_board[i][j]);

						if(isValidOperator(operator)) operators.add(operator);
					}
					if((i < m_board.length-1) &&m_board[i+1][j] != null && m_board[i+1][j].equals(MarbleColor.N))
					{
						Operator operator = new Operator(start_point , new Point(i+1,j),m_board[i][j]);

						if(isValidOperator(operator)) operators.add(operator);
					}


				}
			}

		}
		return operators;
	}
	public SetMeUpState ActivateOperator(Operator operator)
	{
		MarbleColor[][] new_board = BoardDeepCopy();
		Point start = operator.GetStartPoint() , end = operator.GetEndPoint();

		new_board[start.x][start.y] = MarbleColor.N;
		new_board[end.x][end.y] = operator.GetMarbleColor();
		String current_move = operator.toString();

		if(!m_path.equals(""))
		{
			current_move = "--"+current_move;
		}
		SetMeUpState new_state = new SetMeUpState(new_board ,m_cost + operator.GetCost() ,m_path + current_move,this,operator);
		return new_state;
	}



	public int GetCost()
	{
		return  m_cost;
	}



	public String GetPath()
	{
		return m_path;
	}


	public boolean equals(SetMeUpState state)
	{
		for(int i = 1; i < m_board.length ; i++)
		{
			for(int j = 1; j < m_board.length ; j++)
			{
				if(!m_board[i][j].equals(state.m_board[i][j])) return false;
			}
		}

		return true;
	}



	private void InitializeBoard(String[][] i_board)
	{
		m_board = new MarbleColor[i_board.length+1][i_board.length+1];
		for(int i = 1 ; i < m_board.length ; i++)
		{
			for(int j = 1 ; j < m_board.length ; j++)
			{
				switch (i_board[i-1][j-1])
				{
					case "R":
						m_board[i][j] = MarbleColor.R;
						break;
					case "G":
						m_board[i][j] = MarbleColor.G;
						break;
					case "B":
						m_board[i][j] = MarbleColor.B;
						break;
					case "Y":
						m_board[i][j] = MarbleColor.Y;
						break;

					default:
						m_board[i][j] = MarbleColor.N;

				}
			}
		}
	}



	private boolean isValidOperator(Operator operator)
	{
		if(m_last_operator == null || !(operator.isReverseOpertorOf(m_last_operator)))
		{
			return true;
		}

		return false;
	}

	private MarbleColor[][] BoardDeepCopy()
	{
		MarbleColor[][] copy = new MarbleColor[m_board.length][m_board.length];

		for (int i = 0 ; i < m_board.length ; i++)
		{
			for (int j = 0 ; j < m_board.length ; j++)
			{
				copy[i][j] = m_board[i][j];
			}
		}

		return copy;
	}

	@Override
	public String toString()
	{
		StringBuilder arr = new StringBuilder();
		for(int i = 1; i < m_board.length ; i++)
		{
			for(int j = 1; j < m_board.length ; j++)
			{
				String tile = " "+m_board[i][j].name();

				if(j == 1) tile = tile.substring(1 , tile.length());
				if(j == m_board.length -1) tile += "\n";

				arr.append(tile);

			}



		}

		return arr.toString();

	}
}
