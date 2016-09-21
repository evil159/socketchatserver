package com.rol.models;

import com.rol.models.history.ChatHistory;
import com.rol.models.history.ChatHistoryObserver;
import com.rol.models.history.ChatMessage;
import com.sun.istack.internal.Nullable;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * U-type
 * <p>
 * Created by Roman Laitarenko on 9/13/16.
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

            String input = reader.nextLine();
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

        outputStream.println("Hello!");

        ChatHistory.getInstance().register(this);
    }

    private void onStop() {

        Users.getInstance().remove(currentUser);
        ChatHistory.getInstance().deregister(this);

        outputStream.println("Goodbye.");
    }

    private void onListCommand() {

        outputStream.println(String.format("Users:\n%s", Users.getInstance()));
    }

    private void onHistoryCommand() {
        outputStream.println(String.format("History:\n%s", ChatHistory.getInstance()));
    }

    private void onUserCommand(@Nullable String argument) throws Exception {

        if (argument == null || argument.length() == 0) {

            String message = currentUser == null ?
                    "User not set" :
                    String.format("Username is %s", currentUser.name);

            outputStream.println(message);
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

        outputStream.printf("Username is %s\n", currentUser.name);
    }

    private void onMessageCommand(@Nullable String argument) throws Exception {

        if (currentUser == null) {
            throw new Exception("User not set. Use :user command.");
        }

        ChatMessage message = new ChatMessage(currentUser, argument.trim());

        ChatHistory.getInstance().insert(message);
    }

    private void onQuitCommand() {

    }

    private void onInvalidCommand() {

        outputStream.println("Invalid command.");
    }

    private void onError(Exception error) {

        outputStream.println(error.getMessage());
    }

    @Override
    public void onChatHistoryUpdate(ChatMessage message) {
        if (currentUser == null) {
            return;
        }

        outputStream.println(message);
    }
}
