package com.company;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SetMeUpState
{



	private class Tile
	{

		Point m_position;
		MarbleColor m_color;

		public Tile(Point i_position , MarbleColor i_color)
		{
			m_position = i_position;
			m_color = i_color;
		}

		public Point GetPosition()
		{
			return m_position;
		}

		public MarbleColor GetColor()

		{
			return m_color;
		}

	}
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
	private int m_cost;
	private int m_id;
	private boolean m_is_out_of_open_list;

	private MarbleColor[][] m_board;
	private SetMeUpState m_parnet;
	private String m_path;
	private Operator m_last_operator;
	private HashMap<MarbleColor,ArrayList<Tile>> m_full_tiles;
	private final static MarbleColor[] m_colors = {MarbleColor.B , MarbleColor.G , MarbleColor.Y , MarbleColor.R};
	private static int state_id = 1;

	





	public SetMeUpState(String[][] i_board)
	{
		InitializeBoard(i_board);
		m_parnet = null;
		m_cost = 0;
		m_last_operator = null;
	}




	public SetMeUpState(MarbleColor[][] i_board , int i_cost , SetMeUpState i_parent , Operator i_last_operator)
	{
		m_parnet = i_parent;
		m_last_operator = i_last_operator;
		m_board = i_board;
		m_cost = i_cost;
		m_id = state_id++;
		UpdateFullTilesList();
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

		SetMeUpState new_state = new SetMeUpState(new_board ,m_cost + operator.GetCost() ,this,operator);
		return new_state;
	}



	public int GetCost()
	{
		return  m_cost;
	}





	public String GetPath()
	{
		String path = "";
		path = m_last_operator.toString();
		SetMeUpState parent = m_parnet;
		while(parent != null)
		{
			if(parent.m_last_operator != null)
			{
				path = parent.m_last_operator.toString()+"--"+path;
			}

			parent = parent.m_parnet;
		}

		return path;
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
		UpdateFullTilesList();
	}

	private void UpdateFullTilesList()
	{
		m_full_tiles = new HashMap<MarbleColor,ArrayList<Tile>>();
		ArrayList<Tile> blues = new ArrayList<Tile>();
		ArrayList<Tile> yellows = new ArrayList<Tile>();
		ArrayList<Tile> reds = new ArrayList<Tile>();
		ArrayList<Tile> greens = new ArrayList<Tile>();
		for (int i = 1 ; i < m_board.length ; i++)
		{
			for (int j = 1 ; j < m_board.length ; j++)
			{
				switch (m_board[i][j])
				{
					case Y: yellows.add(new Tile(new Point(i,j),m_board[i][j]));break;
					case B: blues.add(new Tile(new Point(i,j),m_board[i][j]));break;
					case R: reds.add(new Tile(new Point(i,j),m_board[i][j]));break;
					case G: greens.add(new Tile(new Point(i,j),m_board[i][j]));break;
					case N: break;
				}
			}
		}
		m_full_tiles.put(MarbleColor.B , blues);
		m_full_tiles.put(MarbleColor.Y , yellows);
		m_full_tiles.put(MarbleColor.R , reds);
		m_full_tiles.put(MarbleColor.G , greens);
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

	public boolean IsOutOfOpenList()
	{
		return m_is_out_of_open_list;
	}

	public void SetIsOutOfOpenList(boolean is_out_of_open_list)
	{
		m_is_out_of_open_list = is_out_of_open_list;
	}

	public int GetMaxThreshold()
	{
		int size = m_board.length;
		int possibilities = factorial(size*size);
		return Math.min(possibilities , Integer.MAX_VALUE);
	}

	public Operator GetLastOperator()
	{
		return m_last_operator;
	}

	private int factorial(int num) {
		//if num = 13 its above MAX_VALUE
		if(num > 12)
		{
			return Integer.MAX_VALUE;
		}
		int factorial = 1;
		for (int i = 2; i <= num; i++)
		{
			factorial = factorial * i;
		}
		return factorial;
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

	public int Heuristic(SetMeUpState end_state)
	{


			return ComplexHeuristic(end_state);

	}

	private int ComplexHeuristic(SetMeUpState end_state)
	{
		int min_general_dist = 0;
		for(int color = 0 ; color < m_colors.length ; color++)
		{

			MarbleColor tile_color = m_colors[color];
			ArrayList<Tile> current_state_tiles = this.m_full_tiles.get(tile_color);
			ArrayList<Tile> end_state_tiles = end_state.m_full_tiles.get(tile_color);
			if(current_state_tiles.size()>0)
			{
				int[][] dists_matrix = new int[current_state_tiles.size()][current_state_tiles.size()];

				for (int i = 0 ; i < dists_matrix.length; i++)
				{
					for (int j = 0 ; j < dists_matrix[0].length ; j++)
					{
						Point src_point = current_state_tiles.get(i).m_position;


						Point dest_point = end_state_tiles.get(j).m_position;

						int x_dist = Math.abs(src_point.x - dest_point.x);
						int y_dist = Math.abs(src_point.y - dest_point.y);
						int dist = (x_dist + y_dist) * m_colors[color].m_value;

						dists_matrix[i][j] = dist;
					}
				}



				min_general_dist += GetMinimumMatch(dists_matrix);
			}


		}
		return min_general_dist;
	}

	private static int GetMinimumMatch(int[][] dists_matrix)
	{

		return GetMinimumMatch(dists_matrix,new ArrayList<Integer>(),0);

	}
	private static int GetMinimumMatch(int[][] dists_matrix ,ArrayList<Integer> i_indexes , int j)
	{

		if(j >= dists_matrix[0].length)
		{
			return 0;
		}
		int min_dest = Integer.MAX_VALUE;
		for(int i = 0; i < dists_matrix.length ; i++)
		{
			if(!i_indexes.contains(i))
			{
				ArrayList new_i_indexes = new ArrayList<Integer>(i_indexes);
				new_i_indexes.add(i);
				int dist = dists_matrix[i][j] + GetMinimumMatch(dists_matrix,new_i_indexes,j+1);

				if(dist < min_dest)
				{
					min_dest = dist;
				}
			}

		}

		return min_dest;

	}

	public int GetId()
	{
		return m_id;
	}

	public int GetParentId()
	{
		if(m_parnet == null)
		{
			return 0;
		}
		return m_parnet.m_id;
	}

	private int SimpleHeuristic(SetMeUpState end_state)
	{
	 	int heuristic = 0;
		for(int color = 0 ; color < m_colors.length ; color++)
		{

			ArrayList<Tile> src_tiles = this.m_full_tiles.get(m_colors[color]);
			ArrayList<Tile> dest_tiles = end_state.m_full_tiles.get(m_colors[color]);

			if(src_tiles != null && dest_tiles!= null&& src_tiles.size() > 0)
			{

				for (int i = 0; i < src_tiles.size() ; i++)
				{
					int min_dist = Integer.MAX_VALUE;
					for( int j = 0 ; j< dest_tiles.size() ; j++)
					{
						Point src_point = src_tiles.get(i).m_position;


						Point dest_point = dest_tiles.get(j).m_position;

						int x_dist = Math.abs(src_point.x - dest_point.x);
						int y_dist = Math.abs(src_point.y - dest_point.y);
						System.out.println("X DIST :"+x_dist);
						System.out.println("Y DIST :"+y_dist);
						int dist = (x_dist + y_dist) * m_colors[color].m_value;

						if(dist < min_dist)
						{
							min_dist = dist;
						}
					}

					heuristic += min_dist;
				}
			}

		}



		System.out.println(heuristic);
		return heuristic;

	}
}
