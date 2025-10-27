package com.mpt.journal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_users")
    private Long id;

    @NotBlank(message = "Фамилия обязательна")
    @Size(max = 30, message = "Фамилия не должна превышать 30 символов")
    @Column(nullable = false, length = 30)
    private String surname;

    @NotBlank(message = "Имя обязательно")
    @Size(max = 30, message = "Имя не должно превышать 30 символов")
    @Column(nullable = false, length = 30)
    private String name;

    @Size(max = 30, message = "Отчество не должно превышать 30 символов")
    @Column(name = "middle_name", length = 30)
    private String middleName;

    @Column(name = "role_ID")
    private Integer roleId;

    @NotBlank(message = "Логин обязателен")
    @Size(max = 30, message = "Логин не должен превышать 30 символов")
    @Column(nullable = false, unique = true, length = 30)
    private String login;

    @NotBlank(message = "Пароль обязателен")
    @Size(max = 30, message = "Пароль не должен превышать 30 символов")
    @Column(nullable = false, length = 30)
    private String password;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}