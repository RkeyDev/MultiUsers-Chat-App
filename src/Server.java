import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.ClientHandler;
import util.ConfigFileManager;

public class Server {
    static ConfigFileManager configFileManager = new ConfigFileManager("../config.properties");
    static final int SERVER_PORT = Integer.parseInt(configFileManager.getPropertyData("server.port"));
    public static void main(String[] args){
        

        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
             
            System.out.println("Server listening on port " + SERVER_PORT);

            while(true){
                acceptClients(serverSocket);
                System.out.println(serverSocket);
            }

            
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }


    public static void acceptClients(ServerSocket serverSocket) throws IOException{
        Socket clientSocket = serverSocket.accept(); //Accept clients to the server
        System.out.println("Client connected to the server.");
        new ClientHandler(clientSocket).run();

    }

    
}
