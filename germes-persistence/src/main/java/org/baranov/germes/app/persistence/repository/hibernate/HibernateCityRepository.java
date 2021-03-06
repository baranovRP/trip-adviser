package org.baranov.germes.app.persistence.repository.hibernate;

import org.baranov.germes.app.model.entity.geography.City;
import org.baranov.germes.app.persistence.hibernate.SessionFactoryBuilder;
import org.baranov.germes.app.persistence.repository.CityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * Hibernate implementation of {@link CityRepository}
 */
public class HibernateCityRepository implements CityRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateCityRepository.class);
    private final SessionFactory sessionFactory;

    @Inject
    public HibernateCityRepository(SessionFactoryBuilder builder) {
        sessionFactory = builder.getSessionFactory();
    }

    @Override
    public void save(City city) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(city);
            tx.commit();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public City findById(int cityId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(City.class, cityId);
        }
    }

    @Override
    public void delete(int cityId) {
        try (Session session = sessionFactory.openSession()) {
            City city = session.get(City.class, cityId);
            if (city != null) {
                session.delete(city);
            }
        }
    }

    @Override
    public List<City> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createCriteria(City.class).list();
        }
    }
}
