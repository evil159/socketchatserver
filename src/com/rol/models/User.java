package com.rol.models;

import com.sun.javafx.beans.annotations.NonNull;

/**
 * U-type
 * <p>
 * Created by Roman Laitarenko on 9/13/16.
 */
public class User {

    final String name;
    final String host;

    public User(@NonNull String name, @NonNull String host) {
        this.name = name;
        this.host = host;
    }

    @Override
    public String toString() {
        return String.format("%s@%s", name, host);
    }
}
