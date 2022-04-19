package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // Дает знать спрингу, что это сущность, которую нужно сохранять в базе данных
public class Message {

    @Id // Идентификатор позволяет различать две записи в одной таблице
    @GeneratedValue(strategy = GenerationType.AUTO) // Говорим, чтобы фреймворк вместе с базой данных сами разобрали в каком виде и в каком порядке хранить данные
    private Integer id;

    private String text;
    private String tag;

    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }

    public Message() { //Spring не сможет создать данный класс без пустого конструктора
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
}
