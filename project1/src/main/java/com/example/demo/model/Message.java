package com.example.demo.model;

import com.example.demo.model.util.MessageHelper;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity // Сущность, которую нужно сохранять в базе данных
public class Message {

    @Id // Идентификатор для различия записей в одной таблице
    private String id;

    @NotBlank(message = "Please fill the message")
    @Length(max=2048, message = "Message too long (more than 2kB")
    private String text;
    @Length(max=255, message = "Message too long (more than 255")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER) // Одному пользователю соответствует множество сообщений
    private User author;

    private String filename;

   /* @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = { @JoinColumn(name = "message_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();*/

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }

    public Message() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /*public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }*/
}
