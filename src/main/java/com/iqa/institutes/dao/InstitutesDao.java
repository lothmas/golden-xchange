//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.institutes.dao;



import com.iqa.institutes.exception.InstitutesNotFoundException;
import com.iqa.institutes.model.InstitutesEntity;
import com.iqa.utilities.AbstractDao;

import java.util.List;

public interface InstitutesDao extends AbstractDao<InstitutesEntity, Integer> {

    void saveInstitutes(InstitutesEntity var1);

    List<InstitutesEntity> findInstitutesByUserType(int var1) throws InstitutesNotFoundException;
    InstitutesEntity findInstitutesById(int id) throws InstitutesNotFoundException;
    List<InstitutesEntity> getAllInstitutes() throws InstitutesNotFoundException;
    public List<InstitutesEntity> getInstitutesByCountry(int countryId) throws InstitutesNotFoundException;;
}

