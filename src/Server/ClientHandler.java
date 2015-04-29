/*
 */
package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 * @author Lars Mortensen
 */
public class ClientHandler extends Thread {

  Scanner input;
  PrintWriter output;

  public void handleClient(SSLSocket socket) {
    try {
      input = new Scanner(socket.getInputStream());
      output = new PrintWriter(socket.getOutputStream(), true);
      start();
    } catch (IOException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void run() {
    //Important: Blocking call
    String message = input.nextLine();
    while (!message.equals("close")) {
      message = message.toUpperCase();
      System.out.println("Got a message from the client: " + message);
      output.println(message);
      message = input.nextLine();
    }
    output.println(message);
    input.close();
    output.close();
  }
}
