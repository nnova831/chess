import java.awt.Color;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Board {
	
	private static HashMap<Integer, Piece> board;
	private int WIDTH = 900;
	private int HEIGHT = 900;
	private int OFFSETHEIGHT = 25;
	private JFrame frame;
	private Piece movingPiece;
	private Piece destinationPiece;
	private boolean isSecond = false;
	private boolean darkMode = true;
	Color turn;
	
	public static void main(String[] args) {		
		Board b = new Board();
		b.setDefaultBoard();
	}
	
	public Board() {
		
		frame = new JFrame ("Chess");
		frame.setSize(WIDTH, HEIGHT + OFFSETHEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		movingPiece = null;
		destinationPiece = null;
		MenuBar menuBar = new MenuBar ();
			Menu file = new Menu ("File");
				MenuItem newGame = new MenuItem ("New Game");
					newGame.addActionListener(new EndingListener ()); 
				MenuItem undo = new MenuItem ("Undo");
					undo.addActionListener(new EndingListener ()); 
				MenuItem darkChess = new MenuItem ("Dark Chess");
					darkChess.addActionListener(new EndingListener ()); 
				MenuItem refresh = new MenuItem ("Refresh");
					refresh.addActionListener(new EndingListener ()); 
		
		ButtonExtend victoryScreen = new ButtonExtend ( " wins!");
		frame.add(victoryScreen);
		file.add(newGame);
		file.add(undo);
		file.add(darkChess);
		file.add(refresh);
		menuBar.add(file);
		frame.setMenuBar(menuBar);
		
	}
	
	
	private void resetBoard ()
	{
		isSecond = false;
		setDefaultBoard ();
		drawPossibleMoves (new ArrayList <>());
		clearMovesVisual();
	}
	
	//setDefaultBoard: takes arraylist of the board and clears it, then add's the default start to the game
	private void setDefaultBoard(){
		if (darkMode == true)
		{
			setRandomizedBoard ();
			return;
		}
		board = new HashMap<>();
		turn = Color.WHITE;
				
		board.put(Column.A.getOrigIndex(8), new Piece(new Location(Column.A, 8), Color.BLACK, pieceType.ROOK));
		board.put(Column.H.getOrigIndex(8), new Piece(new Location(Column.H, 8), Color.BLACK, pieceType.ROOK));
		board.put(Column.B.getOrigIndex(8), new Piece(new Location(Column.B, 8), Color.BLACK, pieceType.KNIGHT));
		board.put(Column.G.getOrigIndex(8), new Piece(new Location(Column.G, 8), Color.BLACK, pieceType.KNIGHT));
		board.put(Column.C.getOrigIndex(8), new Piece(new Location(Column.C, 8), Color.BLACK, pieceType.BISHOP));
		board.put(Column.F.getOrigIndex(8), new Piece(new Location(Column.F, 8), Color.BLACK, pieceType.BISHOP));
		board.put(Column.D.getOrigIndex(8), new Piece(new Location(Column.D, 8), Color.BLACK, pieceType.QUEEN));
		board.put(Column.E.getOrigIndex(8), new Piece(new Location(Column.E, 8), Color.BLACK, pieceType.KING));
		
		// real version (pawns)
		for (Column col : Column.values()) 
		{
			board.put(col.getOrigIndex(7), new Piece(new Location (col, 7), Color.BLACK, pieceType.PAWN));
			board.put(col.getOrigIndex(2), new Piece(new Location (col, 2), Color.WHITE, pieceType.PAWN));
		}
		
		// test version with no pawns
//		for (Column col : Column.values()) 
//		{
//			board.put(col.getIndex(7), new Piece(new Location(col, 7), Color.BLUE, pieceType.EMPTY));
//			board.put(col.getIndex(2), new Piece(new Location(col, 2), Color.BLUE, pieceType.EMPTY));
//		}
		
		board.put(Column.A.getOrigIndex(1), new Piece(new Location(Column.A, 1), Color.WHITE, pieceType.ROOK));
		board.put(Column.H.getOrigIndex(1), new Piece(new Location(Column.H, 1), Color.WHITE, pieceType.ROOK));
		board.put(Column.B.getOrigIndex(1), new Piece(new Location(Column.B, 1), Color.WHITE, pieceType.KNIGHT));
		board.put(Column.G.getOrigIndex(1), new Piece(new Location(Column.G, 1), Color.WHITE, pieceType.KNIGHT));
		board.put(Column.C.getOrigIndex(1), new Piece(new Location(Column.C, 1), Color.WHITE, pieceType.BISHOP));
		board.put(Column.F.getOrigIndex(1), new Piece(new Location(Column.F, 1), Color.WHITE, pieceType.BISHOP));
		board.put(Column.D.getOrigIndex(1), new Piece(new Location(Column.D, 1), Color.WHITE, pieceType.QUEEN));
		board.put(Column.E.getOrigIndex(1), new Piece(new Location(Column.E, 1), Color.WHITE, pieceType.KING));
		
		//add empty's
		for (Column col : Column.values()) 
		{
			for (int i = 3; i < 7; i++) 
			{
				board.put(col.getOrigIndex(i), new Piece(new Location(col, i), Color.BLUE, pieceType.EMPTY));
			}
		}
		
		drawBoard();
		updatePossibleMoves();
		frame.setVisible(true);
	}
	
	
	private void setRandomizedBoard()
	{
		board = new HashMap<>();
		turn = Color.WHITE;
		
		ArrayList <pieceType> randBoard = new ArrayList <>();
		randBoard.add(pieceType.KING);
		randBoard.add(pieceType.QUEEN);
		randBoard.add(pieceType.BISHOP);
		randBoard.add(pieceType.BISHOP);
		randBoard.add(pieceType.BISHOP);
		randBoard.add(pieceType.KNIGHT);
		randBoard.add(pieceType.KNIGHT);
		randBoard.add(pieceType.KNIGHT);
		randBoard.add(pieceType.ROOK);
		randBoard.add(pieceType.ROOK);
		randBoard.add(pieceType.ROOK);
		randBoard.add(pieceType.ROOK);
		randBoard.add(pieceType.PAWN);
		randBoard.add(pieceType.PAWN);
		randBoard.add(pieceType.PAWN);
		randBoard.add(pieceType.PAWN);
		
		Collections.shuffle(randBoard);
		
		int c = 0;
		for (Column col : Column.values()) 
		{
			board.put(col.getOrigIndex(8), new Piece(new Location (col, 8), Color.BLACK, randBoard.get(c)));
			c ++;
			board.put(col.getOrigIndex(7), new Piece(new Location (col, 7), Color.BLACK, randBoard.get(c)));
			c ++;
		}
		
		Collections.shuffle(randBoard);
		c = 0;
		for (Column col : Column.values()) 
		{
			board.put(col.getOrigIndex(2), new Piece(new Location (col, 2), Color.WHITE, randBoard.get(c)));
			c++;
			board.put(col.getOrigIndex(1), new Piece(new Location (col, 1), Color.WHITE, randBoard.get(c)));
			c ++;
		}
		//add empty's
		for (Column col : Column.values()) 
		{
			for (int i = 3; i < 7; i++) 
			{
				board.put(col.getOrigIndex(i), new Piece(new Location(col, i), Color.BLUE, pieceType.EMPTY));
			}
		}
		
		drawBoard();
		updatePossibleMoves();
		frame.setVisible(true);
	}
	
	//updatePossibleMoves: uses the current board to set its piece's with the right array of possibleMoves,
	//						based on the state of the board and the type
    private void updatePossibleMoves() 
    {
    	for (int i = 0; i <64; i++) // for (int i = 0; i < 64; i++) does the same thing
    	{	
			if (board.get(i).type != pieceType.EMPTY)
			{
				board.get(i).possibleMoves = board.get(i).setPossibleMovesArray(board);
			}
    	}
    }
       
	//drawBoard: takes array of currentBoard and adds buttons 
    private void drawBoard() 
    {
    	frame.repaint();
    	for (int i = 0; i < board.size(); i ++)
    	{
    		Color c = Color.WHITE;
    		if ((board.get(i).location.col.getX() + board.get(i).location.row) % 2 == 0)
    		{
    			c = new Color (172, 112, 61);
    		}
    		createButton(board.get(i), c);
    	}
    	createButton(new Piece(new Location(Column.A, 1), Color.BLUE, null), Color.WHITE); //the fuker button (fix issue?)
	}
    
    //updateBoard: called when a piece is switched: resets frame and redraws based on the HashMap
	private void updateBoardVisual()
	{
		frame.getContentPane().removeAll();
		drawBoard();
	}
	
	private void drawPossibleMoves(ArrayList<Location> possibleMoves)
	{
//		for (Location loc : possibleMoves) 
//		{
//			board.get(loc.col.getOrigIndex(loc.row)).isPossible = true;
//		}
		for (int i = 0; i < board.size(); i ++)
		{
			for (int j = 0; j < possibleMoves.size(); j++)
			{
				if (board.get(i).equalsCoord (possibleMoves.get(j).col.getX(), possibleMoves.get(j).row))
				{
					board.get(i).isPossible = true;
				}
			}
		}
		updateBoardVisual();	
	}

	private void clearMovesVisual()
	{
		for (Piece p : board.values()) {
			p.isPossible = false;
		}
		updateBoardVisual();
	}
	
    //createButton: takes piece and draws it on the board
	//fuker button (fix issue at some point?) "fuk the fuker button" - abraham lincoln
	//if this button fucker makes it to final release, it will become the fukee button  	
    private void createButton (Piece p, Color color) {
    	ButtonExtend button;
    	String img = null;
    	// created solely for.. The Fuker   Even typing its name makes me shudder
    	if(p.type == null) //null case, couldn't be handled by default in a switch
    	{
    		button = new ButtonExtend();
    		frame.add(button);
    		return;
    	}
    	else
    	{
    		button = new ButtonExtend();
    		
    		if (turn != p.color && p.type != pieceType.EMPTY && darkMode == true)
    		{
    			if (p.color == Color.WHITE)
    			{
    				img = "White Unknown.png";
    				button.addImage (img);
    			}
    			else
    			{
    				img = "Black Unknown.png";
    				button.addImage (img);
    			}
    		}
    		else
    		{
				switch (p.type)
				{
				case PAWN:
					if(p.colorToString() == "WHITE")
					{
						img = "White Pawn.png";
					}
					else
					{
						img = "Black Pawn.png";
					}
					button.addImage (img);
					break;
				case KNIGHT:
					if(p.colorToString() == "WHITE"){
						img = "White Knight.png";
					}else{
						img = "Black Knight.png";
					}
					button.addImage (img);
					break;
				case BISHOP:
					if(p.colorToString() == "WHITE"){
						img = "White Bishop.png";
					}else{
						img = "Black Bishop.png";
					}
					button.addImage (img);
					break;
				case ROOK:
					if(p.colorToString() == "WHITE"){
						img = "White Rook.png";
					}else{
						img = "Black Rook.png";
					}
					button.addImage (img);
					break;
				case QUEEN:
					if(p.colorToString() == "WHITE"){
						img = "White Queen.png";
					}else{
						img = "Black Queen.png";
					}
					button.addImage (img);
					break;
				case KING:
					if(p.colorToString() == "WHITE"){
						img = "White King.png";
					}else{
						img = "Black King.png";
					}
					button.addImage(img);
					break;
				case EMPTY: 
	//				button = new ButtonExtend(p.location.col + "" + p.location.row + "" + p.index);
					break;
				default: //case null
					System.out.println("Should never get here"); //because we handle every situation already
					button = new ButtonExtend();
		    		break;
				}
    		}
    	}
    	
    	button.setPiece(p); //polymorphism of JButton for a button to hold a piece object
    	
    	//setBounds to do: change this so we can fit reset button in frame vvvvv
		button.setBounds(((this.WIDTH/8)*p.location.col.getX()), ((this.HEIGHT/8)*(8 - p.location.row)), this.WIDTH/8, this.HEIGHT/8);
		
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.addActionListener(new EndingListener ()); 
		button.setActionCommand(p.toStringIndex()); //adds logic from actionListener's code
		if(p.isPossible == true)
		{
			button.setBackground(Color.GRAY);
		}else
		{
			button.setBackground(color);
		}
		button.setBorder(new LineBorder (Color.BLACK, 1));
		button.setBorderPainted(true);
		frame.add(button);
	}
    
    private void buttonPressedLogic(int indexOf) //logic that runs when a piece or space on the board is clicked.
    {
		if (isSecond == false) // first click
		{			
			if(board.get(indexOf).type != pieceType.EMPTY)
			{
				if (board.get(indexOf).color != turn)
				{
					System.out.println("Wrong Piece");
					return;
				}
				movingPiece = board.get(indexOf); 
				movingPiece.possibleMoves = movingPiece.setPossibleMovesArray(board);
				drawPossibleMoves(movingPiece.possibleMoves);
				System.out.println(board.get(indexOf).colorToString() + ", " + board.get(indexOf).type + " (" + 
						board.get(indexOf).location.col +", "+board.get(indexOf).location.row+")   PossMoves:" + board.get(indexOf).possibleMoves);
				
				isSecond = true;
			// Attempt at shading in all possible moves after first click
//				createButton (new Piece (board.get(indexOf).possibleMoves.get(0), board.get(indexOf).color, board.get(indexOf).type) , Color.GREEN);
//				for (int i = 0; i < board.get(indexOf).possibleMoves.size(); i++)
//				{
//					createButton (new Piece (board.get(indexOf).possibleMoves.get(i), board.get(indexOf).color, board.get(indexOf).type) , Color.GREEN);
//				}
				return;
			}
			else
			{
				System.out.println("was empty.");
				return;
			}
		}
		else // second click
		{
			if(board.get(indexOf).type == pieceType.EMPTY || board.get(indexOf).color != movingPiece.color)
			{
				destinationPiece = board.get(indexOf);
				boolean isItLegal = movingPiece.isLegal(destinationPiece, board);
				System.out.println(movingPiece.possibleMoves);
				System.out.println("Legal?: " + isItLegal + "\n");
				if (isItLegal)
				{
//					if (destinationPiece.type == pieceType.KING)
//					{
//						endGame (movingPiece.color);
//					}
					isSecond = false;
					System.out.println("moving to " + destinationPiece.type + "(" + 
							destinationPiece.location.col + "" + destinationPiece.location.row+")");
					
					if (turn == Color.WHITE)
					{
						turn = Color.BLACK;
					}
					else if (turn == Color.BLACK)
					{
						turn = Color.WHITE;
					}
					movePiece();
					clearMovesVisual();
				}
				updateBoardVisual ();
			}
			else
			{
				System.out.println("Can't move to your own piece.");
				clearMovesVisual ();
				movingPiece = board.get(indexOf); 
				movingPiece.possibleMoves = movingPiece.setPossibleMovesArray(board);
				drawPossibleMoves(movingPiece.possibleMoves);
				return;
			}
		}
    }
    
    
    //movePiece: assumes class variables movingPiece and destinatinoPiece are both occupied
    //			 performs a switch piece move in the board, and updates the JFrame accordingly
    //			 also updates the board's piece's possible moves
    private void movePiece() 
    {
    	board = movingPiece.switchPieces(destinationPiece, board);
		frame.setVisible(true);
		if (destinationPiece.type == pieceType.KING)
    	{
    		resetBoard();
    		return;
    	}
	}
    
	//toStringArray: prints out each piece in the current board
	public String toStringArray()
	{
		StringBuilder sb = new StringBuilder ();
		for (Piece piece: board.values())
	    {
			sb.append ("\n");
			sb.append (board.get(piece.index).colorToString());
	    	sb.append (" " + board.get(piece.index).type);
	    	sb.append ("("+board.get(piece.index).location.col + ", " + board.get(piece.index).location.row + ")");
	    	sb.append (" i: " + piece.index);
	    }
		return sb.toString();
	}

	private class EndingListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e) 
		{		
    		System.out.println("Command:   " + e.getActionCommand());	
    		if (e.getActionCommand().equalsIgnoreCase("new game")) // reset the board
    		{
    			resetBoard();
    			return;
    		}
    		if (e.getActionCommand().equalsIgnoreCase("undo"))     // undo move
    		{
    			movingPiece.switchPieces(destinationPiece, board);
    			updateBoardVisual();
    			turn = movingPiece.color;
    			return;
    		}
    		if (e.getActionCommand().equalsIgnoreCase("dark chess")) // reset the board
    		{
    			darkMode = !darkMode;
    			updateBoardVisual ();
    			return;
    		}
    		if (e.getActionCommand().equalsIgnoreCase("refresh"))
    		{
    			drawBoard();
    			return;
    		}
    		
    		buttonPressedLogic(Integer.parseInt(e.getActionCommand()));
		}	
    }
}