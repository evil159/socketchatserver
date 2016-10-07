package com.rol.models;

import java.util.ArrayList;

/**
 * Chat server
 *
 * 9/21/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
public class Users {

    private final static ArrayList<User> users = new ArrayList<User>();
    private static Users staticUsers = null;

    private Users() {

    }

    public static Users getInstance() {
        if (staticUsers == null) {
            staticUsers = new Users();
        }

        return staticUsers;
    }

    boolean exists(User user) {
        return users.contains(user);
    }

    void insert(User user) {
        users.add(user);
    }

    void remove(User user) {
        users.remove(user);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (User user : users) {
            result.append(user);
            result.append("\n");
        }

        return result.toString();
    }
}
