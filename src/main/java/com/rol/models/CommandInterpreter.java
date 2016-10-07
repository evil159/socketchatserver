package com.rol.models;

import com.rol.models.history.ChatHistory;
import com.rol.models.history.ChatHistoryObserver;
import com.rol.models.history.ChatMessage;
import com.sun.istack.internal.Nullable;
import com.sun.javafx.beans.annotations.NonNull;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Chat server
 * <p>
 * 9/13/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
public class CommandInterpreter implements Runnable, ChatHistoryObserver {

    private static final int MAX_USERNAME_LENGTH = 100;

    private final InputStream inputStream;
    private final PrintStream outputStream;

    private User currentUser = null;

    public CommandInterpreter(InputStream inputStream, PrintStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {

        Scanner reader = new Scanner(inputStream);
        Command command = null;

        onStart();

        while (command != Command.Quit) {

            String input = null;
            try {
                input = reader.nextLine();
            } catch (Exception e) {
                break;
            }

            System.out.println(String.format("Message from %s: %s", currentUser == null ? "" : currentUser, input));

            command = Command.fromInput(input);

            try {
                onCommandReceived(command);
            } catch (Exception e) {
                onError(e);
            }
        }

        onStop();
    }

    private void onCommandReceived(@Nullable Command command) throws Exception {

        if (command == null) {
            onInvalidCommand();
            return;
        }

        switch (command) {
            case List:
                onListCommand();
                break;
            case History:
                onHistoryCommand();
                break;
            case User:
                onUserCommand(Command.User.argument);
                break;
            case Message:
                onMessageCommand(Command.Message.argument);
                break;
            case Quit:
                onQuitCommand();
                break;
        }
    }

    private void onStart() {

        sendMessageToClient("Hello!");

        ChatHistory.getInstance().register(this);
    }

    private void onStop() {

        Users.getInstance().remove(currentUser);
        ChatHistory.getInstance().deregister(this);

        sendMessageToClient("Goodbye.");
    }

    private void onListCommand() {

        sendMessageToClient(String.format("Users:\n%s", Users.getInstance()));
    }

    private void onHistoryCommand() {
        outputStream.println(String.format("History:\n%s", ChatHistory.getInstance()));
    }

    private void onUserCommand(@Nullable String argument) throws Exception {

        if (argument == null || argument.length() == 0) {

            String message = currentUser == null ?
                    "User not set" :
                    String.format("Username is %s", currentUser.name);

            sendMessageToClient(message);
            return;
        }

        String name = argument.trim();

        if (name.length() > MAX_USERNAME_LENGTH) {
            throw new Exception(String.format("Username is too long(%d max).", MAX_USERNAME_LENGTH));
        }

        User user = new User(name);

        if (Users.getInstance().exists(user)) {
            throw new Exception("Username is already taken.");
        }

        Users.getInstance().insert(user);
        currentUser = user;

        sendMessageToClient("Username is " + currentUser.name);
    }

    private void onMessageCommand(@Nullable String argument) throws Exception {

        if (currentUser == null) {
            throw new Exception("User not set. Use :user command.");
        }

        ChatMessage message = new ChatMessage(currentUser, argument.trim());

        ChatHistory.getInstance().insert(message);
    }

    private void sendMessageToClient(@NonNull String message) {
        System.out.println(String.format("Message to %s: %s", currentUser == null ? "" : currentUser, message));

        outputStream.println(message);
    }

    private void onQuitCommand() {

    }

    private void onInvalidCommand() {

        sendMessageToClient("Invalid command.");
    }

    private void onError(Exception error) {

        sendMessageToClient(error.getMessage());
    }

    @Override
    public void onChatHistoryUpdate(ChatMessage message) {
        if (currentUser == null) {
            return;
        }

        sendMessageToClient(message.toString());
    }
}
