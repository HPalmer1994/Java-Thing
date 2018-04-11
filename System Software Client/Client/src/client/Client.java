/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import java.util.Scanner;

/**
 *
 * @author Ben
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
Socket ServerConnection = new Socket("localhost", 9090);
System.out.println("Connected to " + ServerConnection.getInetAddress());

//create io streams
DataInputStream inFromServer = new DataInputStream(ServerConnection.getInputStream());  // Used to send and recieve data from the server
DataOutputStream outToServer = new DataOutputStream(ServerConnection.getOutputStream());

BufferedReader br= new BufferedReader(new InputStreamReader(System.in));

String MessageIn = "", MessageOut = "";
while(!MessageIn.equals("end")){
  MessageOut = br.readLine();
  outToServer.writeUTF(MessageOut);
  MessageIn = inFromServer.readUTF();
  System.out.println(MessageIn);  //Prints server message
}
ServerConnection.close();
    }
    



        