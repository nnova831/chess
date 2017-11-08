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

public class rServer {
	
	public static void main(String[] args){
		
		String parameter = "G4H4";
		
	    try{
	       System.out.println("Server Connecting...");
	    	
	       ServerSocket serverSocket = new ServerSocket(7654);
	       
	       Scanner keyboard = new Scanner (System.in);
	       
	       Socket socket = serverSocket.accept();
           BufferedReader bR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           PrintWriter pW = new PrintWriter(socket.getOutputStream(), true);
	       
           System.out.println("Im White");
           pW.println("You're Black");
           System.out.println("__________________________________");
           
           Board2 b2 = new Board2 ();
           b2.setDefaultBoard();
           
           String line;
           
           while (true) {
        	   
        	   String move = Board2.STR;
	    		
        	   while (move.length() < 4) {
        		   	System.out.print("");
	    			move = Board2.STR;
	    		}
	    		System.out.println("send: " + move);
	    		pW.println(move);
	    		
	    		Board2.STR = "";
	           
	           line = null;
	           while (line == null) {
	        	   line = bR.readLine();
	           }
	           
	           System.out.println("Clients Response: " + line);
	           b2.switchPieces (line);
           }
           
           
           
		   
        		  
	       
	       
	       
    }
    catch(IOException e)
    {
    	System.out.println(e.getMessage());
    }
}
}