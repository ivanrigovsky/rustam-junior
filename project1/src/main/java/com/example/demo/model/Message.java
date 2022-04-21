package com.example.demo.model;

import javax.persistence.*;

@Entity // Дает знать спрингу, что это сущность, которую нужно сохранять в базе данных
public class Message {

    @Id // Идентификатор позволяет различать две записи в одной таблице
    @GeneratedValue(strategy = GenerationType.AUTO) // Говорим, чтобы фреймворк вместе с базой данных сами разобрали в каком виде и в каком порядке хранить данные
    private Integer id;

    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public Message() { //Spring не сможет создать данный класс без пустого конструктора
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
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

}
