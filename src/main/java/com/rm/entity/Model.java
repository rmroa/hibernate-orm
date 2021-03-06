package com.rm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@NamedEntityGraph(name = "withManufacturer", attributeNodes = {
        @NamedAttributeNode("manufacturer")
}
)
@NamedEntityGraph(name = "withManufacturerAndUser", attributeNodes = {
        @NamedAttributeNode("manufacturer"),
        @NamedAttributeNode(value = "orders", subgraph = "orders"),
},
        subgraphs = {
                @NamedSubgraph(name = "orders", attributeNodes = @NamedAttributeNode("user"))
        }
)
@FetchProfile(name = "withManufacturerAndOrders", fetchOverrides = {
        @FetchProfile.FetchOverride(
                entity = Model.class, association = "manufacturer", mode = FetchMode.JOIN
        ),
        @FetchProfile.FetchOverride(
                entity = Model.class, association = "orders", mode = FetchMode.JOIN
        )
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "orders", callSuper = false)
@ToString(exclude = "orders")
@Builder
@Entity
@Table(name = "models")
@Audited
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Model extends AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String model;

    @NotAudited
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(name = "production_year")
    private LocalDate productionYear;

    @NotAudited
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "vehicle_type_id")
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    @Column(name = "drive_unit")
    private DriveUnit driveUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine_type")
    private EngineType engineType;

    @Column(name = "current_mileage")
    private Long currentMileage;

    private BigDecimal price;

    @NotAudited
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.setModel(this);
    }
}
