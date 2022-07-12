package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void addUser() {
        User user = new User();
        user.setEmail("some@gmail.com");
        boolean isUserCreated = userService.addUser(user);

        Assertions.assertTrue(isUserCreated);
        Assertions.assertNotNull(user.getActivationCode());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepository, Mockito.times(1)).save(user); // Проверяем, что к userRepository было одно обращение save(user)
        Mockito.verify(mailSender, Mockito.times(1)) // Проверяем, что к mailSender было одно обращение send c параметрами
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void userFailTest() {
        User user = new User();
        user.setUsername("John");

        Mockito.doReturn(new User()) // Возвращаем нового пользователя,
                .when(userRepository) // когда в userRepository
                .findByUsername("John"); // вызывается метод findByUsername с аргументом "John"

        boolean isUserCreated = userService.addUser(user);

        Assertions.assertFalse(isUserCreated);

        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class)); // Проверяем, что к userRepository не было обращение save с любым объектом класса User
        Mockito.verify(mailSender, Mockito.times(0)) // Проверяем, что к mailSender не было обращение send c параметрами
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void activateUser() {
        User user = new User();

        Mockito.doReturn(user) // Возвращаем пользователя,
                .when(userRepository) // когда в userRepository
                .findByActivationCode("activate"); // вызывается метод findByActivationCode с параметром "Activate"

        boolean isUserActivated = userService.activateUser("activate");

        Assertions.assertTrue(isUserActivated); // Проверяем, что пользователь успешно активирован
        Assertions.assertNull(user.getActivationCode()); // Проверяем, что activationCode не равен null

        Mockito.verify(userRepository, Mockito.times(1)).save(user); // Проверяем, что к userRepository было одно обращение save(user)
    }

    @Test
    public void activateUserFailTest() {
        boolean isUserActivated = userService.activateUser("activate me"); // isUserActivated равен false, потому что нет пользователя с таким activationCode

        Assertions.assertFalse(isUserActivated); // Проверяем, что пользователь не активирован

        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class)); // Проверяем, что к userRepository не было обращение save с любым объектом класса User

    }
}