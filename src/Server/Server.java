/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Daniel
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Server {

    final static String pathToStore = "src\\SSL\\"; //directory
    final static String keyStoreFile = "server.keystore";
    final static String passwd = "muietie";
    
    final static int port = 8888;
    
    
    public static void main(String[] args) {
        try { //need try catch because socket may not be created
            String trustFilename = pathToStore + "/" + keyStoreFile;
            
            System.setProperty("javax.net.ssl.keyStore", trustFilename);
            System.setProperty("javax.net.ssl.keyStorePassword", passwd);
   
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
//            
//            
            while (true) {
                //Important: Blocking call
                System.out.println("Waitin for a client");
                SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
                System.out.println("A client connected");
                ClientHandler h = new ClientHandler();
                h.handleClient(sslsocket);
            }
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    
}
