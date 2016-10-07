package com.rol.models.history;

/**
 * Chat server
 *
 * 9/18/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
interface ChatHistoryObservable {

    void register(ChatHistoryObserver observer);

    void deregister(ChatHistoryObserver observer);

    void notifyObservers(ChatMessage message);
}
