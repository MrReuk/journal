package com.mpt.journal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tree_species")
public class TreeSpeciesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_tree_species")
    private Integer id;

    @NotBlank(message = "Название обязательно")
    @Size(max = 30, message = "Название не должно превышать 30 символов")
    @Column(nullable = false, length = 30)
    private String name;

    @NotBlank(message = "Характеристики обязательны")
    @Size(max = 80, message = "Характеристики не должны превышать 80 символов")
    @Column(nullable = false, length = 80)
    private String characteristics;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCharacteristics() { return characteristics; }
    public void setCharacteristics(String characteristics) { this.characteristics = characteristics; }
}