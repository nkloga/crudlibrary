package com.sda.database.repository;

import java.util.List;

/**
 * JPA specific extension of Respository
 *
 * @author Nikolai Kloga
 *
 * Following desing patterns are used: abstract factory, builder
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
    T findById(long id);

    /**
     * Count amount of records
     * @return number of records
     */
    long count();

    int delete(int id);

    int update(T updateEntity);

    int create(T newEntity);

}
