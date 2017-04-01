package com.colptha.services;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Colptha on 4/1/17.
 */
public interface CRUDService<Form, QueryParam> {
    Map<QueryParam, Form> listAll();

    Form findOne(QueryParam query) throws NoSuchElementException;

    Form saveOrUpdate(Form form);

    Form delete(QueryParam query);
}
