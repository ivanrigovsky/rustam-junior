package com.example.demo.api;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

        @GetMapping("/get") // Указываем URL который будет приходить в этот метод контроллера
        public User getUser(@RequestParam(value = "login") String login, // Через аннотацию задаем параметры для запроса
                            @RequestParam(value = "password") String password) {
            User user1 = userRepository.findByLogin(login);
            if (user1.getPassword().equals(password)) {
                return user1;
            } else
                return null;
        }

        @PostMapping("/update")
    public User changeUser(@RequestBody User user) {
          userRepository.save(user);
          return user;
        }
}
