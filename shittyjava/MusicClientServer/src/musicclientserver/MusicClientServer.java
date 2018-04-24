/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicclientserver;
import static com.sun.javafx.fxml.expression.Expression.add;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Will
 */
public class MusicClientServer {
    
      // The server socket.
  private static ServerSocket socketServer = null;
  
  // The client socket.
  private static Socket socketClient = null;

  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int UserCounts = 15;
  private static final ThreadOfClient[] ThreadCount = new ThreadOfClient[UserCounts];
  List names = new ArrayList();
    public StringBuilder connected = new StringBuilder();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int port = 8080;
        if (args.length < 1){
            System.out.println("Java server using port: " + port);
        }
        else{
            port = Integer.valueOf(args[0]).intValue();
        }
        
        try{
            socketServer = new ServerSocket(port);
            
        }catch (IOException e)
        {
            System.out.println(e);
            
        }
        
        
        while(true){
            try{
                socketClient = socketServer.accept();
                int userCount =0;
                for(userCount = 0; userCount < UserCounts; userCount++){
                    if(ThreadCount[userCount] == null){
                        (ThreadCount[userCount] = new ThreadOfClient(socketClient, ThreadCount, userCount)).start();
                        break;
                    }
                }
                if (userCount == UserCounts){
                    PrintStream os = new PrintStream(socketClient.getOutputStream());
                    os.println("User limit has been hit, please try again later");
                    os.close();
                    socketClient.close();
                }
            } catch (IOException e){
                System.out.println(e);
            }
        }
        
        
    }
    
    
}


class ThreadOfClient extends Thread {

      private DataInputStream InputStream = null;
  private PrintStream OutputStream = null;
    private final ThreadOfClient[] threads;
  private int maxClientsCount;
  public String clientName = null;
  private Socket clientSocket = null;
  ArrayList connected = new ArrayList();
    private int userCount;

  

  public ThreadOfClient(Socket clientSocket, ThreadOfClient[] threads, int userCount) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
   
  }
  


  public void run() {
    int maxClientsCount = this.maxClientsCount;
    ThreadOfClient[] threads = this.threads;
    ArrayList connected = new ArrayList(10);
    int userCount = this.userCount;

    try {
      /*
       * Create input and output streams for this client.
       */
      InputStream = new DataInputStream(clientSocket.getInputStream());
      OutputStream = new PrintStream(clientSocket.getOutputStream());
      String userName;
      while (true) {
        OutputStream.println("Enter your name.");
        userName = InputStream.readLine().trim();
        if (userName.indexOf('@') == -1) {
          break;
        } else {
          OutputStream.println("The name should not contain '@' character.");
        }
      }

      /* Welcome the new the client. */
      OutputStream.println("Welcome " + userName
          + " to our chat room.\nTo leave enter /quit in a new line. To send a whisper simply put an '@' symbol at the start of your message followed by their name");
      
   
  
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] == this) {
            clientName = "@" + userName;
            break;
          }
        }
        
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] != this) {
            threads[i].OutputStream.println("*** A new user " + userName
                + " entered the chat room !!! ***");
           
     
              
        }
      }
      /* Start the conversation. */
      while (true) {
        String messageLine = InputStream.readLine();
        if (messageLine.startsWith("/quit")) {
          break;
        }
        
        
        /* If the message is private sent it to the given client. */
       
             if (messageLine.startsWith("@")) {
          String[] message = messageLine.split("\\s", 2);
          if (message.length > 1 && message[1] != null) {
            message[1] = message[1].trim();
            if (!message[1].isEmpty()) {
              synchronized (this) {
                for (int num = 0; num < maxClientsCount; num++) {
                  if (threads[num] != null && threads[num] != this
                      && threads[num].clientName != null
                      && threads[num].clientName.equals(message[0])) {
                    threads[num].OutputStream.println("WHISPER <" + userName + "> " + message[1]);
                    /*
                     * Echo this message to let the client know the private
                     * message was sent.
                     */
                    this.OutputStream.println(">" + userName + "> Whisper sent:  " + message[1]);
                    break;
                  }
                }
              }
            }
          }
        } else {
          /* The message is public, broadcast it to all other clients. */
          synchronized (this) {
            for (int num = 0; num < maxClientsCount; num++) {
              if (threads[num] != null && threads[num].clientName != null) {
                threads[num].OutputStream.println("<" + userName + "> " + messageLine);
              }
             
        }
              
        
            }
          }
        }
      }
      synchronized (this) {
        for (int num = 0; num < maxClientsCount; num++) {
          if (threads[num] != null && threads[num] != this
              && threads[num].clientName != null) {
            threads[num].OutputStream.println("*** The user " + userName
                + " has left the chat room!!! ***");
          }
        }
      }
      OutputStream.println("*** Thank you for chatting:  " + userName + " ***");

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      synchronized (this) {
        for (int num = 0; num < maxClientsCount; num++) {
          if (threads[num] == this) {
            threads[num] = null;
          }
        }
      }
      /*
       * Close the output stream, close the input stream, close the socket.
       */
      InputStream.close();
      OutputStream.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }
}
