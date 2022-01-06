package com.rm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Embedded
    @AttributeOverride(name = "amountOfDiscount", column = @Column(name = "amount_of_discount"))
    @AttributeOverride(name = "startDate", column = @Column(name = "start_date"))
    @AttributeOverride(name = "endDate", column = @Column(name = "end_date"))
    private DiscountInfo discountInfo;

    @Builder.Default
    @OneToMany(mappedBy = "discount")
    private List<Model> models = new ArrayList<>();

    public void addModel(Model model) {
        models.add(model);
        model.setDiscount(this);
    }
}
