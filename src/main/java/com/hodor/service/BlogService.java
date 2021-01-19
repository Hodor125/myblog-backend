package com.hodor.service;

import com.hodor.pojo.Blog;
import com.hodor.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/19
 * @description ：
 * @version: 1.0
 */
public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long  id, Blog blog);

    void deleteBlog(Long id);
}
