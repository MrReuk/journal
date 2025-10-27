package com.mpt.journal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "Lumber")
public class LumberModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_lumber")
    private Integer id;

    @NotNull(message = "Тип пиломатериала обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_lumber_ID", nullable = false)
    private TypeOfLumberModel typeOfLumber;

    @NotNull(message = "Вид дерева обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_species_ID", nullable = false)
    private TreeSpeciesModel treeSpecies;

    @NotNull(message = "Объем обязателен")
    @DecimalMin(value = "0.01", message = "Объем должен быть больше 0")
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal volume;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public TypeOfLumberModel getTypeOfLumber() { return typeOfLumber; }
    public void setTypeOfLumber(TypeOfLumberModel typeOfLumber) { this.typeOfLumber = typeOfLumber; }
    public TreeSpeciesModel getTreeSpecies() { return treeSpecies; }
    public void setTreeSpecies(TreeSpeciesModel treeSpecies) { this.treeSpecies = treeSpecies; }
    public BigDecimal getVolume() { return volume; }
    public void setVolume(BigDecimal volume) { this.volume = volume; }
}