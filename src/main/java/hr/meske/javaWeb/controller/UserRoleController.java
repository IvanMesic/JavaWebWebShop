package hr.meske.javaWeb.controller;

import hr.meske.javaWeb.services.UserDetailService;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@EnableWebSecurity
@RequestMapping("/userRoleController")
@RestController
@NoArgsConstructor
public class UserRoleController {

    UserDetailService userDetailService;

    @GetMapping("/userInfo")
    public Map<String, String> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String role = roles.contains("ADMIN") ? "ADMIN" : roles.contains("USER") ? "USER" : "GUEST";

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", authentication.getName());
        userInfo.put("role", role);


        return userInfo;
    }

    @GetMapping("/getRole")
    public String getRole() {

        String role = userDetailService.getRole();
        return role;

    }

    @GetMapping("/isAuthenticated")
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !authentication.getName().equals("anonymousUser");
    }

    @GetMapping("/username")
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !authentication.getName().equals("anonymousUser"))
        {
            return authentication.getName();
        }
        else  {
            return "GUEST";
        }


    }


}
