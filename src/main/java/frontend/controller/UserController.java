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
public class UserController extends AbstractController {

    @Autowired
    private UserApi userApi;

    @Autowired
    private ApiAuthenticationManager apiAuthenticationManager;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("credentials", new Credentials());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("credentials") Credentials credentials, HttpServletRequest req) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        Authentication auth = apiAuthenticationManager.authenticate(authReq);
        if(auth == null) {
            return "redirect:/login?errors";
        }
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return "redirect:/";
    }

    @GetMapping("/register/member")
    public String registerMember(Model model) {
        model.addAttribute("user", new User());
        return "user/register-member";
    }

    @GetMapping("/register/partner")
    public String registerPartner(Model model) {
        model.addAttribute("user", new User());
        return "user/register-partner";
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
    public String edit(@ModelAttribute("user") User user, Model model) throws JsonProcessingException {
        if(!isAuthenticated()) {
            return "redirect:/login";
        }
        User authenticated = getUser();
        user.setId(authenticated.getId());
        user.setStatus(authenticated.getStatus());
        userApi.edit(user);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        if (!isAuthenticated()) {
            return "redirect:/login";
        }
        model.addAttribute("user", getUser());
        return "user/show";
    }
}
