/*
 */

package interfaces;

import java.util.EventObject;


/**
 * @author Lars Mortensen
 */
public class MessageArrivedEvent extends EventObject{

  private String message;

  public String getMessage() {
    return message;
  }
  
  public MessageArrivedEvent(Object source) {
    super(source);
  }
  
  public MessageArrivedEvent(Object source,String message) {
    super(source);
    this.message = message;
  }
 
}
