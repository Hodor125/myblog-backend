package com.hodor.service.impl;

import com.hodor.dao.UserRepository;
import com.hodor.pojo.User;
import com.hodor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/16
 * @description ：
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String name, String passWord) {
        User user = userRepository.findByUserNameAndPassWord(name, passWord);
        return user;
    }
}
