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


        this.logFilePath = "../chat-log/"+getCurrentDate()+".log";
        File file = new File(logFilePath.toString());

        if(!file.exists()){
            try {
                file.createNewFile(); //Create the log file if it doesnt exist
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log(); //Log the last message in the chat
    }


    private void log(){
        

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFilePath.toString(),true))){
            String logMessage = getCurrentDate()+
            ":"+getCurrentTime()
            + " [Message Author: " +
             "'" + this.author + "'" + 
             "] [Message content: '"
              +
              (this.message.contains(":")?this.message.split(":")[1].replaceFirst(" ", ""):this.message)
              
              + "'] " + (this.client!=null?"[Client: " + this.client.toString() +"]" :"") +"\n";

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
