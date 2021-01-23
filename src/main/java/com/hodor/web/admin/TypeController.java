package com.hodor.web.admin;

import com.hodor.pojo.Type;
import com.hodor.service.TypeService;
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
import java.util.List;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/16
 * @description ：
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 跳转分类页面
     * @param pageable 前端构建的参数
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 8, sort="id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Type> typePage = typeService.listType(pageable);
        model.addAttribute("page", typePage);
        return "admin/types";
    }

    @GetMapping("types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 跳转到编辑分类的页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 新增分类
     * @param type
     * @param result 可以接收校验后的结果(pojo中注解添加了后端的校验)
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }
        if(result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if(t == null) {
            redirectAttributes.addFlashAttribute("message","新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message","新增成功");
        }
        //返回分页的页面，使用重定向
        return "redirect:/admin/types";
    }

    /**
     * 修改type提交处理
     * @param type
     * @param result 可以接收校验后的结果(pojo中注解添加了后端的校验)
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable  Long id,
                           RedirectAttributes redirectAttributes, Model model) {
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }
        if(result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if(t == null) {
            redirectAttributes.addFlashAttribute("message","更新失败");
        } else {
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        //返回分页的页面，使用重定向
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
