package com.rm.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.rm.dto.ModelFilter;
import com.rm.entity.Discount;
import com.rm.entity.Model;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

import static com.rm.entity.QDiscount.discount;
import static com.rm.entity.QManufacturer.manufacturer;
import static com.rm.entity.QModel.model1;
import static com.rm.entity.QVehicleType.vehicleType;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ModelDao {

    private static final ModelDao INSTANCE = new ModelDao();

    public static ModelDao getInstance() {
        return INSTANCE;
    }

    /**
     * Возвращает список всех моделей
     */
    public List<Model> findAllModels(Session session) {
//        return session.createQuery("select m from Model m", Model.class)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<Model> criteria = cb.createQuery(Model.class);
//        Root<Model> model = criteria.from(Model.class);
//
//        criteria.select(model);
//
//        return session.createQuery(criteria)
//                .list();

        return new JPAQuery<Model>(session)
                .select(model1)
                .from(model1)
                .fetch();
    }

    /**
     * Возвращает список всех моделей по соответствующему производителю
     */
    public List<Model> findAllModelsByManufacturer(Session session, String brand) {
//        return session.createQuery("" +
//                        "select m from Manufacturer mr " +
//                        "join mr.models m " +
//                        "where mr.brand = :brand", Model.class)
//                .setParameter("brand", brand)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<Model> criteria = cb.createQuery(Model.class);
//        Root<Manufacturer> manufacturer = criteria.from(Manufacturer.class);
//        ListJoin<Manufacturer, Model> models = manufacturer.join(Manufacturer_.models);
//
//        criteria.select(models).where(
//                cb.equal(manufacturer.get(Manufacturer_.brand), brand)
//        );
//
//        return session.createQuery(criteria)
//                .list();

        return new JPAQuery<Model>(session)
                .select(model1)
                .from(manufacturer)
                .join(manufacturer.models, model1)
                .where(manufacturer.brand.eq(brand))
                .fetch();
    }

    /**
     * Возвращает первые {limit} моделей, упорядоченных по году производства (в порядке возрастания)
     */
    public List<Model> findLimitedModelsOrderedByProductionYear(Session session, int limit) {
//        return session.createQuery("" +
//                        "select m from Model m " +
//                        "order by m.productionYear", Model.class)
//                .setMaxResults(limit)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<Model> criteria = cb.createQuery(Model.class);
//        Root<Model> model = criteria.from(Model.class);
//        criteria.select(model).orderBy(cb.asc(model.get(Model_.productionYear)));
//
//        return session.createQuery(criteria)
//                .setMaxResults(limit)
//                .list();

        return new JPAQuery<Model>(session)
                .select(model1)
                .from(model1)
                .orderBy(model1.productionYear.asc())
                .limit(2)
                .fetch();
    }

    /**
     * Возвращает список моделей, выше определенной стоимости (в порядке возрастания)
     */
    public List<Model> findByPriceAndOrderedByPrice(Session session, BigDecimal modelPrice) {
//        return session.createQuery("" +
//                        "select m from Model m " +
//                        "where m.price > :modelPrice " +
//                        "order by m.price", Model.class)
//                .setParameter("modelPrice", modelPrice)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<Model> criteria = cb.createQuery(Model.class);
//        Root<Model> model = criteria.from(Model.class);
//        criteria.select(model)
//                .where(
//                        cb.greaterThan(model.get(Model_.price), modelPrice)
//                )
//                .orderBy(cb.asc(model.get(Model_.price)));
//
//        return session.createQuery(criteria)
//                .list();

        return new JPAQuery<Model>(session)
                .select(model1)
                .from(model1)
                .where(model1.price.gt(modelPrice))
                .orderBy(model1.price.asc())
                .fetch();
    }

    /**
     * Возвращает список скидок по производителю, принадлежащих соответствующим моделям
     */
    public List<Discount> findAllDiscountsByManufacturer(Session session, String brand) {
//        return session.createQuery("" +
//                        "select d from Manufacturer mr " +
//                        "join mr.models m " +
//                        "join m.discount d " +
//                        "where mr.brand = :brand", Discount.class)
//                .setParameter("brand", brand)
//                .list();
//
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<Discount> criteria = cb.createQuery(Discount.class);
//        Root<Manufacturer> manufacturer = criteria.from(Manufacturer.class);
//        ListJoin<Manufacturer, Model> models = manufacturer.join(Manufacturer_.models);
//        Join<Model, Discount> discount = models.join(Model_.discount);
//
//        criteria.select(discount).where(
//                cb.equal(manufacturer.get(Manufacturer_.brand), brand)
//        );
//
//        return session.createQuery(criteria)
//                .list();

        return new JPAQuery<Discount>(session)
                .select(discount)
                .from(discount)
                .join(discount.models, model1)
                .join(model1.manufacturer, manufacturer)
                .where(manufacturer.brand.eq(brand))
                .fetch();
    }

    /**
     * Возвращает список моделей, принадлежащх соответствующему типу транспортного средства и производителю
     */
    public List<Model> findModelsByVehicleTypeAndManufacturer(Session session, ModelFilter modelFilter) {
//        return session.createQuery("" +
//                        "select m from VehicleType v " +
//                        "join v.models m " +
//                        "join m.manufacturer mr " +
//                        "where v.type = :vehicleType and mr.brand = :brand ", Model.class)
//                .setParameter("vehicleType", vehicleTypeName)
//                .setParameter("brand", brand)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<Model> criteria = cb.createQuery(Model.class);
//        Root<VehicleType> vehicleTypeRoot = criteria.from(VehicleType.class);
//        ListJoin<VehicleType, Model> models = vehicleTypeRoot.join(VehicleType_.models);
//        Join<Model, Manufacturer> manufacturer = models.join(Model_.manufacturer);
//
//        List<Predicate> predicates = new ArrayList<>();
//        if (vehicleType != null) {
//            predicates.add(cb.equal(vehicleTypeRoot.get(VehicleType_.type), vehicleType));
//        }
//        if (brand != null) {
//            predicates.add(cb.equal(vehicleTypeRoot.get(VehicleType_.type), vehicleType));
//        }
//
//        criteria.select(models).where(
//                predicates.toArray(Predicate[]::new)
//        );
//
//        return session.createQuery(criteria)
//                .list();

        Predicate predicate = QPredicate.builder()
                .add(modelFilter.getVehicleType(), vehicleType.type::eq)
                .add(modelFilter.getBrand(), manufacturer.brand::eq)
                .buildAnd();

        return new JPAQuery<Model>(session)
                .select(model1)
                .from(model1)
                .join(model1.vehicleType, vehicleType).fetchJoin()
                .join(model1.manufacturer, manufacturer).fetchJoin()
                .where(predicate)
                .fetch();
    }

    /**
     * Возвращает для каждого производителя: название, со средней стоимостью модели и размером скидки. Производители упорядочены по названию.
     */
    public List<Tuple> findManufacturerAndModelsWithAvgModelCostAndDiscount(Session session) {
//        return session.createQuery("" +
//                        "select mr.brand, avg(m.price), avg(d.discountInfo.amountOfDiscount) from Manufacturer mr " +
//                        "join mr.models m " +
//                        "join m.discount d " +
//                        "group by mr.brand " +
//                        "order by mr.brand", Object[].class)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<Object[]> criteria = cb.createQuery(Object[].class);
//        Root<Manufacturer> manufacturer = criteria.from(Manufacturer.class);
//        ListJoin<Manufacturer, Model> models = manufacturer.join(Manufacturer_.models);
//        Join<Model, Discount> discount = models.join(Model_.discount);
//
//        criteria.multiselect(
//                        manufacturer.get(Manufacturer_.brand),
//                        cb.avg(models.get(Model_.price)),
//                        cb.avg(discount.get(Discount_.discountInfo).get(DiscountInfo_.amountOfDiscount))
//                )
//                .groupBy(manufacturer.get(Manufacturer_.brand))
//                .orderBy(cb.asc(manufacturer.get(Manufacturer_.brand)));
//
//        return session.createQuery(criteria)
//                .list();

        return new JPAQuery<Tuple>(session)
                .select(manufacturer.brand,
                        model1.price.avg(),
                        discount.discountInfo.amountOfDiscount.avg())
                .from(manufacturer)
                .join(manufacturer.models, model1)
                .join(model1.discount, discount)
                .groupBy(manufacturer.brand)
                .orderBy(manufacturer.brand.asc())
                .fetch();
    }

    /**
     * Возвращает список: модель (объект Model), средняя стоимость модели, но только для тех моделей, чья средняя стоимость
     * больше средней стоимости всех моделей
     * Упорядочить по названию модели
     */
    public List<Tuple> isItPossible(Session session) {
//        return session.createQuery("" +
//                        "select m, avg(m.price) from Model m " +
//                        "group by m " +
//                        "having avg(m.price) > (select avg(m2.price) from Model m2) " +
//                        "order by m.model", Object[].class)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<Tuple> criteria = cb.createQuery(Tuple.class);
//        Root<Model> model = criteria.from(Model.class);
//
//        Subquery<Double> subQuery = criteria.subquery(Double.class);
//        Root<Model> modelSubQuery = subQuery.from(Model.class);
//
//        criteria.select(
//                        cb.tuple(
//                                model,
//                                cb.avg(model.get(Model_.price))
//                        )
//                )
//                .groupBy(model.get(Model_.id))
//                .having(cb.gt(
//                        cb.avg(model.get(Model_.price)),
//                        subQuery.select(cb.avg(modelSubQuery.get(Model_.price)))
//                ))
//                .orderBy(cb.asc(model.get(Model_.model)));
//
//        return session.createQuery(criteria)
//                .list();

        return new JPAQuery<Tuple>(session)
                .select(model1, model1.price.avg())
                .from(model1)
                .groupBy(model1)
                .having(model1.price.avg().gt(
                        new JPAQuery<Double>(session)
                                .select(model1.price.avg())
                                .from(model1)
                ))
                .orderBy(model1.model.asc())
                .fetch();
    }
}
