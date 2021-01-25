package com.hodor.service;

import com.hodor.pojo.Blog;
import com.hodor.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/19
 * @description ：
 * @version: 1.0
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listPage(Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    List<Blog> listBlogTop(Integer size);

    Page<Blog> listBlog(String query, Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long  id, Blog blog);

    void deleteBlog(Long id);

    void updateViews(Long id);
}
