package com.mpt.journal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role_users")
public class RoleUsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_role")
    private Integer id;

    @NotBlank(message = "Название роли обязательно")
    @Size(max = 30, message = "Название роли не должно превышать 30 символов")
    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserModel> users = new HashSet<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<UserModel> getUsers() { return users; }
    public void setUsers(Set<UserModel> users) { this.users = users; }
}