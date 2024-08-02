package util;

import java.net.Socket;

public class ClientHandler extends Thread{
    
    Socket clientSocket;

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run(){
        
    }

}
