package frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import frontend.api.ApiAuthenticationManager;
import frontend.api.UserApi;
import frontend.model.Credentials;
import frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.security.Principal;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
public class UserController {

    private final UserApi userApi;

    private final ApiAuthenticationManager apiAuthenticationManager;

    @Autowired
    public UserController(UserApi userApi, ApiAuthenticationManager apiAuthenticationManager) {
        this.userApi = userApi;
        this.apiAuthenticationManager = apiAuthenticationManager;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("credentials", new Credentials());
        return "security/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("credentials") Credentials credentials, HttpServletRequest req) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        Authentication auth = apiAuthenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return "redirect:/";
    }

    @GetMapping("/register/member")
    public String registerMember(Model model) {
        model.addAttribute("user", new User());
        return "security/register";
    }

    @GetMapping("/register/partner")
    public String registerPartner(Model model) {
        model.addAttribute("user", new User());
        return "security/register-partner";
    }

    @PostMapping("/user/add/client")
    public String createMember(@ModelAttribute("user") User user, Model model) throws JsonProcessingException {
        user.setStatus("client");
        userApi.create(user);
        return "redirect:/";
    }

    @PostMapping("/user/add/partner")
    public String createPartner(@ModelAttribute("user") User user, Model model) throws JsonProcessingException {
        user.setStatus("partner");
        userApi.create(user);
        return "redirect:/";
    }

    @PostMapping("/user/edit")
    public String edit(@ModelAttribute("user") User user, Model model) throws  JsonProcessingException {
        // TODO add security
        userApi.edit(user);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(authentication.getPrincipal() == null) {
            return "redirect:/login";
        }
        User user = userApi.getByUsername(authentication.getPrincipal().toString());
        model.addAttribute("user", user);
        return "user/show";
    }
}
