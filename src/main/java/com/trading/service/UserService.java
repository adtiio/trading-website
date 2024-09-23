package com.trading.service;


import com.trading.model.User;

public interface UserService {

    public User findUserProfileByJwt(String jwt)throws Exception;
    public User findUserByEmail(String email)throws Exception;
    public User findUserById(Long userId)throws Exception;
    public User updatePassword(User user,String newPassword)throws Exception;
}
