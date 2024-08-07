import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import util.ClientMessageHandler;
import util.ConfigFileManager;
import util.TextDecorations;

public class Client {

    static Scanner scanner = new Scanner(System.in);
    static ConfigFileManager configFileManager = new ConfigFileManager("config.properties");
    
    static int serverPort;
    static String serverIp;
    
    public static void main(String[] args) throws IOException {
        
        login();
    }

    public static void login(){
        Socket clientSocket;
        boolean connectionCreated = false;

        while(!connectionCreated){
            try {  
                System.out.println("Enter the chat IP: ");
                serverIp = scanner.nextLine();
                

                System.out.println("Enter the server port: ");
                serverPort = Integer.parseInt(scanner.nextLine());
                
                System.out.println("Connecting to server...");

            
                clientSocket = new Socket(serverIp,serverPort); //Connect to the server
                connectionCreated = true;

                System.out.println("\nPlease enter a username: ");
                String username = scanner.nextLine();

                joinChat(clientSocket, username); //Join the chat

            } catch (IOException | NumberFormatException e ) {
                System.out.println("ERROR:");
                e.printStackTrace();
                System.out.println("\n"+TextDecorations.RED.getDecorationCode()+
                    "An error occourd while trying to connect to the server.\n "
                    + "Please check if the Ip address or the port are correct and try again"+
                    TextDecorations.RESET_COLOR.getDecorationCode()+"\n"
                    );
                    

            } 
         
        }
        

        
        
        
    }

    public static void joinChat(Socket clientSocket,String username){
        System.out.println("\nYou joined the chat room.");

        ClientMessageHandler messageHandler = new ClientMessageHandler(clientSocket);
        
        messageHandler.new SendMessage(username).start(); //Thread to start sending messages
        messageHandler.new ReceiveMessage().start(); //Thread to start receiving messages
    }

}


