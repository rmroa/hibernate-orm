package com.rm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "models")
@ToString(exclude = "models")
@Builder
@Entity
@Table(name = "manufacturer")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String brand;

    private String country;

    @Builder.Default
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private Set<Model> models = new HashSet<>();

    public void addModel(Model model) {
        models.add(model);
        model.setManufacturer(this);
    }
}
