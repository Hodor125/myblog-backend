package com.hodor.web;

import com.hodor.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/4
 * @description ：
 * @version: 1.0
 */
@Controller
public class indexController {

    @GetMapping("/")
    public String index(){
//        int i = 9 / 0;
//        String blog = null;
        //总的异常处理需要放行这个异常NotFoundException
//        if(blog == null) {
//            throw new NotFoundException("博客页面不存在");
//        }
        System.out.println("------------index----------------");
        return "index";
    }

    @GetMapping("/blog")
    public String blog(){
        System.out.println("------------blog----------------");
        return "blog";
    }

    @GetMapping("/tags")
    public String tags(){
        System.out.println("------------tags----------------");
        return "tags";
    }

    @GetMapping("/types")
    public String types(){
        System.out.println("------------types----------------");
        return "types";
    }

    @GetMapping("/archives")
    public String archives(){
        System.out.println("------------archives----------------");
        return "archives";
    }

    @GetMapping("/about")
    public String about(){
        System.out.println("------------about----------------");
        return "about";
    }

    @GetMapping("/adminIndex")
    public String adminIndex(){
        System.out.println("------------adminIndex----------------");
        return "admin/index";
    }

    @GetMapping("/adminBlogs")
    public String adminBlogs(){
        System.out.println("------------adminBlog----------------");
        return "admin/blogs";
    }

    @GetMapping("/adminBlogsInput")
    public String adminBlogsInput(){
        System.out.println("------------adminBlogInput----------------");
        return "admin/blogs-input";
    }

    @GetMapping("/adminLogin")
    public String adminBlogsLogin(){
        System.out.println("------------adminBlogInput----------------");
        return "admin/login";
    }
}
