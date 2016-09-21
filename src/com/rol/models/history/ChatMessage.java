package com.rol.models.history;

import com.rol.models.User;

/**
 * U-type
 * <p>
 * Created by Roman Laitarenko on 9/18/16.
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
