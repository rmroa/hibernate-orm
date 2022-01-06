package com.rm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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
    private List<Model> models = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "manufacturer_locale")
//    @AttributeOverride(name = "lang", column = @Column(name = "language"))
    private List<LocaleInfo> locales = new ArrayList<>();

//    @Column(name = "description")
//    private List<String> locales = new ArrayList<>(); // только на чтение

    public void addModel(Model model) {
        models.add(model);
        model.setManufacturer(this);
    }
}
