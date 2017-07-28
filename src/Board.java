import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.html.HTMLDocument.Iterator;

public class Board {
	
	private static HashMap<Integer, Piece> board;
	private int WIDTH = 800;
	private int HEIGHT = 800;
	private int OFFSETHEIGHT = 25;
	private JFrame frame;
	private Piece movingPiece;
	private Piece destinationPiece;
	private boolean isSecond = false;
	
	public static void main(String[] args) {		
		Board b = new Board();
		b.setDefaultBoard();
		//b.updatePossibleMoves(board);
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
		//(int row, Column col, Color color, pieceType type)
		board.put(Column.A.getIndex(8), new Piece(8, Column.A, Color.BLACK, pieceType.ROOK));
		board.put(Column.H.getIndex(8), new Piece(8, Column.H, Color.BLACK, pieceType.ROOK));
		board.put(Column.B.getIndex(8), new Piece(8, Column.B, Color.BLACK, pieceType.KNIGHT));
		board.put(Column.G.getIndex(8), new Piece(8, Column.G, Color.BLACK, pieceType.KNIGHT));
		board.put(Column.C.getIndex(8), new Piece(8, Column.C, Color.BLACK, pieceType.BISHOP));
		board.put(Column.F.getIndex(8), new Piece(8, Column.F, Color.BLACK, pieceType.BISHOP));
		board.put(Column.D.getIndex(8), new Piece(8, Column.D, Color.BLACK, pieceType.QUEEN));
		board.put(Column.E.getIndex(8), new Piece(8, Column.E, Color.BLACK, pieceType.KING));
		
		
		for (Column col : Column.values()) 
		{
			board.put(col.getIndex(7), new Piece(7, col, Color.BLACK, pieceType.PAWN));
			board.put(col.getIndex(2), new Piece(2, col, Color.WHITE, pieceType.PAWN));
		}
		
		board.put(Column.A.getIndex(1), new Piece(1, Column.A, Color.WHITE, pieceType.ROOK));
		board.put(Column.H.getIndex(1), new Piece(1, Column.H, Color.WHITE, pieceType.ROOK));
		board.put(Column.B.getIndex(1), new Piece(1, Column.B, Color.WHITE, pieceType.KNIGHT));
		board.put(Column.G.getIndex(1), new Piece(1, Column.G, Color.WHITE, pieceType.KNIGHT));
		board.put(Column.C.getIndex(1), new Piece(1, Column.C, Color.WHITE, pieceType.BISHOP));
		board.put(Column.F.getIndex(1), new Piece(1, Column.F, Color.WHITE, pieceType.BISHOP));
		board.put(Column.D.getIndex(1), new Piece(1, Column.D, Color.WHITE, pieceType.QUEEN));
		board.put(Column.E.getIndex(1), new Piece(1, Column.E, Color.WHITE, pieceType.KING));
		
		for (Column col : Column.values()) 
		{
			for (int i = 3; i < 7; i++) 
			{
				board.put(col.getIndex(i), new Piece(i, col, Color.BLUE, pieceType.EMPTY));
			}
		}
		System.out.println(toStringArray(board));
		drawBoard();
		frame.setVisible(true);
	}
	
//    private void updatePossibleMoves(ArrayList<Piece> brd)
//    {
//    	for (Piece p: brd)
//    	{
//			if (p.type != pieceType.EMPTY) -- pseudocode
	
//    		ArrayList<Point> ans = new ArrayList<>();
//			for (Piece p2 : board) {
//				if(p.isLegal(p2, board)){
//					ans.add(new Point(p2.x, p2.y));
//				}
//			}
//			p.possibleMoves = ans;
//    	}
//    }
       
	//drawBoard: takes array of currentBoard and adds buttons 
    private void drawBoard() 
    {
    	frame.repaint();
    	for (Piece piece: board.values())
    	{
    		createButton(piece);
		}
    	createButton(new Piece(1, Column.A, Color.BLUE, null)); //the fuker button
	}
    
    //createButton: takes piece and draws it on the board
    private void createButton(Piece p) {
    	ButtonExtend button;
    	if(p.type == null)
    	{
    		button = new ButtonExtend();
    		frame.add(button);
    		return;
    	}
    	else
    	{
    		//System.out.println(p.type.toString().substring(0,2));
    		button = new ButtonExtend(p.type.toString().substring(0,2));
    		button.setActionCommand(p.toString());
    	}
    	button.setPiece(p);
		button.setBounds(((this.WIDTH/8)*p.col.getX()), ((this.HEIGHT/8)*(8 - p.row)), this.WIDTH/8, this.HEIGHT/8);
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.addActionListener(new EndingListener ());
		
		if (p.type != null)
		{
			//adding an image to the button, corresponding to their piece and color
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
			button.setBackground(Color.WHITE);
			button.setBorder(new LineBorder (Color.BLACK, 1));
			button.setBorderPainted(true);
			frame.add(button);
		}	
	}
    
    public String toStringArray (HashMap<Integer, Piece> brd)
	{	
		StringBuilder sb = new StringBuilder ();
		for (Piece piece: brd.values())
	    {
			sb.append("\n");
	    	sb.append("Type: " + brd.get(piece.findIndex()).type);
	    	sb.append("("+brd.get(piece.findIndex()).col + ", " + brd.get(piece.findIndex()).row + ")");
		}
		return sb.toString();
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
	*/
   
    private class EndingListener implements ActionListener
    { 
		//goal 1: click first piece, click landing spot. Print "no" is cannot be done. Print "yes" if it can
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println(board.get(e.getActionCommand()).type);
			
//			int indexOf = Integer.parseInt(e.getActionCommand());
//			//System.out.println("Clicked on index: " + e.getActionCommand() + ", isSecond: " + isSecond);
//			
//			if(isSecond == false){
//				if(board.get(indexOf).type != pieceType.EMPTY)
//				{
//					movingPiece = board.get(indexOf);
//					isSecond = true;
//					System.out.println("Where do you want the " + board.get(indexOf).type + " to go?");
//					return;
//					
//				}else
//				{
//					isSecond = false;
//					System.out.println("was empty.");
//					return;
//				}
//			}
//			else if(isSecond == true)
//			{
//				if(board.get(indexOf).type == pieceType.EMPTY || board.get(indexOf).color != movingPiece.color)
//				{
//					destinationPiece = board.get(indexOf);
//					isSecond = false;
//					System.out.println("moving to " + board.get(indexOf).type + "...");
//					boolean isItLegal = movingPiece.isLegal(destinationPiece, board);
//					System.out.println("result: " + isItLegal + "\n");
//					return;
//				}
//				else
//				{
////					isSecond = false;
//					System.out.println("can't move to your own piece.");
//					return;
//				}
//				 
//			}
		}
    }
	
}
