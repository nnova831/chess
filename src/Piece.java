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
		this.index = Index();
	}
	
	//isLegal: takes a piece of destination and determines if this piece is allowed to make that move
  	public boolean isLegal(Piece that, HashMap<Integer, Piece> b) {
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
		switch (this.type) {
			case KNIGHT:
				return setKnightArray (this, brd);
			case KING:
				return setKingArray (this, brd);
			case ROOK:
				return setRookArray (this, brd);
			case BISHOP:
				return setBishopArray (this, brd);
			case PAWN:
				return setPawnArray (this, brd);
			default:
				break;
			}    
		return null;
	}
  	
  	private ArrayList <Location> setRookArray (Piece moving, HashMap <Integer, Piece> brd)
	{
		ArrayList <Location> arr = new ArrayList <>();
		for (int i = 0; i < 64; i++)
		{
			if (brd.get(i).col.getX() == moving.col.getX() || brd.get(i).row == moving.row)
			{
				if (moving.RookLegal(brd.get(i), brd))
				{
					arr.add (new Location (brd.get(i).col, brd.get(i).row));
				}
			}
		}
		return arr;
	}

	//returns true if this piece can move to that piece as assuming this is a rook
	private boolean RookLegal(Piece that, HashMap <Integer, Piece> brd)
	{
		Piece p = new Piece (this.row, this.col, this.color, this.type);
		int a, b;
		
		if((that.col.getX() - p.col.getX()) == 0)
		{
			if((that.row - this.row) < 0) //straight up
			{
				a = 0;
				b = -1;
			}else if (that.row - p.row > 0) //straight down
			{
				a = 0;
				b = 1;
			}
			else
			{
				return p.color != that.color;
			}
		}	
		else if((that.row - p.row) == 0)
		{
			if((that.col.getX() - p.col.getX()) < 0) //left
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
			if (brd.get(i).equalsCoord(p.col.getX() + a, p.row + b))
			{
				if(brd.get(i).type != pieceType.EMPTY && !brd.get(i).equals(that)){
					return false;
				}
				p.col = brd.get(i).col;
				p.row = brd.get(i).row;
			}
		}
		return p.RookLegal(that, brd);
	}

	private ArrayList <Location> setPawnArray (Piece moving, HashMap <Integer, Piece> brd)
  	{
  		ArrayList <Location> arr = new ArrayList <>();
  		for (int i = 0; i < brd.size(); i ++)
  		{
  			if (moving.row + 1 == brd.get(i).row && moving.col == brd.get(i).col)
  			{
  				if(brd.get(i).color == Color.BLUE)
				{
					arr.add(new Location(brd.get(i).col, brd.get(i).row));
				}
  			}
  			if (moving.row + 1 == brd.get(i).row && Math.abs(moving.col.getX() - brd.get(i).col.getX()) == 1)
  			{
  				if (brd.get(i).color != Color.BLUE)
  				{
  					arr.add(new Location(brd.get(i).col, brd.get(i).row));
  				}
  			}
  		}
  		return arr;
  	}
  	
  	private ArrayList <Location> setQueenArray (Piece moving, HashMap <Integer, Piece> brd)
  	{
  		return null;
  	}
  	
  	private ArrayList <Location> setBishopArray (Piece moving, HashMap <Integer, Piece> brd)
  	{
  		return null;
  	}
  	
  	private ArrayList <Location> setKingArray (Piece moving, HashMap<Integer, Piece> brd)
  	{
  		ArrayList<Location> arr = new ArrayList<>();
  		for (int i = 0; i < 64; i++)
  		{
  			Piece dest = brd.get(i);
	  		if(Math.abs(moving.col.getX() - dest.col.getX()) <= 1 
	  				&& Math.abs(moving.row - dest.row) <= 1)
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
		return arr;
	}
	
	//switchPiece: takes the destination piece and an hashmap of the board and returns a board 
	//				where this piece moves to the destination piece.
	public HashMap<Integer,Piece> switchPieces(Piece destinationPiece, HashMap<Integer,Piece> brd) 
	{
		Location movTemp = new Location (this.col, this.row);
		
		this.col = destinationPiece.col;
		this.row = destinationPiece.row;
		
		destinationPiece.col = movTemp.col;
		destinationPiece.row = movTemp.row;
		
		if (destinationPiece.color != Color.BLUE)
		{
			System.out.println("REMOVED");
			brd.remove(destinationPiece.index);
			brd.put(destinationPiece.index, new Piece (destinationPiece.row, 
					destinationPiece.col, Color.BLUE, pieceType.EMPTY));
		}
		return brd;
	}
	
	//returns the index of this piece
	private int Index() 
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
	
	//converts this.color to String
	public String colorToString(){
		Color theColor = this.color;
			if (Color.BLACK.equals(theColor)) 
			{
				return "BLACK";
			} 
			else if (Color.WHITE.equals(theColor)) 
			{				  
				return "WHITE";
			}
			return "BLUE";
	}
	
	//POLYMORPH returns true if this and comparable have equal x and y's
	public boolean equalsCoord(Piece comparable)
	{
		return (this.col.getX() == comparable.col.getX() && this.row == comparable.row);
	}
	
	//POLYMORPH returns true if this has the same x as i and the same y as j
	public boolean equalsCoord(int a, int n) 
	{
		return this.col.getX() == a && this.row == n;
	}
	
	//OVERRIDE the equals method so we can compare temporary objects to the original object in the array
	public boolean equals (Piece comparable)
	{
		return (this.color == comparable.color &&
				this.index == comparable.index &&
				this.type == comparable.type &&
				this.col.getX() == comparable.col.getX() &&
				this.row == comparable.row);
	}
	
}
