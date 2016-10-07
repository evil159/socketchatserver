package com.rol.models.history;

import java.util.ArrayList;
import java.util.List;

/**
 * Chat server
 *
 * 9/18/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
public class ChatHistory implements ChatHistoryObservable {

    private static ChatHistory staticHistory = null;

    private final List<ChatMessage> messages = new ArrayList<>();
    private final List<ChatHistoryObserver> observers = new ArrayList<>();

    private ChatHistory() {

    }

    public static ChatHistory getInstance() {
        if (staticHistory == null) {
            staticHistory = new ChatHistory();
        }

        return staticHistory;
    }

    public synchronized void insert(ChatMessage message) {
        messages.add(message);

        notifyObservers(message);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (ChatMessage message : messages) {
            result.append(message);
            result.append("\n");
        }

        return result.toString();
    }

    @Override
    public void register(ChatHistoryObserver observer) {
        observers.add(observer);
    }

    @Override
    public void deregister(ChatHistoryObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ChatMessage message) {
        for (ChatHistoryObserver observer : observers) {
            observer.onChatHistoryUpdate(message);
        }
    }
}