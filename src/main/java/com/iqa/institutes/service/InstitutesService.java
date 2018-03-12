//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.institutes.service;



import com.iqa.institutes.exception.InstitutesNotFoundException;
import com.iqa.institutes.model.InstitutesEntity;

import java.util.List;

public interface InstitutesService {


    void saveInstitutes(InstitutesEntity var1);

    List<InstitutesEntity> findInstitutesByUserType(int var1) throws InstitutesNotFoundException;

    InstitutesEntity findInstitutesById(int id) throws InstitutesNotFoundException;

    List<InstitutesEntity> getAllInstitutes() throws InstitutesNotFoundException;
    public List<InstitutesEntity> getInstitutesByCountry(int countryId)  throws InstitutesNotFoundException;
}

