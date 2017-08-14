
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Board {
	
	private static HashMap<Integer, Piece> board;
	private int WIDTH = 850;
	private int HEIGHT = 850;
	private int OFFSETHEIGHT = 25;
	private JFrame frame;
	private Piece movingPiece;
	private Piece destinationPiece;
	private boolean isSecond = false;
	Color turn = Color.WHITE;
	
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
	}
			
	//setDefaultBoard: takes arraylist of the board and clears it, then add's the default start to the game
	private void setDefaultBoard(){
		board = new HashMap<>();
		board.put(Column.A.getIndex(8), new Piece(new Location(Column.A, 8), Color.BLACK, pieceType.ROOK));
		board.put(Column.H.getIndex(8), new Piece(new Location(Column.H, 8), Color.BLACK, pieceType.ROOK));
		board.put(Column.B.getIndex(8), new Piece(new Location(Column.B, 8), Color.BLACK, pieceType.KNIGHT));
		board.put(Column.G.getIndex(8), new Piece(new Location(Column.G, 8), Color.BLACK, pieceType.KNIGHT));
		board.put(Column.C.getIndex(8), new Piece(new Location(Column.C, 8), Color.BLACK, pieceType.BISHOP));
		board.put(Column.F.getIndex(8), new Piece(new Location(Column.F, 8), Color.BLACK, pieceType.BISHOP));
		board.put(Column.D.getIndex(8), new Piece(new Location(Column.D, 8), Color.BLACK, pieceType.QUEEN));
		board.put(Column.E.getIndex(8), new Piece(new Location(Column.E, 8), Color.BLACK, pieceType.KING));
		
		// real version
		for (Column col : Column.values()) 
		{
			board.put(col.getIndex(7), new Piece(new Location (col, 7), Color.BLACK, pieceType.PAWN));
			board.put(col.getIndex(2), new Piece(new Location (col, 2), Color.WHITE, pieceType.PAWN));
		}
		
		// test version with no pawns
//		for (Column col : Column.values()) 
//		{
//			board.put(col.getIndex(7), new Piece(new Location(col, 7), Color.BLUE, pieceType.EMPTY));
//			board.put(col.getIndex(2), new Piece(new Location(col, 2), Color.BLUE, pieceType.EMPTY));
//		}
		
		board.put(Column.A.getIndex(1), new Piece(new Location(Column.A, 1), Color.WHITE, pieceType.ROOK));
		board.put(Column.H.getIndex(1), new Piece(new Location(Column.H, 1), Color.WHITE, pieceType.ROOK));
		board.put(Column.B.getIndex(1), new Piece(new Location(Column.B, 1), Color.WHITE, pieceType.KNIGHT));
		board.put(Column.G.getIndex(1), new Piece(new Location(Column.G, 1), Color.WHITE, pieceType.KNIGHT));
		board.put(Column.C.getIndex(1), new Piece(new Location(Column.C, 1), Color.WHITE, pieceType.BISHOP));
		board.put(Column.F.getIndex(1), new Piece(new Location(Column.F, 1), Color.WHITE, pieceType.BISHOP));
		board.put(Column.D.getIndex(1), new Piece(new Location(Column.D, 1), Color.WHITE, pieceType.QUEEN));
		board.put(Column.E.getIndex(1), new Piece(new Location(Column.E, 1), Color.WHITE, pieceType.KING));
		
		//add empty's
		for (Column col : Column.values()) 
		{
			for (int i = 3; i < 7; i++) 
			{
				board.put(col.getIndex(i), new Piece(new Location(col, i), Color.BLUE, pieceType.EMPTY));
			}
		}
		
		drawBoard();
		frame.setVisible(true);
		updatePossibleMoves();
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
	
    //createButton: takes piece and draws it on the board
    private void createButton (Piece p, Color color) {
    	ButtonExtend button;
    	if(p.type == null) //if a piece's type is null, than its a fuker button (fix issue at some point?) "fuk the fuker button" - abraham lincoln
    	{
    		button = new ButtonExtend();
    		frame.add(button);
    		return;
    	}
    	else //not null is one of the pieceType enums
    	{
    		button = new ButtonExtend (p.location.col + "" + p.location.row);
    		//add image depending on the enum type
			String img = null;
			switch (p.type)
			{
			case PAWN:
				img = "pawn.png";
				button.addImage (img);
				break;
			case KNIGHT:
				img = "knight.jpg";
				button.addImage (img);
				break;
			case BISHOP:
				img = "bishop.png";
				button.addImage (img);
				break;
			case ROOK:
				img = "rook.png";
				button.addImage (img);
				break;
			case QUEEN:
				img = "queen.png";
				button.addImage (img);
				break;
			case KING:
				img = "king.png";
				button.addImage (img);
				break;
			default:
				break;
			}
    	}
    	
    	button.setPiece(p); //polymorphism of JButton for a button to hold a piece object
    	
    	//setBounds to do: change this so we can fit reset button in frame vvvvv
		button.setBounds(((this.WIDTH/8)*p.location.col.getX()), ((this.HEIGHT/8)*(8 - p.location.row)), this.WIDTH/8, this.HEIGHT/8);
		
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.addActionListener(new EndingListener ()); 
		button.setActionCommand(p.toStringIndex()); //adds logic from actionListener's code
		button.setBackground(color);
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
					System.out.println("That is Not Your Piece");
					return;
				}
				movingPiece = board.get(indexOf); 
				movingPiece.possibleMoves = movingPiece.setPossibleMovesArray(board);
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
				isSecond = false;
				boolean isItLegal = movingPiece.isLegal(destinationPiece, board);
				System.out.println("result: " + isItLegal + "\n");
				if (isItLegal)
				{
					System.out.println("moving to " + destinationPiece.type + "(" + 
							destinationPiece.location.col + "" + destinationPiece.location.row+")");
					movePiece();
					if (turn == Color.WHITE)
					{
						turn = Color.BLACK;
					}
					else if (turn == Color.BLACK)
					{
						turn = Color.WHITE;
					}
				}
			}
			else
			{
				isSecond = false;
				System.out.println("can't move to your own piece.");
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
		updateBoardVisual();
		frame.setVisible(true);
	}

	//toStringArray: prints out each piece in the current board
	public String toStringArray()
	{	
		StringBuilder sb = new StringBuilder ();
		for (Piece piece: board.values())
	    {
			sb.append("\n");
			sb.append(board.get(piece.index).colorToString());
	    	sb.append(" " + board.get(piece.index).type);
	    	sb.append("("+board.get(piece.index).location.col + ", " + board.get(piece.index).location.row + ")");
	    	sb.append(" i: " + piece.index);
	    }
		return sb.toString();
	}

	private class EndingListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e) 
		{			
    		buttonPressedLogic(Integer.parseInt(e.getActionCommand()));
		}	
    }
}


