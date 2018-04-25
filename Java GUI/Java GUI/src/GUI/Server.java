/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.System.in;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class Server {
    
    ServerSocket server;
    ArrayList<TimeHandler> connections = new ArrayList<TimeHandler>();
    boolean Running = true;
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
   
public static void main(String[] args) throws IOException {
    new Server();
}

public Server() throws IOException{
    

//Creating the Server port to connect to.while(Running){
ServerSocket server = new ServerSocket(9090);
while(true){
System.out.println("Waiting for a client to connect");
//establish connection
while (Running){
Socket Client = server.accept();
System.out.println("A client has connected. His address is " + Client.getInetAddress());

//Assgning to Thread and adding it to the array
TimeHandler TH = new TimeHandler(Client, this);
TH.start();
connections.add(TH);
}
}








