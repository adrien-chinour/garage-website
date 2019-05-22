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
import org.springframework.web.bind.annotation.PutMapping;

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

    @PostMapping("/user/edit")
    public String edit(@ModelAttribute User user) throws  JsonProcessingException {
        // TODO add security
        userApi.edit(user);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        // TODO add security
        User user = new User();
        user.setUsername("achinour");
        user.setName("Adrien");
        user.setFirst_name("Chinour");
        user.setStatus("member");
        user.setEmail("achinour@pm.me");
        user.setPhone("06 42 45 15 47");
        model.addAttribute("user", user);
        return "user/show";
    }
}
