package com.hodor.web;

import com.hodor.pojo.Type;
import com.hodor.service.BlogService;
import com.hodor.service.TypeService;
import com.hodor.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/25
 * @description ：
 * @version: 1.0
 */
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 6, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        //从导航进入的情况
        if(id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }

}
