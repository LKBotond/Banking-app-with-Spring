package com.banking.backend.dao;

import java.util.Optional;

import com.banking.backend.users.User;

public interface UsersDao {
    void create(User user);

    Optional<String> checkForUserByEmail(String email);

    Optional<User> getUSerByEmail(String email);

}
