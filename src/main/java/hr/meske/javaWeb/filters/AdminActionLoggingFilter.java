package hr.meske.javaWeb.filters;

import hr.meske.javaWeb.model.EventType;
import hr.meske.javaWeb.services.UserLogService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class AdminActionLoggingFilter implements Filter {

    private final UserLogService userLogService;

    public AdminActionLoggingFilter(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if ("POST".equalsIgnoreCase(request.getMethod()) &&
                (request.getServletPath().equals("/itemController/addItem") || request.getServletPath().startsWith("/categoryController/addCategory"))) {

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            username = principal.getClass().getSimpleName().equals("String") ? (String) principal : ((UserDetails) principal).getUsername();
            String ipAddress = request.getRemoteAddr();

            userLogService.logUserEvent(username, ipAddress, EventType.ADMIN_ACTION);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }


}