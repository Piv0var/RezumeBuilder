package com.example.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Представляет сущность пользователя в приложении.
 * Этот класс аннотирован как JPA-сущность и используется для хранения данных о пользователях в базе данных.
 */
@Entity
public class UserModel {

    /**
     * Уникальный идентификатор пользователя.
     * Генерируется автоматически базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Электронная почта пользователя.
     */
    private String email;

    /**
     * Конструктор по умолчанию для класса UserModel.
     * Необходим для работы JPA.
     */
    public UserModel() {}

    /**
     * Конструктор для создания нового пользователя с указанными именем и электронной почтой.
     *
     * @param name  имя пользователя
     * @param email электронная почта пользователя
     */
    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Возвращает уникальный идентификатор пользователя.
     *
     * @return уникальный идентификатор пользователя
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор пользователя.
     *
     * @param id уникальный идентификатор пользователя
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param name имя пользователя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает электронную почту пользователя.
     *
     * @return электронная почта пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает электронную почту пользователя.
     *
     * @param email электронная почта пользователя
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
