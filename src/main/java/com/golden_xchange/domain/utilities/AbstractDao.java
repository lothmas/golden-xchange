package com.golden_xchange.domain.utilities;

import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author louis
 * @param <E>
 * @param <I>
 */
public interface AbstractDao<E, I extends Serializable> {

    /**
     *
     * @param id
     * @return
     */
    E findById(I id);

    /**
     *
     * @param e
     */
    void saveOrUpdate(E e);
    void delete(E e);

    /**
     *
     * @param criterion
     * @return
     */
        List<E> findByCriteria(Criterion criterion);
}
