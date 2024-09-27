package com.buzz.service;

import com.buzz.exception.UserException;
import com.buzz.model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
