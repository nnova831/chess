import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class rClient {
	
	public static void main(String[] args){
		
		String parameter = null;
		
		try  
	    {
			System.out.println("Client Connecting");
	    	Scanner keyboard = new Scanner (System.in);
	    	
	    	Socket socket = new Socket("127.0.0.1", 7654);
	    	PrintWriter pW = new PrintWriter(socket.getOutputStream(), true);
	    	BufferedReader bR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	
	    	System.out.println("Server Said: " + bR.readLine());
	    	System.out.println("__________________________________");
	    	
	    	Board b = new Board ();
	    	b.setDefaultBoard();
	    	String line;
	    	
	    	while (true) {
	    		
	    		line = null;
	    		while (line == null) {
	    			line = bR.readLine();
	    		}
	    		System.out.println("Server: " + line);
	    		b.switchPieces (line);
	    		

	    		String move = Board.STR;
	    		
	    		while (move.length() < 4) {
	    			System.out.print("");
	    			move = Board.STR;
	    		}
	    		System.out.println("Send: " + move);
	    		pW.println(move);
	    		
	    		Board.STR = "";
	    	}
	    	
	    }
	    catch(IOException e)
	    {
	    	System.out.println("Client Error--> " + e.getMessage());
	    }
	}
}