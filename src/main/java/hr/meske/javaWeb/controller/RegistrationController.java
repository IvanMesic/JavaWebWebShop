package hr.meske.javaWeb.controller;

import hr.meske.javaWeb.model.User;
import hr.meske.javaWeb.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private PersonRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, "admin"); // Assuming your User entity has a constructor like User(String username, String password)
        userRepository.save(user);
        return "redirect:/login"; // Redirect to login page after successful registration
    }
}