package hr.meske.javaWeb.controller;

import hr.meske.javaWeb.model.User;
import hr.meske.javaWeb.services.UserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserDetailService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserDetailService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        logger.info("Accessing the list of users");
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/new")
    public String showUserForm(Model model) {
        logger.info("Showing user form for new user");
        User user = new User();
        model.addAttribute("user", user);
        return "form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") User user) {
        logger.info("Saving new user: {}", user.getName());
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            logger.info("Showing update form for user: {}", id);
            model.addAttribute("user", user.get());
        } else {
            logger.error("User not found with ID: {}", id);
        }
        return "edituser";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, Model model) {
        logger.info("Updating user: {}", id);
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
