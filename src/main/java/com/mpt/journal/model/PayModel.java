package com.mpt.journal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pay")
public class PayModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_pay")
    private Integer id;

    @NotNull(message = "Тип оплаты обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_pay_ID", nullable = false)
    private TypePayModel typePay;

    @NotNull(message = "Покупатель обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyers_ID", nullable = false)
    private UserModel buyer;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "pay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SalesModel> sales = new HashSet<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public TypePayModel getTypePay() { return typePay; }
    public void setTypePay(TypePayModel typePay) { this.typePay = typePay; }
    public UserModel getBuyer() { return buyer; }
    public void setBuyer(UserModel buyer) { this.buyer = buyer; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Set<SalesModel> getSales() { return sales; }
    public void setSales(Set<SalesModel> sales) { this.sales = sales; }

    // Геттеры для обратной совместимости
    public Integer getTypePayId() {
        return typePay != null ? typePay.getId() : null;
    }

    public Integer getBuyerId() {
        return buyer != null ? buyer.getId().intValue() : null;
    }

    // Сеттеры для обратной совместимости
    public void setTypePayId(Integer typePayId) {
        if (typePayId != null) {
            TypePayModel type = new TypePayModel();
            type.setId(typePayId);
            this.typePay = type;
        }
    }

    public void setBuyerId(Integer buyerId) {
        if (buyerId != null) {
            UserModel user = new UserModel();
            user.setId(buyerId.longValue());
            this.buyer = user;
        }
    }
}