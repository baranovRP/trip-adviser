package org.baranov.germes.app.persistence.repository.hibernate;

import org.baranov.germes.app.persistence.hibernate.SessionFactoryBuilder;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class HibernateCityRepository {

    private final SessionFactory sessionFactory;

    @Inject
    public HibernateCityRepository(SessionFactoryBuilder builder) {
        sessionFactory = builder.getSessionFactory();
    }
}
