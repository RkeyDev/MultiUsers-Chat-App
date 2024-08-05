package util;

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
        String username;

        public SendMessage(String username){
            this.username = username;
            sendMessageToServer(this.username);
        }

        @Override
        public void run() {
            
            

            while(!clientSocket.isClosed()){

                String clientMsg = scanner.nextLine(); 
                System.out.print(TextDecorations.CLEAR_LINE.getDecorationCode()); //Clear the line
                System.out.print(TextDecorations.CURSOR_UP.getDecorationCode()); //Move the cursor up
                System.out.println("You: " + clientMsg);
                
                sendMessageToServer(clientMsg);
                
            }
        }

        private void sendMessageToServer(String clientMsg){
            PrintWriter serverWriter;
            try{
                serverWriter = new PrintWriter(clientSocket.getOutputStream(),true);
                serverWriter.println(clientMsg); //Send the message to the server

                } catch (IOException e) {
                    scanner.close(); //Close the scanner to avoid resource leak
                    e.printStackTrace();
                }
        }
           
        
    }

    public class ReceiveMessage extends Thread {
    
        @Override
        public void run() {
            while (!clientSocket.isClosed()) {
                
            
                try{
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
