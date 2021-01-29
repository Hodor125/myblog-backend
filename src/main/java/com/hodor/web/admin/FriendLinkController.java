package com.hodor.web.admin;

import com.hodor.pojo.Friends;
import com.hodor.service.FriendService;
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

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/29
 * @description ：
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin")
public class FriendLinkController {
    @Autowired
    private FriendService friendService;

    @GetMapping("/friends")
    public String friends(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Friends> friendsPage = friendService.listFriend(pageable);
        model.addAttribute("page", friendsPage);
        return "/admin/friends";
    }

    @GetMapping("/friends/input")
    public String input(Model model) {
        model.addAttribute("friends", new Friends());
        return "/admin/friends-input";
    }

    @GetMapping("/friends/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        Friends friendsById = friendService.getFriendsById(id);
        model.addAttribute("friends", friendsById);
        return "/admin/friends-input";
    }

    /**
     * 新增友链
     * @param friends
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/friends")
    public String post(@Valid Friends friends, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "/admin/friends-input";
        }
        Friends f = friendService.saveFriends(friends);
        if(f == null) {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/friends";
    }

    /**
     * 修改友链
     * @param friends
     * @param id
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/friends/{id}")
    public String editPost(@Valid Friends friends,  BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "/admin/friends-input";
        }
        Friends f = friendService.updateFriends(id, friends);
        if(f == null) {
            redirectAttributes.addFlashAttribute("message", "修改失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/friends";
    }

    @GetMapping("/friends/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        friendService.deleteFriends(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/friends";
    }
}
