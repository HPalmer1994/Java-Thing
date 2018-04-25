
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hayden
 */
public class ClientConnection extends Thread{
    
        Socket s;
    DataInputStream din;
    DataOutputStream dout; 
    
    boolean running = true;
    
    public ClientConnection(Socket socket, Backupclient client){
        
        s = socket;
        

    }
    

    public void sendStringtoServer (String text) {
        
            try {
                dout.writeUTF(text);
                dout.flush();
            } catch (IOException ex) {
                Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                close();
            }
    }
    
    public void run(){
        
                   try {
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                        while (running){

            try {
                while(din.available()==0){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                String reply = din.readUTF();
                System.out.println(reply);
            } catch (IOException ex) {
                Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                close();
            }
    }
            } catch (IOException ex) {
                Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                close();
            }

    }
    
    public void close(){
        
            try {
                din.close();
                dout.close();
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
