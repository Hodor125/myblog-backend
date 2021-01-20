package com.hodor.web.admin;

import com.hodor.pojo.Blog;
import com.hodor.pojo.User;
import com.hodor.service.BlogService;
import com.hodor.service.TagService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

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
        //初始化分类
        model.addAttribute("types", typeService.listType());
        return LIST;
    }

    /**
     * 通过ajax方式请求模具部刷新
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
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

    /**
     * 新增博客
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return INPUT;
    }

    /**
     * 新增博客
     * @param blog
     * @param redirectAttributes
     * @param session
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b = blogService.saveBlog(blog);
        if(b != null) {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        }
        return REDIRECT_LIST;
    }
}
