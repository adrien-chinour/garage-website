package frontend.controller;

import frontend.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

abstract class AbstractController {

    protected Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    protected boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    protected boolean isGranted(String role) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return getAuthentication().getAuthorities().contains(authority);
    }

    protected User getUser() {
        if (getAuthentication().isAuthenticated() && getAuthentication().getDetails().getClass() == User.class) {
            return (User) getAuthentication().getDetails();
        }
        return null;
    }

}
