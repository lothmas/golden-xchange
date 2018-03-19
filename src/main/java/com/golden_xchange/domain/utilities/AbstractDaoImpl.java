package com.golden_xchange.domain.utilities;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author louis
 * @param <E>
 * @param <I>
 */
public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E,I> {

    @Autowired
    public SessionFactory sessionFactory;
    private Class<E> entityClass;

    /**
     *
     * @param entityClass
     */
    protected AbstractDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     *
     * @return Current Session
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    /**
     *
     * @return
     */
    public Session openSessoin(){
        return sessionFactory.openSession();
    } 
//    
//    public void  closeSession(Session closeSession){
//        closeSession.close();
//    }

    /**
     *
     * @param id
     * @return An object of the calling class
     */
    @Override
    public E findById(I id) {
        return (E) getCurrentSession().get(entityClass, id);
    }

    /**
     *
     * @param e
     */
    @Override
    public void saveOrUpdate(E e) {
        getCurrentSession().saveOrUpdate(e);
        
    }

//    @Override
    public void delete(E e) {
        getCurrentSession().delete(e);
    }

    /**
     *
     * @param criterion
     * @return A list of the concerned class
     */
    
    @Override
    public List<E> findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return criteria.list();
    }

}
