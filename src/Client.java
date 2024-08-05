import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import util.ClientMessageHandler;
import util.ConfigFileManager;


public class Client {

    static Scanner scanner = new Scanner(System.in);
    static ConfigFileManager configFileManager = new ConfigFileManager("config.properties");
    
    static final int SERVER_PORT = Integer.parseInt(configFileManager.getPropertyData("server.port"));
    static final String SERVER_IP = configFileManager.getPropertyData("server.ip");
    
    public static void main(String[] args) throws IOException {
        System.out.println("Please enter a username: ");
        String username = scanner.nextLine();
        joinChat(username);
    }

    public static void joinChat(String username) throws IOException{
        Socket clientSocket = new Socket(SERVER_IP,SERVER_PORT); //Connect to the server
        System.out.println("You joined the chat room."); 

        ClientMessageHandler messageHandler = new ClientMessageHandler(clientSocket);
        
        messageHandler.new SendMessage(username).start(); //Thread to start sending messages
        messageHandler.new ReceiveMessage().start(); //Thread to start receiving messages
    }

}


