package com.capgemini.wsb.persistence.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Collection;

import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Transactional
    @Test
    public void testGetPatientsByLastName() {
        // given
        final String lastName = "Doe";
        // when
        Collection<PatientEntity> results = patientDao.getPatientsByLastName(lastName);
        // then
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        assertThat(results.iterator().next().getLastName()).isEqualTo(lastName);
    }

    @Transactional
    @Test
    public void testGetPatientVisits() {
        // given
        final long patientId = 1;
        // when
        Collection<VisitEntity> visits = patientDao.getPatientVisits(patientId);
        // then
        assertThat(visits).isNotNull();
        assertThat(visits).isNotEmpty();
    }

    @Transactional
    @Test
    public void testGetPatientsWithMinNumberOfVisits() {
        // given
        final short minVisits = 2;
        // when
        Collection<PatientEntity> patients = patientDao.getPatientsWithMinNumberOfVisits(minVisits);
        // then
        assertThat(patients).isNotNull();
        assertThat(patients).isNotEmpty();
        for (PatientEntity patient : patients) {
            assertTrue(patientDao.getPatientVisits(patient.getId()).size() >= minVisits);
        }

    }


    @Transactional
    @Test
    public void testGetPatientsWithMinVisitCostDiscount() {
        // given
        final short minDiscount = 20;
        // when
        Collection<PatientEntity> patients = patientDao.getPatientsWithMinVisitCostDiscount(minDiscount);
        // then
        assertThat(patients).isNotNull();
        assertThat(patients).isNotEmpty();
        assertThat(patients.iterator().next().getVisitCostDiscount()).isGreaterThanOrEqualTo(minDiscount);
    }

}