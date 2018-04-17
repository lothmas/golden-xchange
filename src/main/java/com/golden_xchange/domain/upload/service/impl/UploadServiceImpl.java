//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.upload.service.impl;

import com.golden_xchange.domain.upload.PaymentProofEntity;
import com.golden_xchange.domain.upload.dao.UploadDao;
import com.golden_xchange.domain.upload.exception.UploadNotFoundException;
import com.golden_xchange.domain.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UploadService")
@Transactional(
    readOnly = true
)
public class UploadServiceImpl implements UploadService {
    @Autowired
    private UploadDao notificationsDao;


    @Override
    public List<PaymentProofEntity> getUserNotifications(String userId) throws UploadNotFoundException {
        return this.notificationsDao.getUserNotifications(userId);
    }


    @Override
    @Transactional(
            readOnly = false
    )
    public void save(PaymentProofEntity notificationsEntity) {
        this.notificationsDao.save(notificationsEntity);
    }

    @Override
    @Transactional(
            readOnly = false
    )
    public void deleteMessage(PaymentProofEntity notificationsEntity) {
            this.notificationsDao.deleteMessage(notificationsEntity);
    }

    @Override
    public PaymentProofEntity getNotificationByRefAndUser(String userName, String mainRef) throws UploadNotFoundException {
        return this.notificationsDao.getNotificationByRefAndUser(userName,mainRef);
    }


}

