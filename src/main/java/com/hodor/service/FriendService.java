package com.hodor.service;

import com.hodor.pojo.Friends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ：XXXX
 * @date ：Created in 2021/1/29
 * @description ：
 * @version: 1.0
 */
public interface FriendService {
    Page<Friends> listFriend(Pageable pageable);

    Friends saveFriends(Friends friends);

    Friends getFriendsById(Long id);

    Friends updateFriends(Long id, Friends friends);

    void deleteFriends(Long id);
}
