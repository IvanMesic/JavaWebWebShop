package hr.meske.javaWeb.filters;


import hr.meske.javaWeb.model.User;
import hr.meske.javaWeb.repo.UserLogRepository;
import hr.meske.javaWeb.services.UserLogService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IpLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(IpLoggingFilter.class);

    private UserLogService userLogService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod()) && "/login".equals(httpRequest.getServletPath())) {
            String ipAddress = httpRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = request.getRemoteAddr();
            }

            logger.info("Login attempt from IP: " + ipAddress);
        }

        chain.doFilter(request, response);
    }

}
