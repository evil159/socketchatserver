package com.rol.models;

import com.sun.javafx.beans.annotations.NonNull;

/**
 * Chat server
 *
 * 9/13/16
 * Roman Laitarenko
 * Apollinariia Gainulenko
 * Amirhossein Nasarbeigi
 */
public class User {

    final String name;

    public User(@NonNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof User) {
            return ((User) obj).name.equalsIgnoreCase(name);
        }

        return false;
    }
}
