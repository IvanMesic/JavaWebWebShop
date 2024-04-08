package hr.meske.javaWeb.controller;

import hr.meske.javaWeb.model.UserLog;
import hr.meske.javaWeb.services.UserLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/logController")
public class LogController {
    private final UserLogService userLogService;

    public LogController(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    @GetMapping("/logs")
    public String showLogs(Model model) {
        List<UserLog> logs = userLogService.getAllLogs();
        model.addAttribute("logs", logs);
        return "logs";
    }
}
