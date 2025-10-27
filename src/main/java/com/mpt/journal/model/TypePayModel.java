package com.mpt.journal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_pay")
public class TypePayModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_type_pay")
    private Integer id;

    @NotBlank(message = "Название типа оплаты обязательно")
    @Size(max = 30, message = "Название типа оплаты не должно превышать 30 символов")
    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "typePay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PayModel> payments = new HashSet<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<PayModel> getPayments() { return payments; }
    public void setPayments(Set<PayModel> payments) { this.payments = payments; }
}