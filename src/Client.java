import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import util.ClientMessageHandler;
import util.ConfigFileManager;

public class Client {

    
    static ConfigFileManager configFileManager = new ConfigFileManager("config.properties");
    static final int SERVER_PORT = Integer.parseInt(configFileManager.getPropertyData("server.port"));
    static final String SERVER_IP = "localhost";
    
    public static void main(String[] args) {
        try{ 
            Socket clientSocket = new Socket(SERVER_IP,SERVER_PORT); //Connect to the server
            System.out.println("You joined the chat room."); 
            ClientMessageHandler messageHandler = new ClientMessageHandler(clientSocket);

            ClientMessageHandler.SendMessage clientSendMessage = messageHandler.new SendMessage();
            ClientMessageHandler.ReceiveMessage clientReceiveMessage = messageHandler.new ReceiveMessage();
            
            clientReceiveMessage.start(); //Start receiving messages
            clientSendMessage.start(); //Start sending messages
            
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }

    
}


