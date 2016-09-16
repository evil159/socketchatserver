package com.rol;

import com.sun.istack.internal.Nullable;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * U-type
 * <p>
 * Created by Roman Laitarenko on 9/13/16.
 */
public class CommandInterpreter {

    private static final int MAX_USERNAME_LENGTH = 100;

    private final InputStream inputStream;
    private final PrintStream outputStream;

    private User currentUser = null;

    CommandInterpreter(InputStream inputStream, PrintStream outputStream) {
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

    private void onStart() {

        outputStream.println("Hello!");
    }

    private void onListCommand() {

        outputStream.println("Not implemented:\n");
    }

    private void onHistoryCommand() {

        outputStream.println("Not implemented:\n");
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

        currentUser = new User(name, "host");

        outputStream.printf("Username is %s\n", currentUser.name);
    }

    private void onMessageCommand(@Nullable String argument) throws Exception {

        if (currentUser == null) {
            throw new Exception("User not set. Use :user command.");
        }

        outputStream.printf("%s:%s\n", currentUser, argument.trim());
    }

    private void onQuitCommand() {

        outputStream.println("Goodbye.");
    }

    private void onInvalidCommand() {

        outputStream.println("Invalid command.");
    }

    private void onError(Exception error) {

        outputStream.println(error.getMessage());
    }
}