/*
 Needed Comment

int count = 0;
int secCount = 0;
for (int a = 0; a < ar.length; a++) {
	for (int z = 0; z < ar.length; z++) {
		JButton button = new JButton(Integer.toString(ar[z][a]));
		button.setBounds(new Rectangle( ((WIDTH/8)*z), ((HEIGHT/8)*a), WIDTH/8, HEIGHT/8) );
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.addActionListener(new EndingListener ());
		
		if (secCount % 8 == 0)
		{
			count ++;
		}
		//new new new shit
		
		if (count % 2 == 0)
		{
			button.setBackground (new Color (172, 112, 61));
		}
		frame.add(button);
		count ++;
		secCount++;
	}
}    

//if ur reading this, stay woke lil fuker
JButton fuker = new JButton ();
frame.add(fuker);
frame.setVisible(true);
=======
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Board {
	
	private static HashMap<Integer, Piece> board;
	private int WIDTH = 850;
	private int HEIGHT = 850;
	private int OFFSETHEIGHT = 25;
	private JFrame frame;
	private Piece movingPiece;
	private Piece destinationPiece;
	private boolean isSecond = false;
	Color turn = Color.WHITE;
	
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
	}
			
	//setDefaultBoard: takes arraylist of the board and clears it, then add's the default start to the game
	private void setDefaultBoard(){
		board = new HashMap<>();
		
		board.put(Column.A.getIndex(8), new Piece(8, Column.A, Color.BLACK, pieceType.ROOK));
		board.put(Column.H.getIndex(8), new Piece(8, Column.H, Color.BLACK, pieceType.ROOK));
		board.put(Column.B.getIndex(8), new Piece(8, Column.B, Color.BLACK, pieceType.KNIGHT));
		board.put(Column.G.getIndex(8), new Piece(8, Column.G, Color.BLACK, pieceType.KNIGHT));
		board.put(Column.C.getIndex(8), new Piece(8, Column.C, Color.BLACK, pieceType.BISHOP));
		board.put(Column.F.getIndex(8), new Piece(8, Column.F, Color.BLACK, pieceType.BISHOP));
		board.put(Column.D.getIndex(8), new Piece(8, Column.D, Color.BLACK, pieceType.QUEEN));
		board.put(Column.E.getIndex(8), new Piece(8, Column.E, Color.BLACK, pieceType.KING));
		
		// real version
		for (Column col : Column.values()) 
		{
			board.put(col.getIndex(7), new Piece(7, col, Color.BLACK, pieceType.PAWN));
			board.put(col.getIndex(2), new Piece(2, col, Color.WHITE, pieceType.PAWN));
		}
		
		// test version with no pawns
//		for (Column col : Column.values()) 
//		{
//			board.put(col.getIndex(7), new Piece(7, col, Color.BLUE, pieceType.EMPTY));
//			board.put(col.getIndex(2), new Piece(2, col, Color.BLUE, pieceType.EMPTY));
//		}
		
		board.put(Column.A.getIndex(1), new Piece(1, Column.A, Color.WHITE, pieceType.ROOK));
		board.put(Column.H.getIndex(1), new Piece(1, Column.H, Color.WHITE, pieceType.ROOK));
		board.put(Column.B.getIndex(1), new Piece(1, Column.B, Color.WHITE, pieceType.KNIGHT));
		board.put(Column.G.getIndex(1), new Piece(1, Column.G, Color.WHITE, pieceType.KNIGHT));
		board.put(Column.C.getIndex(1), new Piece(1, Column.C, Color.WHITE, pieceType.BISHOP));
		board.put(Column.F.getIndex(1), new Piece(1, Column.F, Color.WHITE, pieceType.BISHOP));
		board.put(Column.D.getIndex(1), new Piece(1, Column.D, Color.WHITE, pieceType.QUEEN));
		board.put(Column.E.getIndex(1), new Piece(1, Column.E, Color.WHITE, pieceType.KING));
		
		//add empty's
		for (Column col : Column.values()) 
		{
			for (int i = 3; i < 7; i++) 
			{
				board.put(col.getIndex(i), new Piece(i, col, Color.BLUE, pieceType.EMPTY));
			}
		}
		
		drawBoard();
		frame.setVisible(true);
		updatePossibleMoves();
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
    	for (Piece piece: board.values())
    	{
    		createButton(piece, 0);
		}
    	createButton(new Piece(1, Column.A, Color.BLUE, null) , 0); //the fuker button (fix issue?)
	}
    
    //updateBoard: called when a piece is switched: resets frame and redraws based on the HashMap
	private void updateBoard() 
	{
		frame.getContentPane().removeAll();
		drawBoard();
	}
	
    //createButton: takes piece and draws it on the board
    private ButtonExtend createButton (Piece p, int bin) {
    	ButtonExtend button;
    	if(p.type == null) //if a piece's type is null, than its a fuker button (fix issue at some point?)
    	{
    		button = new ButtonExtend();
    		frame.add(button);
    		return null;
    	}
    	else //not null is one of the pieceType enums
    	{
    		button = new ButtonExtend (p.col+""+p.row);
    		
    		//add image depending on the enum type
			String img = null;
			switch (p.type)
			{
			case PAWN:
				img = "pawn.png";
				button.addImage (img);
				break;
			case KNIGHT:
				img = "knight.jpg";
				button.addImage (img);
				break;
			case BISHOP:
				img = "bishop.png";
				button.addImage (img);
				break;
			case ROOK:
				img = "rook.png";
				button.addImage (img);
				break;
			case QUEEN:
				img = "queen.png";
				button.addImage (img);
				break;
			case KING:
				img = "king.png";
				button.addImage (img);
				break;
			default:
				break;
			}
    	}
    	
    	button.setPiece(p); //polymorphism of JButton for a button to hold a piece object
    	
    	//setBounds to do: change this so we can fit reset button in frame vvvvv
		button.setBounds(((this.WIDTH/8)*p.col.getX()), ((this.HEIGHT/8)*(8 - p.row)), this.WIDTH/8, this.HEIGHT/8);
		
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.addActionListener(new EndingListener ()); 
		button.setActionCommand(p.toStringIndex()); //adds logic from actionListener's code
		button.setBackground(Color.WHITE);
		if (bin == 1)
		{
			button.setBackground(Color.BLACK);
		}
		button.setBorder(new LineBorder (Color.BLACK, 1));
		button.setBorderPainted(true);
		frame.add(button);
		return null;
	}
    
    //toStringArray: prints out each piece in the current board
    public String toStringArray()
	{	
		StringBuilder sb = new StringBuilder ();
		for (Piece piece: board.values())
	    {
			sb.append("\n");
			sb.append(board.get(piece.index).colorToString());
	    	sb.append(" " + board.get(piece.index).type);
	    	sb.append("("+board.get(piece.index).col + ", " + board.get(piece.index).row + ")");
	    	sb.append(" i: " + piece.index);
	    }
		return sb.toString();
	}
    
    //movePiece: assumes class variables movingPiece and destinatinoPiece are both occupied
    //			 performs a switch piece move in the board, and updates the JFrame accordingly
    //			 also updates the board's piece's possible moves
    private void movePiece() 
    {
    	board = movingPiece.switchPieces(destinationPiece, board);
		updateBoard();
		updatePossibleMoves();
		frame.setVisible(true);
	}

	private void buttonPressedLogic(int indexOf) //logic that runs when a piece or space on the board is clicked.
	{
		// ERIC NOTE TO NICK:
		// Switched the order of the first and the second click, so it is easier to follow
		if (isSecond == false)
		{			
			if(board.get(indexOf).type != pieceType.EMPTY)
			{
				if (board.get(indexOf).color != turn)
				{
					System.out.println("That is Not Your Piece");
					return;
				}
		// 		Darken the possible pieces the current piece can go	
				for (int i = 0; i < board.size(); i ++)
				{
					for (int j = 0; j < board.get(i).possibleMoves.size(); j ++)
					{
						if (board.get(i).possibleMoves.get(j).equals(board.get(i)))
						{
							System.out.println("THIS CODE RAN");
						}
//						Button [at board.get(i)] . set background (darker shade) // pseudo code
					}
				}
				System.out.println(board.get(indexOf).colorToString() + ", " + board.get(indexOf).type + " (" + 
						board.get(indexOf).col +", "+board.get(indexOf).row+")   PossMoves:" + board.get(indexOf).possibleMoves);
				movingPiece = board.get(indexOf);
				isSecond = true;
				return;
			}
			else
			{
				isSecond = false;
				System.out.println("was empty.");
				return;
			}
		}
		else
		{
			if(board.get(indexOf).type == pieceType.EMPTY || board.get(indexOf).color != movingPiece.color)
			{
				destinationPiece = board.get(indexOf);
				isSecond = false;
				boolean isItLegal = movingPiece.isLegal(destinationPiece, board);
				System.out.println("result: " + isItLegal + "\n");
				if (isItLegal)
				{
					System.out.println("moving to " + destinationPiece.type + "(" + 
							destinationPiece.col + "" + destinationPiece.row+")");
					movePiece();
					if (turn == Color.WHITE)
					{
						turn = Color.BLACK;
					}
					else if (turn == Color.BLACK)
					{
						turn = Color.WHITE;
					}
				}
			}
			else
			{
				isSecond = false;
				System.out.println("can't move to your own piece.");
				return;
			}
		}
	}

	private class EndingListener implements ActionListener
    { 
		
    	public void actionPerformed(ActionEvent e) 
		{			
    		buttonPressedLogic(Integer.parseInt(e.getActionCommand()));
		}
    	
    	
    }
	
}


/*
 Needed Comment

int count = 0;
int secCount = 0;
for (int a = 0; a < ar.length; a++) {
	for (int z = 0; z < ar.length; z++) {
		JButton button = new JButton(Integer.toString(ar[z][a]));
		button.setBounds(new Rectangle( ((WIDTH/8)*z), ((HEIGHT/8)*a), WIDTH/8, HEIGHT/8) );
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.addActionListener(new EndingListener ());
		
		if (secCount % 8 == 0)
		{
			count ++;
		}
		//new new new shit
		
		if (count % 2 == 0)
		{
			button.setBackground (new Color (172, 112, 61));
		}
		frame.add(button);
		count ++;
		secCount++;
	}
}    

//if ur reading this, stay woke lil fuker
JButton fuker = new JButton ();
frame.add(fuker);
frame.setVisible(true);
>>>>>>> stash
*/