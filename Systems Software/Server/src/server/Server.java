/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.System.in;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Handler;

/**
 *
 * @author Ben
 */
public class Server {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
   
public static void main(String[] args) throws IOException {
//Creating the    
ServerSocket server = new ServerSocket(9090);
while(true){
System.out.println("Waiting...");

//establish connection
Socket client = server.accept();
System.out.println("Connected " + client.getInetAddress());

//assign the client to  the handler
TimeHandler th = new TimeHandler(client);
Thread t = new Thread(th);
t.start();
}
}
}


