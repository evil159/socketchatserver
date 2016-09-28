package com.rol.connectivity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Chat server
 *
 * 9/16/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
public class ChatServer {

    private static final int SERVER_SOCKET_QUEUE_SIZE = 3;

    private final ServerSocket serverSocket;

    public ChatServer() throws Exception {

        serverSocket = new ServerSocket(0, SERVER_SOCKET_QUEUE_SIZE);
    }

    public void serve() {

        System.out.println(String.format("Running socket server on port %d", serverSocket.getLocalPort()));

        while (true) {

            try {
                Socket socket = serverSocket.accept();
                SocketConnectionThread thread = new SocketConnectionThread(socket);

                thread.start();
            } catch (IOException e) {
                System.out.printf("Failed to establish socket connection, reason: %s\n", e.getMessage());
                e.printStackTrace();
            }

        }
    }
}
