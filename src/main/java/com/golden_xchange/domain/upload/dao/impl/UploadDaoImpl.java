//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.upload.dao.impl;

import com.golden_xchange.domain.upload.PaymentProofEntity;
import com.golden_xchange.domain.upload.dao.UploadDao;
import com.golden_xchange.domain.upload.exception.UploadNotFoundException;
import com.golden_xchange.domain.utilities.AbstractDaoImpl;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class UploadDaoImpl extends AbstractDaoImpl<PaymentProofEntity, Integer> implements UploadDao {
    Logger schedulerLog = Logger.getLogger(this.getClass().getName());
    private static EntityManagerFactory emFactory;
    private static EntityManager em;

    LocalDateTime endDate = LocalDateTime.now();
    Date toDate = new Date();
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp sqlDate;
    private final Logger log;
    Logger MainListEntityDaoImplLogger;

    protected UploadDaoImpl() {
        super(PaymentProofEntity.class);
        this.sqlDate = new Timestamp(this.toDate.getTime());
        this.log = Logger.getLogger(UploadDaoImpl.class);
    }

    public void save(PaymentProofEntity paymentProofEntity) {
        this.saveOrUpdate(paymentProofEntity);

    }

    @Override
    public void deleteMessage(PaymentProofEntity notificationsEntity) {
        delete(notificationsEntity);
    }

    @Override
    public PaymentProofEntity getNotificationByRefAndUser(String userName, String mainRef) throws UploadNotFoundException {
        List<PaymentProofEntity> returnMainList = this.getCurrentSession().createCriteria(PaymentProofEntity.class)
                .add(Restrictions.eq("userName", userName))
                .add(Restrictions.eq("mainListRef", mainRef))
                .list();
        if (returnMainList.size() == 0 ) {
            throw new UploadNotFoundException("No Notifications found:");
        }
        else {
            return returnMainList.get(0);
        }

    }


    @Override
    public List<PaymentProofEntity> getUserNotifications(String username) throws UploadNotFoundException {
        List<PaymentProofEntity> returnMainList = this.getCurrentSession().createCriteria(PaymentProofEntity.class)
                .add(Restrictions.eq("userName", username))
                .add(Restrictions.eq("status", 0))
                .list();
        if (returnMainList.size() == 0 ) {
            throw new UploadNotFoundException("No Notifications found:");
        }
        else {
            return returnMainList;
        }
    }


}

