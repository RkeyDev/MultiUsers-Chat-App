import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import util.ConfigFileManager;

public class Client {

    
    static ConfigFileManager configFileManager = new ConfigFileManager("config.properties");
    static final int SERVER_PORT = Integer.parseInt(configFileManager.getPropertyData("server.port"));
    static final String SERVER_IP = "localhost";
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try(Socket clientSocket = new Socket(SERVER_IP,SERVER_PORT)){
            System.out.println("Successfully connected to the server.");
            scanner.nextLine();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
}
