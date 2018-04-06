//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.notifications.service;

import com.golden_xchange.domain.notifications.exception.NotificationsNotFoundException;
import com.golden_xchange.domain.notifications.model.NotificationsEntity;

import java.util.List;

public interface NotificationsService {
    List<NotificationsEntity> getUserNotifications(int userID)  throws NotificationsNotFoundException;

    void save(NotificationsEntity notificationsEntity);

    void deleteMessage(NotificationsEntity notificationsEntity);
}

