package com.rol.models.history;

import com.rol.models.User;

/**
 * Chat server
 *
 * 9/18/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
public class ChatMessage {

    private final User sender;
    private final String message;

    public ChatMessage(User sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", sender, message);
    }
}
