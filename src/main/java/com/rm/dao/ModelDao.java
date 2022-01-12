package com.rm.dao;

import com.rm.entity.Discount;
import com.rm.entity.Model;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

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
        return session.createQuery("select m from Model m", Model.class)
                .list();
    }

    /**
     * Возвращает список всех моделей по соответствующему производителю
     */
    public List<Model> findAllModelsByManufacturer(Session session, String brand) {
        return session.createQuery("" +
                        "select m from Manufacturer mr " +
                        "join mr.models m " +
                        "where mr.brand = :brand", Model.class)
                .setParameter("brand", brand)
                .list();
    }

    /**
     * Возвращает первые {limit} моделей, упорядоченных по году производства (в порядке возрастания)
     */
    public List<Model> findLimitedModelsOrderedByProductionYear(Session session, int limit) {
        return session.createQuery("" +
                        "select m from Model m " +
                        "order by m.productionYear", Model.class)
                .setMaxResults(limit)
                .list();
    }

    /**
     * Возвращает список моделей, выше определенной стоимости (в порядке возрастания)
     */
    public List<Model> findByPriceAndOrderedByPrice(Session session, BigDecimal modelPrice) {
        return session.createQuery("" +
                        "select m from Model m " +
                        "where m.price > :modelPrice " +
                        "order by m.price", Model.class)
                .setParameter("modelPrice", modelPrice)
                .list();
    }

    /**
     * Возвращает список скидок по производителю, принадлежащих соответствующим моделям
     */
    public List<Discount> findAllDiscountsByManufacturer(Session session, String brand) {
        return session.createQuery("" +
                        "select d from Manufacturer mr " +
                        "join mr.models m " +
                        "join m.discount d " +
                        "where mr.brand = :brand", Discount.class)
                .setParameter("brand", brand)
                .list();
    }

    /**
     * Возвращает список моделей, принадлежащх соответствующему типу транспортного средства и производителю
     */
    public List<Model> findModelsByVehicleTypeAndManufacturer(Session session, String vehicleType, String brand) {
        return session.createQuery("" +
                        "select m from VehicleType v " +
                        "join v.models m " +
                        "join m.manufacturer mr " +
                        "where v.type = :vehicleType and mr.brand = :brand ", Model.class)
                .setParameter("vehicleType", vehicleType)
                .setParameter("brand", brand)
                .list();
    }

    /**
     * Возвращает для каждого производителя: название, со средней стоимостью модели и размером скидки. Производители упорядочены по названию.
     */
    public List<Object[]> findManufacturerAndModelsWithAvgModelCostAndDiscount(Session session) {
        return session.createQuery("" +
                        "select mr.brand, avg(m.price), avg(d.discountInfo.amountOfDiscount) from Manufacturer mr " +
                        "join mr.models m " +
                        "join m.discount d " +
                        "group by mr.brand " +
                        "order by mr.brand", Object[].class)
                .list();
    }

    /**
     * Возвращает список: модель (объект Model), средняя стоимость модели, но только для тех моделей, чья средняя стоимость
     * больше средней стоимости всех моделей
     * Упорядочить по названию модели
     */
    public List<Object[]> isItPossible(Session session) {
        return session.createQuery("" +
                        "select m, avg(m.price) from Model m " +
                        "group by m " +
                        "having avg(m.price) > (select avg(m2.price) from Model m2) " +
                        "order by m.model", Object[].class)
                .list();
    }

}
