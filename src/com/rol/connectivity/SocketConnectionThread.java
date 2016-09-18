package com.rol.connectivity;

import com.rol.models.CommandInterpreter;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * U-type
 * <p>
 * Created by Roman Laitarenko on 9/16/16.
 */
class SocketConnectionThread extends Thread {

    private final Socket socket;

    SocketConnectionThread(Socket socket) throws IOException {
        super(new CommandInterpreter(socket.getInputStream(), new PrintStream(socket.getOutputStream(), true)));

        this.socket = socket;

        System.out.printf("Socket connection with %s(%d) established\n", socket.getInetAddress(), socket.getPort());
    }

    @Override
    public void run() {
        super.run();

        try {
            socket.close();
        } catch (IOException e) {
            // nothing
        }

        System.out.printf("Socket %s(%d) is disconnected\n", socket.getInetAddress(), socket.getPort());
    }
}
