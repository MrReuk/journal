package com.mpt.journal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "Lumber")
public class LumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_lumber")
    private Integer id;

    @NotNull(message = "ID типа пиломатериала обязателен")
    @Positive(message = "ID типа пиломатериала должен быть положительным")
    @Column(name = "type_of_lumber_ID", nullable = false)
    private Integer typeOfLumberId;

    @NotNull(message = "ID вида дерева обязателен")
    @Positive(message = "ID вида дерева должен быть положительным")
    @Column(name = "tree_species_ID", nullable = false)
    private Integer treeSpeciesId;

    @NotNull(message = "Объем обязателен")
    @DecimalMin(value = "0.01", message = "Объем должен быть больше 0")
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal volume;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getTypeOfLumberId() { return typeOfLumberId; }
    public void setTypeOfLumberId(Integer typeOfLumberId) { this.typeOfLumberId = typeOfLumberId; }
    public Integer getTreeSpeciesId() { return treeSpeciesId; }
    public void setTreeSpeciesId(Integer treeSpeciesId) { this.treeSpeciesId = treeSpeciesId; }
    public BigDecimal getVolume() { return volume; }
    public void setVolume(BigDecimal volume) { this.volume = volume; }
}