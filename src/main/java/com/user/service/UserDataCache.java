package com.user.service;

import com.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDataCache {

    boolean addUser(User user);

    Optional<User> getUser(Long id);

    List<User> getUsers(String lastName);

    List<User> getAllUsers();
}
