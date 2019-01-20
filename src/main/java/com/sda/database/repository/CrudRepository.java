package com.sda.database.repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA specific extension of Respository
 *
 * @author Isa Kalinsaz
 */
public interface CrudRepository<T> {

    /**
     * Retrieves all entities
     *
     * @see com.sda.database.repository.CrudRepository#findAll()
     */
    List<T> findAll();

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Optional<T> findById(long id);

}
