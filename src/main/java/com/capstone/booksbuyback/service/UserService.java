package com.capstone.booksbuyback.service;

import com.capstone.booksbuyback.model.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}