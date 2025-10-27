package com.mpt.journal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sales")
public class SalesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_sales")
    private Integer id;

    @NotNull(message = "Сотрудник обязателен")
    @Column(name = "employee_ID", nullable = false)
    private Integer employeeId;

    @NotNull(message = "Покупатель обязателен")
    @Column(name = "buyers_ID", nullable = false)
    private Integer buyerId;

    @NotNull(message = "Платеж обязателен")
    @Column(name = "pay_ID", nullable = false)
    private Integer payId;

    @Column(name = "lumber_ID")
    private Integer lumberId;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public Integer getBuyerId() { return buyerId; }
    public void setBuyerId(Integer buyerId) { this.buyerId = buyerId; }
    public Integer getPayId() { return payId; }
    public void setPayId(Integer payId) { this.payId = payId; }
    public Integer getLumberId() { return lumberId; }
    public void setLumberId(Integer lumberId) { this.lumberId = lumberId; }
}