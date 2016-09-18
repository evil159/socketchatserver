package com.rol.models.history;

/**
 * U-type
 * <p>
 * Created by Roman Laitarenko on 9/18/16.
 */
public interface ChatHistoryObserver {

    void onChatHistoryUpdate(ChatMessage message);
}
