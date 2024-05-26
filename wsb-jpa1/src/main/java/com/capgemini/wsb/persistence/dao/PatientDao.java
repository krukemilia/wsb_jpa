package com.capgemini.wsb.persistence.dao;

import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;

import java.util.Collection;

public interface PatientDao extends Dao<PatientEntity, Long>
{
    public Collection<PatientEntity> getPatientsByLastName(final String lastName);

    public Collection<VisitEntity> getPatientVisits(final long patientId);

    public Collection<PatientEntity> getPatientsWithMinNumberOfVisits(final Short numberOfVisits);

    public Collection<PatientEntity> getPatientsWithMinVisitCostDiscount(final Short costDiscount);
}