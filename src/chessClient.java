import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class chessClient {
	
	Scanner keyboard = new Scanner (System.in);
	
	Socket socket;
	PrintWriter pW;
	BufferedReader bR;
	boolean myTurn;
	
	public chessClient ()
	{
		try  
	    {
			System.out.println("Client Waiting...");
	    	
	    	socket = new Socket("127.0.0.1", 40276);
	    	pW = new PrintWriter(socket.getOutputStream(), true);
	    	bR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	
	    	myTurn = true;
	        
	    }
	    catch(IOException e)
	    {
	    	System.out.println("Client Error--> " + e.getMessage());
	    }
	}
	
	public void run (String command)
	{
		System.out.println("Client sending "+ command + " to Output");
		pW.println(command);
		myTurn = false;
	}
}