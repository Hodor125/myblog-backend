package com.hodor.service;

import com.hodor.pojo.User;

/**
 * @author ：XXXX
 * @date ：Created in 2021/1/16
 * @description ：
 * @version: 1.0
 */
public interface UserService {

    User checkUser(String name, String passWord);
}
