package com.example.demo.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("User not found");
        }
        return userRepository.findByUsername(username);
    }

    /**
     * Add new user
     */
    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Устанавливаем пароль с шифрованием

        userRepository.save(user);

        sendMessage(user); // Отправка пользователю сообщения

        return true;
    }

    /**
     * Sending a message to the user with an activation key
     */
    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) { // Если email не пустой
            String message = String.format("Hello %s! \n" +
                            "Welcome to Project1. Please, visit next link: http://localhost:8005/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            ); // Формирование сообщения с ключом активации пользователю
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    /**
     * Saving a user after activation
     */
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Saves user changes (name, roles)
     */
    public void saveUser(String newUsername, String username, Map<String, String> form) { //разобрать код (6)
        User user = userRepository.findByUsername(username);
        user.setUsername(newUsername);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet())
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        userRepository.save(user);
    }

    /**
     * Updates the user his data and sends message with the activation key
     */
    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && userEmail.equals(email));

        if (isEmailChanged) { // Если email обновлен
            user.setEmail(email); // Сохраняем его

            if (!StringUtils.isEmpty(email)) { // Если пользователь установил новый email
                user.setActivationCode(UUID.randomUUID().toString()); // то присваиваем код активации
            }
        }
        if (StringUtils.isEmpty(password)) { // Если пользователь установил новый пароль
            user.setPassword(password); // присваиваем новый пароль
        }

        userRepository.save(user); // Сохраняем пользователя
        if (isEmailChanged) { // Если email изменен
            sendMessage(user); // то отправляем пользователю ключ активации
        }
    }
    public User findUser(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);

        userRepository.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}
