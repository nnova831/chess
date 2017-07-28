import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class Piece {
	
	int row; //row on the board (FORMAL so 8 - 1)
	Column col; 
	Color color;
	pieceType type;
	ArrayList <Location> possibleMoves = new ArrayList<>();
	int index;
	
	public Piece(int row, Column col, Color color, pieceType type)
	{
		this.color  = color;
		this.type = type;
		this.row = row;
		this.col = col;
		this.index = findIndex();
	}
	
	public int findIndex() {
		return this.col.getIndex(row);
	}

	//isLegal: takes a piece of destination and determines if this piece is allowed to make that move
  	public boolean isLegal(Piece that, HashMap<Integer, Piece> b) {
  		if(this.possibleMoves.contains(new Point(that.col.getX(), (8 - that.row))))
  		{
  			return true;
  		}
  		else
  		{
  			switch (this.type) 
  			{
  			case KNIGHT:
  				return this.KnightLegal(that);
  			case KING: 
  				return this.KingLegal(that);
  			case ROOK:
  				return this.RookLegal(that, b);
  			default:
  				return false;
  			}
  		}
  	}
	
  	//returns true if this piece can move to that piece as assuming this is a bishop
	private boolean BishopLegal(Piece that, HashMap<Integer, Piece> brd)
	{
		int upR, upL, dwnR, dwnL;
		Piece p = this;
		return false;
	}
	
	//returns true if this piece can move to that piece as assuming this is a knight
	private boolean KnightLegal(Piece that)
	{
		if(Math.abs(that.col.getX() - this.col.getX()) == 1 && Math.abs(that.row - this.row) == 2)
		{
			if(this.color == that.color)
			{
				return false;
			}else
			{
				return true;
			}
		}
		else if(Math.abs(that.col.getX() - this.col.getX()) == 2 && Math.abs(that.row - this.row) == 1)
		{
			if(this.color == that.color)
			{
				return false;
			}else
			{
				return true;
			}
		}
		return false;
	}

	//returns true if this piece can move to that piece as assuming this is a king
	private boolean KingLegal(Piece that)
	{
		if(Math.abs(that.col.getX() - this.col.getX()) <= 1 && Math.abs(that.row - this.row) <= 1)
		{
			if(this.color != that.color)
			{
				return true;
			}
		}
		return false;
	}
	
	//returns true if this piece can move to that piece as assuming this is a rook
	private boolean RookLegal(Piece that, HashMap <Integer, Piece> brd)
	{
		int a, b;
		
		if((that.col.getX() - this.col.getX()) == 0)
		{
			if((that.row - this.row) < 0) //straight up
			{
				a = 0;
				b = -1;
			}else if (that.row - this.row > 0) //straight down
			{
				a = 0;
				b = 1;
			}
			else
			{
				return this.color != that.color;
			}
		}	
		else if((that.row - this.row) == 0)
		{
			if((that.col.getX() - this.col.getX()) < 0) //left
			{
				a = -1;
				b = 0;
			}else //right
			{
				a = 1;
				b = 0;
			}
		}
		else
		{
			return false;
		}
		
		for (int i = 0; i < brd.size(); i++)
		{
			if (brd.get(i).equalsCoord(this.col.getX() + a, this.row + b))
			{
				if(brd.get(i).type != pieceType.EMPTY && !brd.get(i).equals(that)){
					return false;
				}
				this.col = brd.get(i).col;
				this.row = brd.get(i).row;
			}
		}
		return this.RookLegal(that, brd);
	}

	// OVERRIDE: prints out the index in array (which is converted to an 
	// int later to find the corresponding Piece) instead of memory address
	public String toStringIndex()
	{
		StringBuilder sb = new StringBuilder ();
		sb.append(this.index);
		return sb.toString();
	}

	public String toStringPossibleMoves ()
	{
		StringBuilder sb = new StringBuilder (this.type.toString() + " (" + this.col + "" + this.row + ")   Poss Moves: ");
		for (int i = 0; i < possibleMoves.size(); i++)
		{
			sb.append(this.possibleMoves.get(i));
		}
		return sb.toString();
	}
	
	//returns true if this and comparable have equal x and y's
	public boolean equalsCoord(Piece comparable)
	{
		return (this.col.getX() == comparable.col.getX() && this.row == comparable.row);
	}
	
	//returns true if this has the same x as i and the same y as j
	public boolean equalsCoord(int i, int j) {
		return this.col.getX() == i && this.row == j;
	}
	
	//OVERRIDES the equals method so we can compare temporary objects to the original object in the array
	public boolean equals (Piece comparable)
	{
		return (this.color == comparable.color &&
				this.index == comparable.index &&
				this.type == comparable.type &&
				this.col.getX() == comparable.col.getX() &&
				this.row == comparable.row);
	}
	
}
