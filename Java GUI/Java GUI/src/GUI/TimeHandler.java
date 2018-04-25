/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author Ben
 */
public class TimeHandler implements Runnable {
Socket client;
DataOutputStream outToClient;
DataInputStream InFromClient;

public TimeHandler(Socket _client) throws IOException {
client = _client;
outToClient = new DataOutputStream(client.getOutputStream());
InFromClient = new DataInputStream(client.getInputStream());


} //constructor


public void run() {
try{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // Find out what this does 
    String MessageIn = "", MessageOut = "";
    while(!MessageIn.equals("end")){
    MessageIn = InFromClient.readUTF();
    System.out.println(MessageIn); //Printing Client message
    MessageOut = br.readLine();
    outToClient.writeUTF(MessageOut);
    outToClient.flush();
    }
}catch(Exception e){
}
        



