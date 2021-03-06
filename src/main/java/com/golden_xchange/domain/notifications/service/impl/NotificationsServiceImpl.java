//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.notifications.service.impl;

import com.golden_xchange.domain.mainlist.dao.MainListDao;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.notifications.dao.NotificationsDao;
import com.golden_xchange.domain.notifications.exception.NotificationsNotFoundException;
import com.golden_xchange.domain.notifications.model.NotificationsEntity;
import com.golden_xchange.domain.notifications.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("NotificationsService")
@Transactional(
    readOnly = true
)
public class NotificationsServiceImpl implements NotificationsService {
    @Autowired
    private NotificationsDao notificationsDao;


    @Override
    public List<NotificationsEntity> getUserNotifications(String userId) throws NotificationsNotFoundException{
        return this.notificationsDao.getUserNotifications(userId);
    }


    @Override
    @Transactional(
            readOnly = false
    )
    public void save(NotificationsEntity notificationsEntity) {
        this.notificationsDao.save(notificationsEntity);
    }

    @Override
    @Transactional(
            readOnly = false
    )
    public void deleteMessage(NotificationsEntity notificationsEntity) {
            this.notificationsDao.deleteMessage(notificationsEntity);
    }

    @Override
    public NotificationsEntity getNotificationByRefAndUser(String userName, String mainRef) throws NotificationsNotFoundException {
        return this.notificationsDao.getNotificationByRefAndUser(userName,mainRef);
    }


}

