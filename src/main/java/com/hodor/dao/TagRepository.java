package com.hodor.dao;

import com.hodor.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：XXXX
 * @date ：Created in 2021/1/18
 * @description ：
 * @version: 1.0
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);
}
