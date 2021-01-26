package com.hodor.web;

import com.hodor.service.BlogService;
import com.hodor.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 展示统计数据
 * @author ：hodor007
 * @date ：Created in 2021/1/26
 * @description ：
 * @version: 1.0
 */
@Controller
public class StatisticsShowController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/statistic")
    public String statistic(Model model) {
        Long countBlog = blogService.countBlog();
        Long countView = blogService.countView();
        Long countComment = commentService.countComment();
        model.addAttribute("articleNum",countBlog);
        model.addAttribute("viewNum",countView);
        model.addAttribute("commentNum", countComment);
        return "_fragments :: statisticList";
    }
}
