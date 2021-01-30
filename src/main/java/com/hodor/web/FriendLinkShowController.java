package com.hodor.web;

import com.hodor.pojo.Friends;
import com.hodor.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/30
 * @description ：
 * @version: 1.0
 */
@Controller
public class FriendLinkShowController {
    @Autowired
    private FriendService friendService;

    @GetMapping("/friends")
    public String friends(@PageableDefault(size = 8, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Friends> friendsPage = friendService.listFriend(pageable);
        model.addAttribute("page", friendsPage);
        return "friends";
    }
}
