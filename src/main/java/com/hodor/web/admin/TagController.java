package com.hodor.web.admin;

import com.hodor.pojo.Tag;
import com.hodor.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/18
 * @description ：
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 分页查询所有标签
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Tag> tagPage = tagService.listTag(pageable);
        Map map = new HashMap<>();
        map.put("content", tagPage.getContent());
        map.put("totalPages", tagPage.getTotalPages());
        map.put("number", tagPage.getPageable().getPageNumber());
        map.put("first", tagPage.getPageable().getPageNumber() == 0);
        map.put("last", tagPage.getPageable().getPageNumber() == tagPage.getTotalPages() - 1);
        model.addAttribute("page", map);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**
     * 跳转到编辑标签的页面，实际上和新增标签的页面是相同的
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    /**
     * 新增标签
     * @param tag
     * @param result
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加标签");
        }
        //后台的校验
        if(result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null) {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 修改标签
     * @param tag
     * @param result
     * @param id
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加标签");
        }
        if(result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if(t == null) {
            redirectAttributes.addFlashAttribute("message", "修改失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
