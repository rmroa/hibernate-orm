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
@Table(name = "vehicle_type")
public class VehicleType implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String type;

    @Builder.Default
    @OneToMany(mappedBy = "vehicleType", cascade = CascadeType.ALL)
    private List<Model> models = new ArrayList<>();

    public void addModel(Model model) {
        models.add(model);
        model.setVehicleType(this);
    }
}
