package com.hodor.dao;

import com.hodor.pojo.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/19
 * @description ：
 * @version: 1.0
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
}
