package com.mpt.journal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "pay")
public class PayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_pay")
    private Integer id;

    @NotNull(message = "ID типа оплаты обязателен")
    @Positive(message = "ID типа оплаты должен быть положительным")
    @Column(name = "type_pay_ID", nullable = false)
    private Integer typePayId;

    @NotNull(message = "ID покупателя обязателен")
    @Positive(message = "ID покупателя должен быть положительным")
    @Column(name = "buyers_ID", nullable = false)
    private Integer buyerId;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getTypePayId() { return typePayId; }
    public void setTypePayId(Integer typePayId) { this.typePayId = typePayId; }
    public Integer getBuyerId() { return buyerId; }
    public void setBuyerId(Integer buyerId) { this.buyerId = buyerId; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}