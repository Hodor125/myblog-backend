package com.hodor.dao;

import com.hodor.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ：XXXX
 * @date ：Created in 2021/1/16
 * @description ：
 * @version: 1.0
 */
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
