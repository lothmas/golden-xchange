//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.notifications.dao;

import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.notifications.exception.NotificationsNotFoundException;
import com.golden_xchange.domain.notifications.model.NotificationsEntity;
import com.golden_xchange.domain.utilities.AbstractDao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface NotificationsDao extends AbstractDao<NotificationsEntity, Integer> {
    List<NotificationsEntity> getUserNotifications(int userID)  throws NotificationsNotFoundException;

    void save(NotificationsEntity notificationsEntity);

    void deleteMessage(NotificationsEntity notificationsEntity);
}

