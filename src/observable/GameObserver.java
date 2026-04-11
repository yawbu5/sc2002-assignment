package src.observable;

/**
 * Classes that implement this interface have the capability to send messages
 * and other kinds of data to subscribed listeners.
 *
 */
public interface GameObserver {
    public void onNotify();
}
