import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class chessServer {
	
	Scanner keyboard = new Scanner (System.in);
	ServerSocket serverSocket;
	Socket socket;
	PrintWriter pW;
	BufferedReader bR;
	boolean myTurn;
	
	public chessServer ()
	{
		try 
		{
			System.out.println("Server Waiting...");
			System.out.println("Server Waiting...");
	    	
			serverSocket = new ServerSocket(40276);
	       
			Scanner keyboard = new Scanner (System.in);
	       
			socket = serverSocket.accept();
	       	bR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	       	pW = new PrintWriter(socket.getOutputStream(), true);
	       	
		}
		catch(IOException e)
	    {
	    	System.out.println("Server Error--> " + e.getMessage());
	    }
	}
}