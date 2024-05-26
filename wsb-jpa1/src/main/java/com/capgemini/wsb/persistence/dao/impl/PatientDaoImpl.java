package com.capgemini.wsb.persistence.dao.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.dao.PatientDao;


@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao
{
  @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<PatientEntity> getPatientsByLastName(final String lastName)
    {
        @SuppressWarnings("unchecked")
        Collection<PatientEntity> patients =
                entityManager
                        .createQuery("SELECT p from PatientEntity p WHERE p.lastName = :lastName")
                        .setParameter("lastName", lastName)
                        .getResultList();
        return patients;
    }

    @Override
    public Collection<VisitEntity> getPatientVisits(final long patientId)
    {
        PatientEntity patient = findOne(patientId);

        @SuppressWarnings("unchecked")
        Collection<VisitEntity> visits =
                entityManager
                .createQuery("SELECT v from VisitEntity v WHERE v.patient = :patient")
                .setParameter("patient", patient)
                .getResultList();

        return visits;
    }

    @Override
    public Collection<PatientEntity> getPatientsWithMinNumberOfVisits(final Short numberOfVisits)
    {

        @SuppressWarnings("unchecked")
        Collection<VisitEntity> visits =
                entityManager
                        .createQuery("SELECT v from VisitEntity v")
                        .getResultList();

        @SuppressWarnings("unchecked")
        Collection<PatientEntity> patients =
                entityManager
                        .createQuery("SELECT p from PatientEntity p")
                        .getResultList();

        Collection<PatientEntity> result = new ArrayList<>();

        for (PatientEntity patient : patients)
        {
            long count = visits.stream()
                    .filter(v -> patient.getId().equals(v.getPatientId()))
                    .count();

            if(count >= numberOfVisits)
            {
                result.add(patient);
            }
        }

        return result;
    }

    @Override
    public Collection<PatientEntity> getPatientsWithMinVisitCostDiscount(final Short visitCostDiscount)
    {
        Collection<PatientEntity> patients =
                entityManager
                        .createQuery("SELECT p from PatientEntity p WHERE p.visitCostDiscount >= :visitCostDiscount", PatientEntity.class)
                        .setParameter("visitCostDiscount", visitCostDiscount)
                        .getResultList();
        return patients;
    }
}
