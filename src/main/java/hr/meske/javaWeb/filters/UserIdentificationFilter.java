package hr.meske.javaWeb.filters;

import hr.meske.javaWeb.repo.PersonRepo;
import hr.meske.javaWeb.services.UserDetailService;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class UserIdentificationFilter implements Filter {


    private final UserDetailService userService;

    public UserIdentificationFilter(UserDetailService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            Long userId = userService.GetUsersIdByName();
            request.setAttribute("userId", userId);
        }
        chain.doFilter(request, response);
    }




}
