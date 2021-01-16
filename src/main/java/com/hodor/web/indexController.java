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
}
