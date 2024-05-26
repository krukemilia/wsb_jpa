package com.capgemini.wsb.service.impl;

import com.capgemini.wsb.dto.PatientTO;
import com.capgemini.wsb.mapper.PatientMapper;
import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PatientServiceImpl implements PatientService{
  private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao)
    {
        this.patientDao = patientDao;
    }

    @Override
    public PatientTO findById(final Long id)
    {
        final PatientEntity entity = patientDao.findOne(id);
        final PatientTO patientTO = PatientMapper.mapToTO(entity);
        if(patientTO != null)
        {
            patientTO.setVisits(patientDao.getPatientVisits(id));
        }
        return patientTO;
    }

    @Override
    public void deleteById(final Long id)
    {
        patientDao.delete(id);
    }
}
