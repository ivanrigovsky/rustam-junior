package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity // Сущность, которую нужно сохранять в базе данных
public class User implements UserDetails {

    @Id // Идентификатор для различия записей в одной таблице
    @NotBlank(message = "Username cannot be empty")
    private String username;

    private String id;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    private boolean active;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    private String activationCode; // Для подтверждения, что пользователь, действительно, владеет этим email

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER) // Fetch - Подгрузка параметров (EAGER - при запросе пользователя сразу все подгружается)
    @Enumerated(EnumType.STRING) // Храним Enum в виде строки
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "user_subscription",
            joinColumns = { @JoinColumn(name = "channel_id")},
            inverseJoinColumns = { @JoinColumn(name = "subscriber_id")}
    )
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscription",
            joinColumns = { @JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = { @JoinColumn(name = "channel_id")}
    )
    private Set<User> subscription = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }


    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { // Указывает, истек ли срок действия учетной записи пользователя
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // Указывает, заблокирован или разблокирован пользователь
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // Указывает, истек ли срок действия учетных данных пользователя (пароля)
        return true;
    }

    @Override
    public boolean isEnabled() { // Указывает, включен или отключен пользователь
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<User> getSubscription() {
        return subscription;
    }

    public void setSubscription(Set<User> subscription) {
        this.subscription = subscription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


