//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.institutes.service.impl;


import com.iqa.institutes.exception.InstitutesNotFoundException;
import com.iqa.institutes.service.InstitutesService;
import com.iqa.institutes.model.InstitutesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("InstitutesService")
@Transactional(
    readOnly = true
)
public class InstitutesServiceImpl implements InstitutesService {

    @Autowired
    private com.iqa.institutes.dao.InstitutesDao InstitutesDao;


    @Override
    public void saveInstitutes(InstitutesEntity var1) {

    }

    @Override
    public List<InstitutesEntity> findInstitutesByUserType(int username) throws InstitutesNotFoundException {
        return this.InstitutesDao.findInstitutesByUserType(username);
    }

    @Override
    public InstitutesEntity findInstitutesById(int id) throws InstitutesNotFoundException {
        return this.InstitutesDao.findInstitutesById(id);
    }

    @Override
    public List<InstitutesEntity> getAllInstitutes() throws InstitutesNotFoundException {
        return this.InstitutesDao.getAllInstitutes();
    }

    @Override
    public List<InstitutesEntity> getInstitutesByCountry(int countryId) throws InstitutesNotFoundException {
        return this.InstitutesDao.getInstitutesByCountry(countryId);
    }


}

