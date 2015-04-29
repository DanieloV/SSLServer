package client;

import interfaces.MessageArrivedEvent;
import interfaces.MessageArrivedListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author Lars Mortensen
 */
public class Client extends Thread {

  final static String pathToStore = "src\\SSL\\"; //directory
  final static String keyStoreFile = "server.truststore";
  final static String passwd = "muietie";  
    
  final static int port = 8888;
  final static String serverName = "localhost";
  
    
  Scanner input;
  PrintWriter output;
  List<MessageArrivedListener> listeners = new ArrayList();

  public void connect(String ip, int port) throws UnknownHostException, IOException {
    SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    SSLSocket socket = (SSLSocket) sslsocketfactory.createSocket(ip, port);      
    input = new Scanner(socket.getInputStream());
    output = new PrintWriter(socket.getOutputStream(), true);
    start();
    System.out.println("Started Listening");

  }

  public void run() {
    String message = input.nextLine();
    while (!message.equals("close")) {
      System.out.println("Recieved a message: " + message);
      notifyObservers(message);
      message = input.nextLine();
    }
    input.close();
    output.close();
  }

  public void disconnect() {
    output.println("close");
    System.out.println("Disconnect");
  }

  public void sendMessage(String msg) {
    output.println(msg);
  }

  private void notifyObservers(String message) {
    MessageArrivedEvent evt = new MessageArrivedEvent(this, message);
    for (MessageArrivedListener listener : listeners) {
      listener.messageArrived(evt);
    }
  }

  public void addMessageArrivedListener(MessageArrivedListener mal) {
    listeners.add(mal);
  }

  public void removeMessageArrivedListener(MessageArrivedListener mal) {
    listeners.remove(mal);
  }
  
  /*
   * You should test the full protocol in your chat-solution using a non_GUI
   * approach a below
   */
  public static void main(String[] args) {
    try {
      String trustFilename = pathToStore + "/" + keyStoreFile;
            
      System.setProperty("javax.net.ssl.trustStore", trustFilename);
      System.setProperty("javax.net.ssl.trustStorePassword", passwd);  
        
      
      Client client = new Client();
      client.connect(serverName, port);
      client.addMessageArrivedListener(new MessageArrivedListener() {
        @Override
        public void messageArrived(MessageArrivedEvent evt) {
          System.out.println(evt.getMessage());
        }
      });
      client.sendMessage("hello world");
      client.sendMessage("hello world 2");
      client.sendMessage("hello world 3");
      client.sendMessage("hello world 4");
      client.sendMessage("hello world 5");
      client.disconnect();

    } catch (UnknownHostException ex) {
      Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
