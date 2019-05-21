package frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import frontend.api.UserApi;
import frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserApi userApi;

    @GetMapping("/register/member")
    public String registerMember(Model model) {
        User user = new User();
        user.setStatus("client");
        model.addAttribute("user", user);
        return "security/register";
    }

    @GetMapping("/register/partner")
    public String registerPartner(Model model) {
        User user = new User();
        user.setStatus("partner");
        model.addAttribute("user", user);
        return "security/register-partner";
    }

    @PostMapping("/user/add")
    public String create(@ModelAttribute User user) throws JsonProcessingException {
        userApi.create(user);
        return "redirect:/";
    }
}
