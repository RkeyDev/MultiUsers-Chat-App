package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMessageHandler {
    
    private Socket clientSocket;

    public ClientMessageHandler(Socket client_socket){
        this.clientSocket = client_socket;
    }


    public class SendMessage extends Thread {
        Scanner scanner = new Scanner(System.in);
        
        @Override
        public void run() {
            PrintWriter serverWriter;

            while(true){

                System.out.print("You: "); //Display the message to the client
                String clientMsg = scanner.nextLine(); //Read input from the client

                try{
                serverWriter = new PrintWriter(clientSocket.getOutputStream(),true);
                serverWriter.println(clientMsg); //Send the message to the server

                } catch (IOException e) {
                    scanner.close();
                    e.printStackTrace();
                }
                
            }
        }
           
        
    }

    public class ReceiveMessage extends Thread {
    
        @Override
        public void run() {
            while (!clientSocket.isClosed()) {
                
            
                try
                {
                    BufferedReader serverBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    //Receive message from the server
                    String receivedMsg; 
                    if((receivedMsg = serverBufferedReader.readLine()) != null)
                        System.out.println(receivedMsg); //Print the message
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("You left the server.");
            
        }
    
        
    }
}
