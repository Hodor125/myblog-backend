package com.hodor.service.impl;

import com.hodor.dao.FriendRepository;
import com.hodor.exception.NotFoundException;
import com.hodor.pojo.Friends;
import com.hodor.service.FriendService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/29
 * @description ：
 * @version: 1.0
 */
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Override
    public Page<Friends> listFriend(Pageable pageable) {
        return friendRepository.findAll(pageable);
    }

    /**
     * 保存友链
     * @param friends
     * @return
     */
    @Transactional
    @Override
    public Friends saveFriends(Friends friends) {
        return friendRepository.save(friends);
    }

    /**
     * 通过id查找
     * @param id
     * @return
     */
    @Override
    public Friends getFriendsById(Long id) {
        return friendRepository.getOne(id);
    }

    @Transactional
    @Override
    public Friends updateFriends(Long id, Friends friends) {
        Friends f = friendRepository.getOne(id);
        if(f == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(friends, f);
        return friendRepository.save(f);
    }

    @Transactional
    @Override
    public void deleteFriends(Long id) {
        friendRepository.deleteById(id);
    }
}
