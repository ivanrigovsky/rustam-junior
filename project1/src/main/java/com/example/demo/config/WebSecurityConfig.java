package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // Класс, который при старте приложения конфигурирует WebSecurity
    @Autowired
    private UserService userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception { // Система заходит сюда и передает на вход объект
        http // мы в нем включаем:
                .authorizeRequests() // авторизацию
                    .antMatchers("/", "/registration").permitAll() // Разрешаем для главной страничке и странице регистрации полный доступ
                    .anyRequest().authenticated() // Для всех остальных запросов требуем авторизацию
                .and()
                    .formLogin() // Включаем форму Login
                    .loginPage("/login") // Указываем, что loginPage на данном мэппинге
                    .permitAll() // Разрешаем этим пользоваться всем
                .and()
                    .logout() // Включаем logout
                    .permitAll(); // Разрешаем им пользоваться всем
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance()); // Шифрует пароли
    }
}

