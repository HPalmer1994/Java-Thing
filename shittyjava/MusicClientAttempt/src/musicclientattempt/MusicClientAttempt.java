/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicclientattempt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Will
 */
public class MusicClientAttempt implements Runnable {

 // The client socket
  private static Socket socketClient = null;
    // The input stream
  private static DataInputStream InputStream = null;
  // The output stream
  private static PrintStream OutputStream = null;


  private static BufferedReader dataInput = null;
  private static boolean closed = false;
    public static String message;
  
  public static void main(String[] args) {
      
     
      
      
    // The default port.
    int portNumber = 8080;
    // The default host.
    String host = "localhost";

    if (args.length < 2) {
      System.out
          .println("Connecting to the current host on port number: " + portNumber);
    } else {
      host = args[0];
      portNumber = Integer.valueOf(args[1]).intValue();
    }

    /*
     * Open a socket on a given host and port. Open input and output streams.
     */
    try {
      socketClient = new Socket(host, portNumber);
      dataInput = new BufferedReader(new InputStreamReader(System.in));
      OutputStream = new PrintStream(socketClient.getOutputStream());
      InputStream = new DataInputStream(socketClient.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Unknown host error: " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }
    
 

    /*
     * If everything has been initialized then we want to write some data to the
     * socket we have opened a connection to on the port portNumber.
     */
    if (socketClient != null && OutputStream != null && InputStream != null) {
      try {

        /* Create a thread to read from the server. */
        new Thread(new MusicClientAttempt()).start();
        while (!closed) {
          OutputStream.println(dataInput.readLine().trim());
        }
        
        /*
         * Close the output stream, close the input stream, close the socket.
         */
        OutputStream.close();
        InputStream.close();
        socketClient.close();
      } catch (IOException e) {
        System.err.println("Current error:  " + e);
      }
    }
    
  }
  
  

    static void Message(String message) {
         OutputStream.println(message);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

public void run() {
    /*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */
    LoggedInClient Client = new LoggedInClient();
    Client.setVisible(true);
    String responseLine;
    try {
      while ((responseLine = InputStream.readLine()) != null) {
        System.out.println(responseLine);
        Client.Chatbox.append(responseLine + "\n");
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }
      closed = true;
    } catch (IOException f) {
      System.err.println("IOException:  " + f);
    }
  }
  }
  