import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import util.ChatLogger;
import util.ConfigFileManager;
import util.TextDecorations;

public class Server {


    public static ConfigFileManager configFileManager = new ConfigFileManager("config.properties");
    public static final int SERVER_PORT = Integer.parseInt(configFileManager.getPropertyData("server.port"));

    public static Map<String,Socket> connectedClients = new HashMap<>(); //Connected users map: KEY-username VALUE-clientSocket

    


    public static void main(String[] args){
        
        

        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            new ChatLogger("SERVER","Server is online and listening on port " + SERVER_PORT,null);

            
            new Thread(()->{
                Scanner scanner = new Scanner(System.in);
                System.out.println("Press enter to shut down the server");
                scanner.nextLine();
                scanner.close();
                
                shutDownClientConnections();
                try {
                    serverSocket.close(); //Shut down server
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (!serverSocket.isClosed())
                acceptClients(serverSocket); //Accept clients to the server and handle them
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void acceptClients(ServerSocket serverSocket) throws IOException{
        
        try{ 
        Socket clientSocket = serverSocket.accept(); //Accept clients to the server        

        new Thread(() -> {
            try {
                handleClients(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start(); //Handle client
        }catch(SocketException e){
            System.out.println("Server has shut down.");
        }
    }

    public static void handleClients(Socket clientSocket) throws IOException{

        

        try(BufferedReader clientBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            String clientName = clientBufferedReader.readLine();
            connectedClients.put(clientName,clientSocket); //Add client socket to the connected clients list
            
            String userJoinMessage = clientName + " joined the chat.";
            broadcastMessage(clientSocket, userJoinMessage,"SERVER",TextDecorations.GREEN.getDecorationCode()); //Display a join message to the users
            displayOnlineUsersNumber(null);
            


            String clientMsg;
            try{
            while ((clientMsg = clientBufferedReader.readLine()) != null) 
                broadcastMessage(clientSocket,clientName + ": " + clientMsg,clientName,null); 
                
            
        }catch(SocketException e){
            String userLeftMessage = clientName + " has left the chat.";
            
            connectedClients.remove(clientName); //Remove the client from the connected clients list 
            broadcastMessage(clientSocket, userLeftMessage,"SERVER",TextDecorations.RED.getDecorationCode());
            displayOnlineUsersNumber(clientSocket);
        }

        }
    }

    public static void broadcastMessage(Socket clientSocket, String msg,String msgAuthor,String messageColor) throws IOException{
        new ChatLogger(msgAuthor, msg, clientSocket);

        //Applying decorations to the msg
        msg = (messageColor!=null?messageColor:"")+msg+TextDecorations.RESET_COLOR.getDecorationCode(); 
        
        boolean isMessageSender;
        try{
        for (Socket client : connectedClients.values()) {
            isMessageSender = client == clientSocket;

            if(!isMessageSender && !client.isClosed()){
                PrintWriter clientWriter = new PrintWriter(client.getOutputStream(),true);
                
                clientWriter.println(msg); //Send the message to the current client
            }
        }
        }catch(SocketException e){
            e.printStackTrace();
        }
    }
    

    public static void displayOnlineUsersNumber(Socket clientSocket) throws IOException{
        String onlineUsersMsg = "[" + connectedClients.size() + " Online users]";
        broadcastMessage(clientSocket, onlineUsersMsg,"SERVER",TextDecorations.GREEN.getDecorationCode()); //Display online users amount
    }

    public static void shutDownClientConnections(){
        try{
                    
            broadcastMessage(null, "Server is shutting down", "SERVER", TextDecorations.RED.getDecorationCode());
            
            for (Socket clientSocekt : connectedClients.values())
                clientSocekt.close(); //Close all client sockets

            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}


