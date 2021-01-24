package com.hodor.service.impl;

import com.hodor.pojo.Comment;

import java.util.List;

/**
 * @author ：XXXX
 * @date ：Created in 2021/1/24
 * @description ：
 * @version: 1.0
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
