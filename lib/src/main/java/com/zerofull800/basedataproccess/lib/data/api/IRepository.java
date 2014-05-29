package com.zerofull800.basedataproccess.lib.data.api;

/**
 * Created by NestorLuis on 1/4/14.
 */
public interface IRepository<T> {
    /**
     * Get T Object by id
     *
     * @param id T object id.
     * @return A T object that match with the id parameter.
     */
    T getById(int id);
}
