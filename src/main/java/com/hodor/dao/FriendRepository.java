package com.hodor.dao;

import com.hodor.pojo.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/29
 * @description ：
 * @version: 1.0
 */
public interface FriendRepository extends JpaRepository<Friends, Long> {
}
