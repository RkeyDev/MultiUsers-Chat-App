package util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatLogger {
    String author;
    String message;
    Socket client;

    String logFilePath;


    public ChatLogger(String author, String message,Socket clientSocket){
        this.author = author;
        this.message = message;
        this.client = clientSocket;


        this.logFilePath = "chat-log/"+getCurrentDate()+".log";
        File logFile = new File(logFilePath);
        logFile.getParentFile().mkdirs(); //Create the directory and the log file if it doesnt exist
        

        log(); //Log the last message in the chat
    }


    private void log(){
        String currentTime = getCurrentTime();
        String currentDate = getCurrentDate();

        String messageContent = (message.contains(":")?message.split(":")[1].replaceFirst(" ", ""):message);
        String clientInfo = (this.client!=null?"[Client: " + this.client.toString() +"]" :"");


        String logMessage = currentDate +":" + currentTime+ " [Message Author: " +"'" + author + "'" + "] [Message content: '"+messageContent+ "'] " + clientInfo +"\n";
        
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFilePath.toString(),true))){
            bufferedWriter.write(logMessage);

        }catch(IOException e){
            
            e.printStackTrace();
        }
        
    }




    private String getCurrentDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date = dateTime.toLocalDate();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(dateFormatter);

        return formattedDate;
    } 

    private String getCurrentTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime time = dateTime.toLocalTime();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time.format(timeFormatter);

        return formattedTime;
    } 
}
