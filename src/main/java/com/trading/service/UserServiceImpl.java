package com.trading.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.config.JwtProvider;
import com.trading.model.User;
import com.trading.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception{
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception{
        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("User not found");
        }
        return user;
    }
    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user=userRepository.findById(userId);
        if(user.isEmpty()){
            throw new Exception("User not found");
        }
        return user.get();
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
