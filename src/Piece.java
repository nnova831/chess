import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class Piece {
	
	Location location;
	Color color;
	pieceType type;
	ArrayList <Location> possibleMoves = new ArrayList<>();
	int index;
	
	public Piece (Location location, Color color, pieceType type)
	{
		this.color  = color;
		this.type = type;
		this.location = location;
		this.index = Index();
	}
	
	//isLegal: takes a piece of destination and determines if this piece is allowed to make that move
  	public boolean isLegal(Piece that, HashMap<Integer, Piece> b) {
  		for (int i = 0; i < this.possibleMoves.size(); i++)
  		{
  			if (this.possibleMoves.get(i).col == that.location.col && this.possibleMoves.get(i).row == that.location.row)
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
		case QUEEN:
			return setQueenArray (this, brd);
		}
		return null;
	}
  	
  	private ArrayList <Location> setPawnArray (Piece moving, HashMap <Integer, Piece> brd)
	{
		ArrayList <Location> arr = new ArrayList <>();
		int a = 1;
		int b = 0;
		if (moving.location.row == 2 || moving.location.row == 7)
		{
			b = 1;
		}
		if (moving.color == Color.BLACK)
		{
			a *= -1;
		}
		for (int i = 0; i < brd.size(); i ++)
		{
			if (Math.abs(moving.location.row + a - brd.get(i).location.row) <= b && moving.location.col == brd.get(i).location.col)
			{
				if(brd.get(i).color == Color.BLUE)
				{
					arr.add(new Location(brd.get(i).location.col, brd.get(i).location.row));
				}
			}
			if (moving.location.row + a == brd.get(i).location.row && Math.abs(moving.location.col.getX() - brd.get(i).location.col.getX()) == 1)
			{
				if (moving.color != brd.get (i).color && brd.get(i).color != Color.BLUE)
				{
					arr.add(new Location(brd.get(i).location.col, brd.get(i).location.row));
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
			if (dest.equalsCoord(movingPiece.location.col.getX() + 1, movingPiece.location.row + 2)     	
				|| dest.equalsCoord(movingPiece.location.col.getX() + 2, movingPiece.location.row + 1)					
				|| dest.equalsCoord(movingPiece.location.col.getX() + 1, movingPiece.location.row - 2)	
				|| dest.equalsCoord(movingPiece.location.col.getX() + 2, movingPiece.location.row - 1)	
				|| dest.equalsCoord(movingPiece.location.col.getX() - 1, movingPiece.location.row + 2)	
				|| dest.equalsCoord(movingPiece.location.col.getX() - 2, movingPiece.location.row + 1)	
				|| dest.equalsCoord(movingPiece.location.col.getX() - 1, movingPiece.location.row - 2)	
				|| dest.equalsCoord(movingPiece.location.col.getX() - 2, movingPiece.location.row - 1))	
			{
				if (movingPiece.color != dest.color)
				{
					arr.add(new Location(dest.location.col, dest.location.row));
				}
			}
		}
		return arr;
	}

	private ArrayList <Location> setBishopArray (Piece moving, HashMap <Integer, Piece> brd)
	{
		ArrayList <Location> arr = new ArrayList <>();
		
		ArrayList <Piece> NE = new ArrayList <>();
		ArrayList <Piece> NW = new ArrayList <>();
		ArrayList <Piece> SE = new ArrayList <>();
		ArrayList <Piece> SW = new ArrayList <>();
		
		for (int i = 0; i < 64; i ++)
		{
			Piece pDest = brd.get(i);
			if (pDest.location.col.getX() - moving.location.col.getX() != 0 && pDest.location.row - moving.location.row != 0)
			{
				if ((double)(pDest.location.row - moving.location.row) / (pDest.location.col.getX() - moving.location.col.getX()) == 1)
				{
					if ((pDest.location.col.getX() - moving.location.col.getX() > 0))
					{
						NE.add(pDest);
					}
					else if (pDest.location.col.getX() - moving.location.col.getX() < 0)
					{
						SW.add(pDest);
					}
				}
				if ((double)(pDest.location.row - moving.location.row) / (pDest.location.col.getX() - moving.location.col.getX()) == -1)
				{
					if (pDest.location.col.getX() - moving.location.col.getX() > 0)
					{
						SE.add(pDest);
					}
					else if (pDest.location.col.getX() - moving.location.col.getX() < 0)
					{
						NW.add(pDest);
					}
				}
			}
		}
		NE = sortDiags (NE);
		NW = sortDiags (NW);
		SE = sortDiags (SE);
		SW = sortDiags (SW);
//		for (int i = 0; i < NE.size(); i++)
//		{
//			System.out.println(NE.get(i).location.col +""+NE.get(i).location.row);
//		}
//		for (int i = 0; i < NW.size(); i++)
//		{
//			System.out.println(NW.get(i).location.col +""+NW.get(i).location.row);
//		}
//		for (int i = 0; i < SE.size(); i++)
//		{
//			System.out.println(SE.get(i).location.col +""+SE.get(i).location.row);
//		}
//		for (int i = 0; i < SW.size(); i++)
//		{
//			System.out.println(SW.get(i).location.col +""+SW.get(i).location.row);
//		}
		
		for (int i = 0; i < NE.size(); i ++)
		{
			Piece p = NE.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}

		for (int i = 0; i < NW.size(); i ++)
		{
			Piece p = NW.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}	
		
		for (int i = SE.size() - 1; i >= 0; i --)
		{
			Piece p = SE.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		for (int i = SW.size() - 1; i >= 0; i --)
		{
			Piece p = SW.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		return arr;
	}
  	
  	
  	private ArrayList <Location> setRookArray (Piece moving, HashMap <Integer, Piece> brd)
	{
		ArrayList <Location> arr = new ArrayList <>();
		
		ArrayList <Piece> Up = new ArrayList <>();
		ArrayList <Piece> Down = new ArrayList <>();
		ArrayList <Piece> Left = new ArrayList <>();
		ArrayList <Piece> Right = new ArrayList <>();
		
		for (int i = 0; i < 64; i ++)
		{
			Piece pDest = brd.get(i);
			if (pDest.location.col.getX() == moving.location.col.getX())
			{
				if (pDest.location.row > moving.location.row)
				{
					Up.add(pDest);
				}
				else if (pDest.location.row < moving.location.row)
				{
					Down.add (pDest);
				}
			}
			else if (pDest.location.row == moving.location.row)
			{
				if (pDest.location.col.getX() < moving.location.col.getX())
				{
					Left.add(pDest);
				}
				else if (pDest.location.row < moving.location.row)
				{
					Right.add(pDest);
				}
			}
		}
		Up = sortStraights (Up);
		Down = sortStraights (Down);
		Left = sortStraights (Left);
		Right = sortStraights (Right);
		
		for (int i = 0; i < Up.size(); i ++)
		{
			Piece p = Up.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		
		for (int i = Down.size() - 1; i >= 0; i --)
		{
			Piece p = Down.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		for (int i = Left.size() - 1; i >= 0; i --)
		{
			Piece p = Left.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		for (int i = 0; i < Right.size(); i ++)
		{
			Piece p = Right.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}	
		return arr;
	}

	private ArrayList <Location> setQueenArray (Piece moving, HashMap <Integer, Piece> brd)
	{
		ArrayList <Location> arr = new ArrayList <>();
		
		ArrayList <Piece> N = new ArrayList <>();
		ArrayList <Piece> E = new ArrayList <>();
		ArrayList <Piece> W = new ArrayList <>();
		ArrayList <Piece> S = new ArrayList <>();
		ArrayList <Piece> NE = new ArrayList <>();
		ArrayList <Piece> NW = new ArrayList <>();
		ArrayList <Piece> SE = new ArrayList <>();
		ArrayList <Piece> SW = new ArrayList <>();
		
		for (int i = 0; i < 64; i ++)
		{
			Piece pDest = brd.get(i);
			if (pDest.location.col.getX() - moving.location.col.getX() != 0 && pDest.location.row - moving.location.row != 0)
			{
				if ((double)(pDest.location.row - moving.location.row) / (pDest.location.col.getX() - moving.location.col.getX()) == 1)
				{
					if ((pDest.location.col.getX() - moving.location.col.getX() > 0))
					{
						NE.add(pDest);
					}
					else if (pDest.location.col.getX() - moving.location.col.getX() < 0)
					{
						SW.add(pDest);
					}
				}
				if ((double)(pDest.location.row - moving.location.row) / (pDest.location.col.getX() - moving.location.col.getX()) == -1)
				{
					if (pDest.location.col.getX() - moving.location.col.getX() > 0)
					{
						SE.add(pDest);
					}
					else if (pDest.location.col.getX() - moving.location.col.getX() < 0)
					{
						NW.add(pDest);
					}
				}
			}
			if (pDest.location.col.getX() == moving.location.col.getX())
			{
				if (pDest.location.row > moving.location.row)
				{
					N.add(pDest);
				}
				else if (pDest.location.row < moving.location.row)
				{
					S.add (pDest);
				}
			}
			else if (pDest.location.row == moving.location.row)
			{
				if (pDest.location.col.getX() < moving.location.col.getX())
				{
					W.add(pDest);
				}
				else if (pDest.location.row < moving.location.row)
				{
					E.add(pDest);
				}
			}
		}
		
		NE = sortDiags (NE);
		NW = sortDiags (NW);
		SE = sortDiags (SE);
		SW = sortDiags (SW);
		N = sortStraights (N);
		W = sortStraights (W);
		E = sortStraights (E);
		S = sortStraights (S);
		
		// Go through each sorted array and run through until  you hit a piece,
		// and include all the pieces before that to the array
		for (int i = 0; i < NE.size(); i ++)
		{
			Piece p = NE.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
	
		for (int i = 0; i < NW.size(); i ++)
		{
			Piece p = NW.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}	
		for (int i = SE.size() - 1; i >= 0; i --)
		{
			Piece p = SE.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		for (int i = SW.size() - 1; i >= 0; i --)
		{
			Piece p = SW.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		for (int i = 0; i < N.size(); i ++)
		{
			Piece p = N.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		
		for (int i = S.size() - 1; i >= 0; i --)
		{
			Piece p = S.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		for (int i = W.size() - 1; i >= 0; i --)
		{
			Piece p = W.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}
		for (int i = 0; i < E.size(); i ++)
		{
			Piece p = E.get(i);
			if (p.type == pieceType.EMPTY)
			{
				arr.add(new Location (p.location.col, p.location.row));
			}
			else if (p.color != moving.color)
			{
				arr.add(new Location (p.location.col, p.location.row));
				break;
			}
			else if (p.color == moving.color)
			{
				break;
			}
		}	
		
		return arr;
	}

	private ArrayList <Location> setKingArray (Piece moving, HashMap<Integer, Piece> brd)
	{
		ArrayList<Location> arr = new ArrayList<>();
		for (int i = 0; i < 64; i++)
		{
			Piece dest = brd.get(i);
	  		if(Math.abs(moving.location.col.getX() - dest.location.col.getX()) <= 1 
	  				&& Math.abs(moving.location.row - dest.location.row) <= 1)
			{
				if(dest.color != moving.color)
				{
					arr.add(new Location(dest.location.col, dest.location.row));
				}
			}
		}
		return arr;
	}

	// Doesnt work yet/ assume it does
  	private ArrayList<Piece> sortStraights (ArrayList <Piece> a) // 0: sort x    1:sort y
  	{
  		for (int i = 0; i < a.size(); i ++)
  		{
  			int min = i;
  			for (int j = i+1; j < a.size(); j++)
  			{
  				if (a.get(min).location.row + a.get(min).location.col.getX() > a.get(j).location.row + a.get(j).location.col.getX())
  				{
  					min = j;
  				}
  			}
  			Piece temp = a.get(i);
  			a.set(i, a.get(min));
  			a.set(min, temp);
  		}
  		return a;
  	}
  	private ArrayList<Piece> sortDiags (ArrayList <Piece> a) // 0: sort x    1:sort y
  	{
  		for (int i = 0; i < a.size(); i ++)
  		{
  			int min = i;
  			for (int j = i+1; j < a.size(); j++)
  			{
  				if (a.get(min).location.row > a.get(j).location.row)
  				{
  					min = j;
  				}
  			}
  			Piece temp = a.get(i);
  			a.set(i, a.get(min));
  			a.set(min, temp);
  		}
  		return a;
  	}
  	

	//switchPiece: takes the destination piece and an hashmap of the board and returns a 
	//board where this piece moves to the destination piece.
	public HashMap<Integer,Piece> switchPieces(Piece destinationPiece, HashMap<Integer,Piece> brd) 
	{
		Location movTemp = new Location (this.location.col, this.location.row);
		
		this.location.col = destinationPiece.location.col;
		this.location.row = destinationPiece.location.row;
		
		destinationPiece.location.col = movTemp.col;
		destinationPiece.location.row = movTemp.row;
		
		if (destinationPiece.color != Color.BLUE)
		{
			System.out.println("REMOVED");
			brd.remove(destinationPiece.index);
			brd.put(destinationPiece.index, new Piece (destinationPiece.location, Color.BLUE, pieceType.EMPTY));
		}
		return brd;
	}
	
	//returns the index of this piece
	private int Index() 
	{
		return this.location.col.getIndex(this.location.row);
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
		return (this.location.col.getX() == comparable.location.col.getX() && this.location.row == comparable.location.row);
	}
	
	//POLYMORPH returns true if this has the same x as i and the same y as j
	public boolean equalsCoord(int a, int n) 
	{
		return this.location.col.getX() == a && this.location.row == n;
	}
	
	//OVERRIDE the equals method so we can compare temporary objects to the original object in the array
	public boolean equals (Piece comparable)
	{
		return (this.color == comparable.color &&
				this.index == comparable.index &&
				this.type == comparable.type &&
				this.location.col.getX() == comparable.location.col.getX() &&
				this.location.row == comparable.location.row);
	}
	
}
