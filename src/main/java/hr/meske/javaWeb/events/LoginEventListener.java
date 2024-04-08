package hr.meske.javaWeb.events;

import hr.meske.javaWeb.model.EventType;
import hr.meske.javaWeb.services.UserLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class LoginEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserLogService userLogService;

    public LoginEventListener(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        String ipAddress = request.getRemoteAddr();
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();

        System.out.println("Login success for user: " + userDetails.getUsername() + " from IP: " + ipAddress);

        userLogService.logUserEvent(userDetails.getUsername(), ipAddress, EventType.LOGIN_SUCCESS);
    }
}