package it.intesys.academy.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SecurityControllerAdvice {

    @ModelAttribute("currentUsername")
    public String currentUsername() {

        Object principal = SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            return user.getUsername();
        }
        return null;
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {

        return SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch("ROLE_ADMIN"::equals);
    }
}
