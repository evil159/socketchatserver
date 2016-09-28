package com.rol.models.history;

/**
 * Chat server
 *
 * 9/18/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
public interface ChatHistoryObserver {

    void onChatHistoryUpdate(ChatMessage message);
}
