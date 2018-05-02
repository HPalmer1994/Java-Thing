/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_final;


import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
 




/**
 *
 * @author Hayden
 */
public class Server_Final {
    
     private static int uniqueId;
     private ArrayList<ClientThread> al;
     private SimpleDateFormat sdf;
     private int port;
     private boolean keepGoing;
     private ServerGUI sg;

     //change server Gui bit with ours

public Server_Final (int port) {
        this(port);
    }

  public Server(int port) {
 
        // Port for the server followed by the date and then the user array list
        this.port = port;
        sdf = new SimpleDateFormat("HH:mm:ss");
        al = new ArrayList<ClientThread>();
    }

    /**
     * @param args the command line arguments
     */
	    public void start() {
            keepGoing = true;
        /* Server Socket*/
        try
        {
            ServerSocket serverSocket = new ServerSocket(port);
 
       // Connections Loop (endless)
            while(keepGoing)
            {

                // Server host message to confirm launch
                display("Server waiting for Clients on port " + port + ".");
                Socket socket = serverSocket.accept();      // accept connection
                // if I was asked to stop
                if(!keepGoing)
                    break;
                
                ClientThread t = new ClientThread(socket);  // make a thread of it
                al.add(t);                                  // save it in the ArrayList
                t.start();
            }

            // Executes on the command to stop the server
            try {

                serverSocket.close();
                for(int i = 0; i < al.size(); ++i) {

                    ClientThread tc = al.get(i);

                    try {

                    tc.sInput.close();
                    tc.sOutput.close();
                    tc.socket.close();
                    }
                    catch(IOException ioE) {
                      
                    }
                }
            }
            catch(Exception e) {
                display("Exception closing the server and clients: " + e);
            }
        }
        // oops
        catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
            display(msg);
        }
    }   
            
     //Yah need to put GUI stuffs here   
    //All sg stuff needs to be replaced with gui stuff   
    //Displays Date 
    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        if(sg == null)
            System.out.println(time);
        else
            sg.appendEvent(time + "\n");
    }
    private synchronized void broadcast(String message) {
        String time = sdf.format(new Date());
        String messageLf = time + " " + message + "\n";
        if(sg == null)
            System.out.print(messageLf);
        else
            sg.appendRoom(messageLf);    
         
        // Loop in reverse incase of client disconnection
        for(int i = al.size(); --i >= 0;) {
            ClientThread ct = al.get(i);
            if(!ct.writeMsg(messageLf)) {
                al.remove(i);
                display("Disconnected Client " + ct.username + " removed from list.");
            }
        }
    }
    // LogOut command and Search for ID
    synchronized void remove(int id) {
        for(int i = 0; i < al.size(); ++i) {
            ClientThread ct = al.get(i);
            if(ct.id == id) {
                al.remove(i);
                return;
            }
        }
    }
     
    public static void main(String[] args) {
        // start server on port 1500 unless a PortNumber is specified
        int portNumber = 1500;
        switch(args.length) {
            case 1:
                try {
                    portNumber = Integer.parseInt(args[0]);
                }
                catch(Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Server [portNumber]");
                    return;
                }
            case 0:
                break;
            default:
                System.out.println("Usage is: > java Server [portNumber]");
                return;
        }
        // create a server object and start it
        Server_Final server = new Server_Final(portNumber);
        server.start();
    }
   
    class ClientThread extends Thread {
        // In and Out Sockets
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
       
        //client stuffs
        int id;
        String username;
        ChatMessage cm;
        String date;
        
        // Constructor
        ClientThread(Socket socket) {
            // a unique id creater
            id = ++uniqueId;
            this.socket = socket;
            
            System.out.println("Thread trying to create Object Input/Output Streams");
            try
            {
                //output then input created
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput  = new ObjectInputStream(socket.getInputStream());
                // Registers username
                username = (String) sInput.readObject();
                display(username + " just connected.");
            }
            catch (IOException e) {
                display("Exception creating new Input/output Streams: " + e);
                return;
            }

            catch (ClassNotFoundException e) {
            }
            date = new Date().toString() + "\n";
        }
        public void run() {
            boolean keepGoing = true;
            while(keepGoing) {
                // read String
                try {
                    cm = (ChatMessage) sInput.readObject();
                }
                catch (IOException e) {
                    display(username + " Exception reading Streams: " + e);
                    break;             
                }
                catch(ClassNotFoundException e2) {
                    break;
                }
                // the messaage part of the ChatMessage
                String message = cm.getMessage();
                // Switch on the type of message receive
                switch(cm.getType()) {
                case ChatMessage.MESSAGE:
                    broadcast(username + ": " + message);
                    break;
                case ChatMessage.LOGOUT:
                    display(username + " disconnected with a LOGOUT message.");
                    keepGoing = false;
                    break;
                case ChatMessage.WHOISIN:
                    writeMsg("List of the users connected at " + sdf.format(new Date()) + "\n");
                    // scan al the users connected
                    for(int i = 0; i < al.size(); ++i) {
                        ClientThread ct = al.get(i);
                        writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
                    }
                    break;
                }
            }
            remove(id);
            close();
        }    
        // Close All the things
        private void close() {
            try {
                if(sOutput != null) sOutput.close();
            }
            catch(Exception e) {}
            try {
                if(sInput != null) sInput.close();
            }
            catch(Exception e) {};
            try {
                if(socket != null) socket.close();
            }
            catch (Exception e) {}
        }

        private boolean writeMsg(String msg) {
            // if Client is still connected send the message to it
            if(!socket.isConnected()) {
              close();
                return false;
            }
            // write the message to the stream
            try {
                sOutput.writeObject(msg);
            }

            // if an error occurs, do not abort just inform the user

            catch(IOException e) {

                display("Error sending message to " + username);

                display(e.toString());

            }
            return true;
        

}
    }
}