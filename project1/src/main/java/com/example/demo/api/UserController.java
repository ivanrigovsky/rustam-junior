package com.example.demo.api;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    /**
     * Show a list of users
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    /**
     * Snow users (name, roles)
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{username}")
    public String userEditForm(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    /**
     * Edits users
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam String username,
                           @RequestParam Map<String, String> form,
                           @RequestParam String newUsername
    ) {
        userService.saveUser(newUsername, username, form);
        return "redirect:/user";
    }

    /**
     * Shows the user his data
     */
    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    /**
     * Updates the user his data
     */
    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }

    @GetMapping("suscribe/{username}")
    public String subscribe(@PathVariable String username,
                            @AuthenticationPrincipal User currentUser) {
        User user = userService.findUser(username);
        userService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getUsername();
    }

    @GetMapping("unsuscribe/{username}")
    public String unsubscribe(@PathVariable String username,
                            @AuthenticationPrincipal User currentUser) {
        User user = userService.findUser(username);
        userService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getUsername();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(Model model,
                           @PathVariable String username,
                           @PathVariable String type,
                           @AuthenticationPrincipal User currentUser) {
        User user = userService.findUser(username);
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);

        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscription());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subscriptions";
    }

}
