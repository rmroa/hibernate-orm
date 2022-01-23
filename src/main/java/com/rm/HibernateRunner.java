package com.rm;

import com.rm.entity.Model;
import com.rm.entity.Order;
import com.rm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            RootGraph<Model> modelGraph = session.createEntityGraph(Model.class);
            modelGraph.addAttributeNodes("manufacturer", "orders");
            SubGraph<Order> ordersSubGraph = modelGraph.addSubgraph("orders", Order.class);
            ordersSubGraph.addAttributeNodes("user");

            Map<String, Object> properties = Map.of(
                    GraphSemantic.LOAD.getJpaHintName(), modelGraph
//                    GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withManufacturerAndUser") // для аннотаций
            );
            Model model = session.find(Model.class, 1L, properties);
            System.out.println(model.getManufacturer().getBrand());
            System.out.println(model.getOrders().size());

            List<Model> models = session.createQuery("" +
                            "select m from Model m", Model.class)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), modelGraph)
//                    .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withManufacturerAndUser")) // для аннотаций
                    .list();

            models.forEach(it -> System.out.println(it.getManufacturer().getBrand()));
            models.forEach(it -> System.out.println(it.getOrders().size()));

            session.getTransaction().commit();
        }
    }
}
