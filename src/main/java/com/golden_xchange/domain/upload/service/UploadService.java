//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.upload.service;

import com.golden_xchange.domain.upload.PaymentProofEntity;
import com.golden_xchange.domain.upload.exception.UploadNotFoundException;

import java.util.List;

public interface UploadService {
    List<PaymentProofEntity> getUserNotifications(String userID)  throws UploadNotFoundException;

    void save(PaymentProofEntity notificationsEntity);

    void deleteMessage(PaymentProofEntity notificationsEntity);

    PaymentProofEntity getNotificationByRefAndUser(String userName, String mainRef)  throws UploadNotFoundException;
}

