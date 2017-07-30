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
		this.index = colToIndex();
	}
	
	//isLegal: takes a piece of destination and determines if this piece is allowed to make that move
  	public boolean isLegal(Piece that, HashMap<Integer, Piece> b) {
  		System.out.println("New Method");
  		for (int i = 0; i < this.possibleMoves.size(); i++)
  		{
  			if (this.possibleMoves.get(i).col == that.col && this.possibleMoves.get(i).row == that.row)
  			{
	  			return true;
  			}
  		}
  		return false;
  	}
  	
  	
  	public ArrayList<Location> setPossibleMovesArray (HashMap<Integer, Piece> brd) // Given a piece, return all possible moves
	{
		Piece p = this;
		switch (p.type) {
			case KNIGHT:
				return setKnightArray(p, brd);
			case KING:
				return setKingArray (p, brd);
			default:
				break;
			}    
		return null;
	}

  	private ArrayList <Location> setKingArray (Piece moving, HashMap<Integer, Piece> brd)
  	{
  		ArrayList<Location> arr = new ArrayList<>();
  		for (int i = 0; i < 64; i++)
  		{
  			Piece dest = brd.get(i);
	  		if(Math.abs(moving.col.getX() - dest.col.getX()) <= 1 && 
	  			Math.abs(moving.row - dest.row) <= 1)
			{
				if(dest.color != moving.color)
				{
					arr.add(new Location(dest.col, dest.row));
				}
			}
  		}
  		return arr;
  	}
  	
	private ArrayList <Location> setKnightArray (Piece movingPiece, HashMap <Integer, Piece> brd)
	{
  		ArrayList <Location> arr = new ArrayList<>();
		for (int i = 0; i < 64; i++)
		{
			Piece dest = brd.get(i);
			if (dest.equalsCoord(movingPiece.col.getX() + 1, movingPiece.row + 2)
				|| dest.equalsCoord(movingPiece.col.getX() + 2, movingPiece.row + 1)
				|| dest.equalsCoord(movingPiece.col.getX() + 1, movingPiece.row - 2)
				|| dest.equalsCoord(movingPiece.col.getX() + 2, movingPiece.row - 1)
				|| dest.equalsCoord(movingPiece.col.getX() - 1, movingPiece.row + 2)
				|| dest.equalsCoord(movingPiece.col.getX() - 2, movingPiece.row + 1)
				|| dest.equalsCoord(movingPiece.col.getX() - 1, movingPiece.row - 2)
				|| dest.equalsCoord(movingPiece.col.getX() - 2, movingPiece.row - 1))
			{
				if (movingPiece.color != dest.color)
				{
					arr.add(new Location(dest.col, dest.row));
				}
			}
		}
//		System.out.println(this.type + " (" + this.col +", "+this.row+")   " + arr);
		return arr;
		
	}

	//returns true if this piece can move to that piece as assuming this is a bishop
	private boolean BishopLegal(Piece that, HashMap<Integer, Piece> brd)
	{
		int upR, upL, dwnR, dwnL;
		Piece p = this;
		return false;
	}
	
	//returns true if this piece can move to that piece as assuming this is a rook
/*	private boolean RookLegal(Piece that, HashMap <Integer, Piece> brd)
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
	}*/
	
	// this = moving piece
	//public void switchPieces (Piece destination, HashMap <Integer, >)
	{
		
	}

	public int colToIndex() 
	{
		return this.col.getIndex(row);
	}

	// prints out the index in array (which is converted to an 
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
	public boolean equalsCoord(int a, int n) 
	{
		return this.col.getX() == a && this.row == n;
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

	public HashMap<Integer,Piece> switchPieces(Piece destinationPiece, HashMap<Integer,Piece> brd) 
	{
		Piece movingPiece = this;
		Piece movTemp = this;
		
		movingPiece.col = destinationPiece.col;
		movingPiece.row = destinationPiece.row;
		
		destinationPiece.col = movTemp.col;
		destinationPiece.row = movTemp.row;
		
		
		
		if (destinationPiece.color != Color.BLUE)
		{
			System.out.println("REMOVED");
			brd.remove(movTemp);
		}
		return brd;
	}
}
