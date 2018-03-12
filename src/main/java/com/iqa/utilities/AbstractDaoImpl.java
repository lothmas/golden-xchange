package com.iqa.utilities;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Transaction;

/**
 *
 * @author louis
 * @param <E>
 * @param <I>
 */
public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E, I> {

    private Class<E> entityClass;

    /**
     *
     * @param entityClass
     */
    protected AbstractDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Autowired
    public SessionFactory sessionFactory;

    /**
     *
     * @return Current Session
     */
    public Session getCurrentSession() {

        Session activeSession = null;

//        if (null != sessionFactory.getCurrentSession()) {
//
//            activeSession = sessionFactory.getCurrentSession();//Only open a new sesssion if NONE exists!!!
//
//        } else {

        activeSession = sessionFactory.getCurrentSession();

//        }

        return activeSession;
    }

    /**
     *
     * @return
     */
    public Session openSession() {

        Session activeSession = null;

//        if (null != sessionFactory.getCurrentSession()) {
//
//            activeSession = sessionFactory.getCurrentSession();//Only open a new sesssion if NONE exists!!!
//
//        } else {

        activeSession = sessionFactory.openSession();

//        }

        return activeSession;
    }
//

    public void closeSession(Session closeSession) {

        if (null != closeSession) {

            closeSession.close();

        }

    }

    public void closeSession() {

        Session activeSession = getCurrentSession();

        if (null != activeSession) {

            activeSession.close();

        }

    }

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
//    public boolean saveObject(E e) {
//        return save(e);
//
//    }
    @Override
    public void saveOrUpdateForTransaction(E e) {
        openSession().saveOrUpdate(e);
    }

    //    @Override
    @Override
    public void delete(E e) {
        getCurrentSession().delete(e);
    }

    @Override
    public void merge(E e) {
        getCurrentSession().merge(e);
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

    /**
     *
     */
    @Override
    public void beginTransaction() {
        getCurrentSession().getTransaction().begin();
    }

    /**
     *
     */
    @Override
    public void transactionCommit() {
        getCurrentSession().getTransaction().commit();
    }

    /**
     *
     */
    @Override
    public void transactionRollBack() {
        getCurrentSession().getTransaction().rollback();
    }

    @Override
    public Transaction getTransaction() {
        return getCurrentSession().getTransaction();
    }

}
