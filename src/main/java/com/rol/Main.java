package com.rol;

import com.rol.connectivity.ChatServer;

/**
 * Chat server
 *
 * 9/13/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
public class Main {

    public static void main(String[] args) {

        ChatServer server;

        try {
            server = new ChatServer();
        } catch (Exception e) {

            System.out.println("Failed to start the server");
            e.printStackTrace();
            return;
        }

        server.serve();
    }
}
