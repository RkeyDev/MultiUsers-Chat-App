import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import util.ConfigFileManager;

public class Server {
    static ConfigFileManager configFileManager = new ConfigFileManager("../config.properties");
    static final int SERVER_PORT = Integer.parseInt(configFileManager.getPropertyData("server.port"));

    static ArrayList<Socket> connectedClients = new ArrayList<>();

    public static void main(String[] args){
        
        

        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            System.out.println("Server listening on port " + SERVER_PORT);

            while (true)
                acceptClients(serverSocket); //Acceot clients to the server and handle them
                
            

        }

        catch(IOException e){
            e.printStackTrace();
        }


    }


    public static void acceptClients(ServerSocket serverSocket) throws IOException{
            
        Socket clientSocket = serverSocket.accept(); //Accept clients to the server
        System.out.println("Client connected to the server.");
        

        new Thread(() -> {
            try {
                handleClients(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start(); //Handle client
              
    }

    public static void handleClients(Socket clientSocket) throws IOException{
        connectedClients.add(clientSocket); //Add client socket to the connected clients list

        try(BufferedReader clientBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            String clientMsg;
            while ((clientMsg = clientBufferedReader.readLine()) != null) {
                System.out.println(clientMsg);
                broadcastMessage(clientSocket,clientMsg);
            }

        }
    }

    public static void broadcastMessage(Socket clientSocket, String clientMsg) throws IOException{
        for (Socket client : connectedClients) {
            if(client != clientSocket){
                PrintWriter clientWriter = new PrintWriter(client.getOutputStream(),true);
                
                clientWriter.println(clientMsg);
            }
        }
    }
    
}


