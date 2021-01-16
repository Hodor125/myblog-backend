package com.hodor.dao;

import com.hodor.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 本身只封装了数据库的基本操作，复杂的操作需要自行定义
 * @author ：XXXX
 * @date ：Created in 2021/1/16
 * @description ：
 * @version: 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserNameAndPassWord(String userName, String Password);
}
