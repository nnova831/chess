import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class Piece {
	
	Location Location; 
	Color color;
	pieceType type;
	ArrayList <Location> possibleMoves = new ArrayList<>();
	int index;
	
	public Piece(Location loc, Color color, pieceType type)
	{
		this.color  = color;
		this.type = type;
		this.Location = loc;
		this.index = findIndex();
	}
	
	public int findIndex() {
		return this.Location.getCol().getIndex(Location.getRow());
	}

	//isLegal: takes a piece of destination and determines if this piece is allowed to make that move
  	public boolean isLegal(Piece that, HashMap<Integer, Piece> b) {
  		if(this.possibleMoves.contains(new Point(that.Location.getCol().getX(), (8 - that.Location.getRow()))))
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
		if(Math.abs(that.Location.getCol().getX() - this.Location.getCol().getX()) == 1 && Math.abs(that.Location.getRow() - this.Location.getRow()) == 2)
		{
			if(this.color == that.color)
			{
				return false;
			}else
			{
				return true;
			}
		}
		else if(Math.abs(that.Location.getCol().getX() - this.Location.getCol().getX()) == 2 && Math.abs(that.Location.getRow() - this.Location.getRow()) == 1)
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
		if(Math.abs(that.Location.getCol().getX() - this.Location.getCol().getX()) <= 1 && Math.abs(that.Location.getRow() - this.Location.getRow()) <= 1)
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
		
		if((that.Location.getCol().getX() - this.Location.getCol().getX()) == 0)
		{
			if((that.Location.getRow() - this.Location.getRow()) < 0) //straight up
			{
				a = 0;
				b = -1;
			}else if (that.Location.getRow() - this.Location.getRow() > 0) //straight down
			{
				a = 0;
				b = 1;
			}
			else
			{
				return this.color != that.color;
			}
		}	
		else if((that.Location.getRow() - this.Location.getRow()) == 0)
		{
			if((that.Location.getCol().getX() - this.Location.getCol().getX()) < 0) //left
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
			if (brd.get(i).equalsCoord(this.Location.getCol().getX() + a, this.Location.getRow() + b))
			{
				if(brd.get(i).type != pieceType.EMPTY && !brd.get(i).equals(that)){
					return false;
				}
				this.Location.setCol(brd.get(i).Location.getCol());
				this.Location.setRow(brd.get(i).Location.getRow());
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
		StringBuilder sb = new StringBuilder (this.type.toString() + " (" + this.Location.getCol() + "" + this.Location.getRow() + ")   Poss Moves: ");
		for (int i = 0; i < possibleMoves.size(); i++)
		{
			sb.append(this.possibleMoves.get(i));
		}
		return sb.toString();
	}
	
	//returns true if this and comparable have equal x and y's
	public boolean equalsCoord(Piece comparable)
	{
		return (this.Location.getCol().getX() == comparable.Location.getCol().getX() && this.Location.getRow() == comparable.Location.getRow());
	}
	
	//returns true if this has the same x as i and the same y as j
	public boolean equalsCoord(int i, int j) {
		return this.Location.getCol().getX() == i && this.Location.getRow() == j;
	}
	
	//OVERRIDES the equals method so we can compare temporary objects to the original object in the array
	public boolean equals (Piece comparable)
	{
		return (this.color == comparable.color &&
				this.index == comparable.index &&
				this.type == comparable.type &&
				this.Location.getCol().getX() == comparable.Location.getCol().getX() &&
				this.Location.getRow() == comparable.Location.getRow());
	}
	
}
