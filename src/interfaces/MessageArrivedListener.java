package interfaces;

import java.util.EventListener;

/**
 * @author Lars Mortensen
 */
public interface MessageArrivedListener extends EventListener {
  void messageArrived(MessageArrivedEvent evt);
}
