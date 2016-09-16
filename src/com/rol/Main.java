package com.rol;

public class Main {

    public static void main(String[] args) {

        CommandInterpreter commandInterpreter = new CommandInterpreter(System.in, System.out);

        commandInterpreter.run();
    }
}
