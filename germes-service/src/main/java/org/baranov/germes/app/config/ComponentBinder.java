package org.baranov.germes.app.config;

import org.baranov.germes.app.persistence.repository.CityRepository;
import org.baranov.germes.app.persistence.repository.inmemory.InMemoryCityRepository;
import org.baranov.germes.app.service.GeographicService;
import org.baranov.germes.app.service.impl.GeographicServiceImpl;
import org.baranov.germes.app.service.transform.Transformer;
import org.baranov.germes.app.service.transform.impl.SimpleDTOTransformer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Binds bean implementations and implemented interfaces
 */
public class ComponentBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(InMemoryCityRepository.class).to(CityRepository.class).in(Singleton.class);
        bind(SimpleDTOTransformer.class).to(Transformer.class).in(Singleton.class);
        bind(GeographicServiceImpl.class).to(GeographicService.class).in(Singleton.class);
    }
}