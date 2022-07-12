package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // Класс, который при старте приложения конфигурирует WebSecurity
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // Система заходит сюда и передает на вход объект
        http // мы в нем включаем:
                .authorizeRequests() // авторизацию
                    .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll() // Разрешаем полный доступ без авторизации
                    .anyRequest().authenticated() // Для всех остальных запросов требуем авторизацию
                .and()
                    .formLogin() // Включаем форму Login
                    .loginPage("/login") // Указываем, что loginPage на данном мэппинге
                    .permitAll() // Разрешаем этим пользоваться всем
                .and()
                    .rememberMe()
                .and()
                    .logout() // Включаем logout
                    .permitAll(); // Разрешаем им пользоваться всем
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService) // Как это работает?
                .passwordEncoder(passwordEncoder); // Шифрует пароли, чтобы они не хранились в явном виде
    }
}

@Configuration
class BeanConfig {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}

