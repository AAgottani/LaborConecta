package com.example.laborconecta.repositories;

import android.content.Context;

import com.example.laborconecta.contracts.UserContract;

import org.jetbrains.annotations.Nullable;

public final class UserRepository {

    private static UserRepository instance;

    private @Nullable UserContract.UserModel user;

    private UserRepository(Context context) {
    }

    public boolean isLogged() {
        return this.user != null;
    }

    public UserContract.UserModel getUser() {
        return user;
    }

    public void setUser(@Nullable UserContract.UserModel user) {
        this.user = user;
    }

    public static UserRepository getInstance(Context context) {

        if (instance == null) {
            instance = new UserRepository(context);
        }

        return instance;
    }
}
