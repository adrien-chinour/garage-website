package frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import frontend.api.UserApi;
import frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserApi userApi;

    @GetMapping("/user")
    public String list(Model model) {
        model.addAttribute("users", userApi.getAll());
        return "user/index";
    }

    @GetMapping("/user/{userId}")
    public String get(Model model, @PathVariable Integer userId) {
        model.addAttribute("user", userApi.get(userId));
        return "user/show";
    }

    @GetMapping("/user/new")
    public String create(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/new";
    }

    @GetMapping("/user/edit/{userId}")
    public String edit(Model model, @PathVariable Integer userId) {
        model.addAttribute("user", userApi.get(userId));
        return "user/edit";
    }

    @PostMapping("/user/add")
    public String post(@ModelAttribute User user) throws JsonProcessingException {
        userApi.create(user);
        return "redirect:/user";
    }

    @PostMapping("/user/edit")
    public String put(@ModelAttribute User user) throws JsonProcessingException {
        userApi.edit(user);
        return "redirect:/user/" + user.getId();
    }
}
