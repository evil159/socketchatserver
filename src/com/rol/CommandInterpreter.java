package com.rol;

import com.sun.istack.internal.Nullable;
import com.sun.javafx.beans.annotations.NonNull;

import java.io.*;
import java.util.Scanner;

/**
 * U-type
 * <p>
 * Created by Roman Laitarenko on 9/13/16.
 */
public class CommandInterpreter {

    private static final int MAX_USERNAME_LENGTH = 100;

    private final InputStream inputStream;
    private final OutputStream outputStream;

    private User currentUser = null;

    CommandInterpreter(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

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

    private void writeToOutput(@NonNull String string) {

        try {
            outputStream.write(string.getBytes("UTF-8"));
            ;
        } catch (IOException e) {
            // oops
            e.printStackTrace();
        }
    }

    private void onStart() {
        writeToOutput("Hello!\n");
    }

    private void onListCommand() {
        writeToOutput("Not implemented\n");
    }

    private void onHistoryCommand() {
        writeToOutput("Not implemented\n");
    }

    private void onUserCommand(@Nullable String argument) throws Exception {

        if (argument == null || argument.length() == 0) {

            String message = currentUser == null ?
                    "User not set\n" :
                    String.format("Username is %s\n", currentUser.name);

            writeToOutput(message);
            return;
        }

        String name = argument.trim();

        if (name.length() > MAX_USERNAME_LENGTH) {
            throw new Exception(String.format("Username is too long(%d max).\n", MAX_USERNAME_LENGTH));
        }

        currentUser = new User(name, "host");

        writeToOutput(String.format("Username is %s\n", currentUser.name));
    }

    private void onMessageCommand(@Nullable String argument) throws Exception {

        if (currentUser == null) {
            throw new Exception("User not set. Use :user command.\n");
        }

        writeToOutput(String.format("%s:%s\n", currentUser, argument.trim()));
    }

    private void onQuitCommand() {

        writeToOutput("Goodbye.\n");
    }

    private void onInvalidCommand() {

        writeToOutput("Invalid command.\n");
    }

    private void onError(Exception error) {

        writeToOutput(error.getMessage());
    }
}
