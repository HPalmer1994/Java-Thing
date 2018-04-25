/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @auAuthor Hayden
 */
public class Backupclient {

    /**
     * @param args the command line arguments
     */
    

    ClientConnection cc;
            
            
    public static void main(String[] args) {
        // TODO code application logic here
        new Backupclient();
        
    }
    
    public Backupclient(){
        
        try {
            Socket s = new Socket("localhost", 3333);
            cc = new ClientConnection(s, this);
            cc.start();

            
            try {
                RecieveMessage();
            } catch (InterruptedException ex) {
                Logger.getLogger(Backupclient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Backupclient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void RecieveMessage() throws IOException, InterruptedException{
        Scanner console = new Scanner(System.in);
        
        while (true){
            while(!console.hasNextLine()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Backupclient.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            String input = console.nextLine();
            if (input.toLowerCase().equals("quit"))
            {
                break;
            }   
            
            cc.sendStringtoServer(input);
        }
        
        cc.close();
        
    }
}
