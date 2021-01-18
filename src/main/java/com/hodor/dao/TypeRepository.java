package com.hodor.dao;

import com.hodor.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：XXXX
 * @date ：Created in 2021/1/16
 * @description ：
 * @version: 1.0
 */
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);
}
