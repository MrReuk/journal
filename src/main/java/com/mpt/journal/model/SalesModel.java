package com.mpt.journal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sales")
public class SalesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_sales")
    private Integer id;

    @NotNull(message = "Сотрудник обязателен")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "employee_ID", nullable = false)
    private UserModel employee;

    @NotNull(message = "Покупатель обязателен")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "buyers_ID", nullable = false)
    private UserModel buyer;

    @NotNull(message = "Платеж обязателен")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "pay_ID", nullable = false)
    private PayModel pay;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "lumber_ID")
    private LumberModel lumber;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public UserModel getEmployee() { return employee; }
    public void setEmployee(UserModel employee) { this.employee = employee; }
    public UserModel getBuyer() { return buyer; }
    public void setBuyer(UserModel buyer) { this.buyer = buyer; }
    public PayModel getPay() { return pay; }
    public void setPay(PayModel pay) { this.pay = pay; }
    public LumberModel getLumber() { return lumber; }
    public void setLumber(LumberModel lumber) { this.lumber = lumber; }

    // Геттеры для обратной совместимости
    public Integer getEmployeeId() {
        return employee != null ? employee.getId().intValue() : null;
    }

    public Integer getBuyerId() {
        return buyer != null ? buyer.getId().intValue() : null;
    }

    public Integer getPayId() {
        return pay != null ? pay.getId() : null;
    }

    public Integer getLumberId() {
        return lumber != null ? lumber.getId() : null;
    }

    // Сеттеры для обратной совместимости
    public void setEmployeeId(Integer employeeId) {
        if (employeeId != null) {
            UserModel user = new UserModel();
            user.setId(employeeId.longValue());
            this.employee = user;
        }
    }

    public void setBuyerId(Integer buyerId) {
        if (buyerId != null) {
            UserModel user = new UserModel();
            user.setId(buyerId.longValue());
            this.buyer = user;
        }
    }

    public void setPayId(Integer payId) {
        if (payId != null) {
            PayModel payment = new PayModel();
            payment.setId(payId);
            this.pay = payment;
        }
    }

    public void setLumberId(Integer lumberId) {
        if (lumberId != null) {
            LumberModel lumberItem = new LumberModel();
            lumberItem.setId(lumberId);
            this.lumber = lumberItem;
        }
    }
}