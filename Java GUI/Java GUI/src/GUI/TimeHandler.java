/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ben
 */
public class TimeHandler extends Thread {
 
Socket Client;
Server server;
DataOutputStream outToClient;
DataInputStream InFromClient;
boolean Running = true;

public TimeHandler(Socket Client, Server server) throws IOException {
super("serverConnectionThread");
    this.Client = Client;
    this.server = server;
} 
public void SendingmessagesOne (String text){
    try {
        outToClient.writeUTF(text);
        outToClient.flush();
    } catch (IOException ex) {
        Logger.getLogger(TimeHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void SendingmessagesMany (String text){
    for (int index = 0; index < server.connections.size(); index++){
       TimeHandler TH = server.connections.get(index);
       TH.SendingmessagesOne(text);
}
}
public void run(){
    
    try {
        InFromClient = new DataInputStream(Client.getInputStream());
        outToClient = new DataOutputStream(Client.getOutputStream());
        while (Running){
            while(InFromClient.available()==0){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                     e.printStackTrace();
                }
            }
             String msgin = InFromClient.readUTF();
            SendingmessagesOne (msgin);
        }
       InFromClient.close();
       outToClient.close();
       Client.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}





}




