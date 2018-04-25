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
public class ClientConnection extends Thread {
    
    static Socket _client;   // Socket
    static DataInputStream din; // input and output streams
    static DataOutputStream dout;
    
    public ClientConnection(Socket Client, Server client){
        _client = Client;
        try {
            din = new DataInputStream(_client.getInputStream()); // Data inout and output streams to write and recieve data
            dout = new DataOutputStream(_client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        
    }
}
