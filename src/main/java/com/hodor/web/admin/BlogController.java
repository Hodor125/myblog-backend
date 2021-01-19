package com.hodor.web.admin;

import com.hodor.pojo.Blog;
import com.hodor.service.BlogService;
import com.hodor.service.TypeService;
import com.hodor.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/16
 * @description ：
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        Page<Blog> blogPage = blogService.listBlog(pageable, blog);
        Map map = new HashMap<>();
        map.put("content", blogPage.getContent());
        map.put("totalPages", blogPage.getTotalPages());
        map.put("number", blogPage.getPageable().getPageNumber());
        map.put("first", blogPage.getPageable().getPageNumber() > 0);
        map.put("first", blogPage.getPageable().getPageNumber() < blogPage.getTotalPages() - 1);
        model.addAttribute("page", blogPage);
        model.addAttribute("types", typeService.listType());
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        Page<Blog> blogPage = blogService.listBlog(pageable, blog);
        Map map = new HashMap<>();
        map.put("content", blogPage.getContent());
        map.put("totalPages", blogPage.getTotalPages());
        map.put("number", blogPage.getPageable().getPageNumber());
        map.put("first", blogPage.getPageable().getPageNumber() > 0);
        map.put("first", blogPage.getPageable().getPageNumber() < blogPage.getTotalPages() - 1);
        model.addAttribute("page", blogPage);
        //只刷新blogList区域的内容
        return "admin/blogs :: blogList";
    }
}
