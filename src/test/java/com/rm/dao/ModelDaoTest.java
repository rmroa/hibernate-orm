package com.rm.dao;

import com.querydsl.core.Tuple;
import com.rm.entity.Discount;
import com.rm.entity.DiscountInfo;
import com.rm.entity.Model;
import com.rm.util.HibernateTestUtil;
import com.rm.util.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class ModelDaoTest {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final ModelDao modelDao = ModelDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void finAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Model> models = modelDao.findAllModels(session);
        assertThat(models).hasSize(3);

        List<String> modelNames = models.stream().map(Model::getModel).collect(toList());
        assertThat(modelNames).containsExactlyInAnyOrder("x6", "a6", "amgGt");

        session.getTransaction().commit();
    }

    @Test
    public void findAllByManufacturer() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Model> models = modelDao.findAllModelsByManufacturer(session, "bmw");
        assertThat(models).hasSize(1);
        assertThat(models.get(0).getModel()).isEqualTo("x6");

        session.getTransaction().commit();
    }

    @Test
    public void findLimitedModelsOrderedByProductionYear() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        int limit = 2;
        List<Model> models = modelDao.findLimitedModelsOrderedByProductionYear(session, limit);
        assertThat(models).hasSize(limit);

        List<String> modelName = models.stream().map(Model::getModel).collect(toList());
        assertThat(modelName).contains("a6", "amgGt");

        session.getTransaction().commit();
    }

    @Test
    public void findByPriceAndOrderedByPrice() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        BigDecimal price = new BigDecimal("60.000");
        List<Model> models = modelDao.findByPriceAndOrderedByPrice(session, price);
        assertThat(models).hasSize(2);

        List<String> modelsName = models.stream().map(Model::getModel).collect(toList());
        assertThat(modelsName).containsSequence("amgGt", "x6");

        session.getTransaction().commit();
    }

    @Test
    public void findAllDiscountsByManufacturer() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        String brand = "bmw";
        long amountOfDiscount = 5;
        List<Discount> discounts = modelDao.findAllDiscountsByManufacturer(session, brand);
        assertThat(discounts).hasSize(1);

        List<Long> discountList = discounts.stream().map(Discount::getDiscountInfo).map(DiscountInfo::getAmountOfDiscount).collect(toList());
        assertThat(discountList).containsOnly(amountOfDiscount);

        session.getTransaction().commit();
    }

    @Test
    public void findModelsByVehicleTypeAndManufacturer() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        String brand = "bmw";
        String vehicleType = "crossover";
        List<Model> models = modelDao.findModelsByVehicleTypeAndManufacturer(session, vehicleType, brand);
        assertThat(models).hasSize(1);

        List<String> modelList = models.stream().map(Model::getModel).collect(toList());
        assertThat(modelList).containsOnly("x6");

        session.getTransaction().commit();
    }

    @Test
    public void findManufacturerAndModelsWithAvgModelCostAndDiscount() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        double priceFirst = 70.700;
        double priceSecond = 50.100;
        double priceThird = 66.300;

        double discountFirst = 5;
        double discountSecond = 15;
        double discountThird = 25;

        List<Tuple> results = modelDao.findManufacturerAndModelsWithAvgModelCostAndDiscount(session);
        assertThat(results).hasSize(3);

        List<String> manufacturers = results.stream().map(a -> a.get(0, String.class)).collect(toList());
        assertThat(manufacturers).containsSequence("audi", "bmw", "mercedes");

        List<Double> prices = results.stream().map(a -> a.get(1, Double.class)).collect(toList());
        assertThat(prices).contains(priceFirst, priceSecond, priceThird);

        List<Double> discounts = results.stream().map(a -> a.get(2, Double.class)).collect(toList());
        assertThat(discounts).contains(discountFirst, discountSecond, discountThird);

        session.getTransaction().commit();
    }

    @Test
    public void isItPossible() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        double priceFirst = 70.700;
        double priceThird = 66.300;

        List<Tuple> results = modelDao.isItPossible(session);
        assertThat(results).hasSize(2);

        List<String> models = results.stream().map(e -> e.get(0, Model.class).getModel()).collect(toList());
        assertThat(models).containsSequence("amgGt", "x6");

        List<Double> prices = results.stream().map(a -> a.get(1, Double.class)).collect(toList());
        assertThat(prices).contains(priceFirst, priceThird);

        session.getTransaction().commit();
    }
}
