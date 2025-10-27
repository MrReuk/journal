package com.mpt.journal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "role_users")
public class RoleUsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_role")
    private Integer id;

    @NotBlank(message = "Название роли обязательно")
    @Size(max = 30, message = "Название роли не должно превышать 30 символов")
    @Column(nullable = false, length = 30)
    private String name;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}