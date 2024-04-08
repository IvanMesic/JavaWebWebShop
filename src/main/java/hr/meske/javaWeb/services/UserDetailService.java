package hr.meske.javaWeb.services;

import hr.meske.javaWeb.model.*;
import hr.meske.javaWeb.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {

    private final PersonRepo userRepository;

    @Autowired
    public UserDetailService(PersonRepo userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getPrivilege());

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), Collections.singletonList(authority));
    }

    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    public long GetUsersIdByName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = userRepository.findByName(authentication.getName()).get().getId();
        return id;
    }

    public static Boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
                .contains("ADMIN");

    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public String getRole() {

        String role = "";
        String name = getUserName();

        if (userRepository.findByName(name).isPresent()) {
            role = userRepository.findByName(name).get().getPrivilege();
        }
        return role;
    }

    public long GetUserIdByProvidedName(String name) {

        return userRepository.findByName(name).get().getId();
    }
}

