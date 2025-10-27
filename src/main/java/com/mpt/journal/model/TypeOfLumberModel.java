package com.mpt.journal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_of_lumber")
public class TypeOfLumberModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_type_of_lumber")
    private Long id;

    @NotBlank(message = "Название обязательно")
    @Size(max = 30, message = "Название не должно превышать 30 символов")
    @Column(nullable = false, length = 30)
    private String name;

    @NotBlank(message = "Область применения обязательна")
    @Size(max = 80, message = "Область применения не должна превышать 80 символов")
    @Column(name = "area_of_speciesion", nullable = false, length = 80)
    private String areaOfSpeciesion;

    @OneToMany(mappedBy = "typeOfLumber", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LumberModel> lumber = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAreaOfSpeciesion() { return areaOfSpeciesion; }
    public void setAreaOfSpeciesion(String areaOfSpeciesion) { this.areaOfSpeciesion = areaOfSpeciesion; }
    public Set<LumberModel> getLumber() { return lumber; }
    public void setLumber(Set<LumberModel> lumber) { this.lumber = lumber; }
}