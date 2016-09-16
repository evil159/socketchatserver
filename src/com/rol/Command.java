package com.rol;

import com.sun.istack.internal.Nullable;
import com.sun.javafx.beans.annotations.NonNull;

/**
 * U-type
 * <p>
 * Created by Roman Laitarenko on 9/13/16.
 */
public enum Command {
    List("list"),
    History("history"),
    User("user"),
    Message(""),
    Quit("quit");

    private static final String COMMAND_PREFIX = ":";

    public final String name;
    public String argument;

    Command(@NonNull String name) {
        this.name = name;
    }

    @Nullable
    public static Command fromInput(@NonNull String input) {

        if (!input.startsWith(COMMAND_PREFIX)) {
            Message.argument = input;
            return Message;
        }

        String[] components = input.split("\\s+");
        String action = components[0];

        if (action.length() <= 1) {
            return null;
        }

        action = action.substring(1, action.length());
        Command result = null;

        for (Command command : Command.values()) {

            if (command.name.equals(action.trim().toLowerCase())) {
                result = command;
                break;
            }
        }

        // supporting up to 1 command argument at the moment
        if (result != null) {
            result.argument = components.length > 1 ? components[1] : null;
        }

        return result;
    }
}
