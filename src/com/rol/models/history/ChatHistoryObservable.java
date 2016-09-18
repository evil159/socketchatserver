package com.rol.models.history;

/**
 * U-type
 * <p>
 * Created by Roman Laitarenko on 9/18/16.
 */
interface ChatHistoryObservable {

    void register(ChatHistoryObserver observer);

    void deregister(ChatHistoryObserver observer);

    void notifyObservers(ChatMessage message);
}
