package com.globant.trainingnewgen.service;

public interface CrudService<T, ID> {

    T create(T entity);

}
