package com.rol.connectivity;

import com.rol.models.CommandInterpreter;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Chat server
 *
 * 9/16/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
class SocketConnectionThread extends Thread {

    private final Socket socket;

    SocketConnectionThread(Socket socket) throws IOException {
        super(new CommandInterpreter(socket.getInputStream(), new PrintStream(socket.getOutputStream(), true)));

        this.socket = socket;

        initialize();
    }

    private void initialize() {
        System.out.printf("Socket connection with %s(%d) established\n", socket.getInetAddress(), socket.getPort());
    }

    @Override
    public void run() {
        super.run();

        deInitialize();
    }

    private void deInitialize() {

        try {
            socket.close();
        } catch (IOException e) {
            // nothing
        }

        System.out.printf("Socket %s(%d) is disconnected\n", socket.getInetAddress(), socket.getPort());
    }
}
