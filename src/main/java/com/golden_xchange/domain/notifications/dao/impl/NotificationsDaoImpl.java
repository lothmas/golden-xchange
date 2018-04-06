//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.notifications.dao.impl;

import com.golden_xchange.domain.notifications.dao.NotificationsDao;
import com.golden_xchange.domain.notifications.exception.NotificationsNotFoundException;
import com.golden_xchange.domain.notifications.model.NotificationsEntity;
import com.golden_xchange.domain.utilities.AbstractDaoImpl;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class NotificationsDaoImpl extends AbstractDaoImpl<NotificationsEntity, Integer> implements NotificationsDao {
    Logger schedulerLog = Logger.getLogger(this.getClass().getName());

    LocalDateTime endDate = LocalDateTime.now();
    Date toDate = new Date();
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp sqlDate;
    private final Logger log;
    Logger MainListEntityDaoImplLogger;

    protected NotificationsDaoImpl() {
        super(NotificationsEntity.class);
        this.sqlDate = new Timestamp(this.toDate.getTime());
        this.log = Logger.getLogger(NotificationsDaoImpl.class);
    }

    public void save(NotificationsEntity notificationsListEntity) {
        this.saveOrUpdate(notificationsListEntity);

    }

    @Override
    public void deleteMessage(NotificationsEntity notificationsEntity) {
        this.delete(notificationsEntity);
    }


    @Override
    public List<NotificationsEntity> getUserNotifications(int userId) throws NotificationsNotFoundException {
        List<NotificationsEntity> returnMainList = this.getCurrentSession().createCriteria(NotificationsEntity.class)
                .add(Restrictions.eq("userId", userId))
                .list();
        if (returnMainList.size() == 0 ) {
            throw new NotificationsNotFoundException("No Notifications found:");
        }
        else {
            return returnMainList;
        }
    }
}

