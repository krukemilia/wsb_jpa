package com.capgemini.wsb.service.impl;

import com.capgemini.wsb.dto.PatientTO;
import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Collection;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PatientServiceImplTest {

    @Mock
    private PatientDao patientDao;

    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById_whenPatientExists_returnsPatientTO() {
        // Given
        Long patientId = 1L;
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientId);
        patientEntity.setFirstName("Piotr");
        patientEntity.setLastName("Wiśniewski");
        Collection<VisitEntity> visits = new ArrayList<>();
        when(patientDao.findOne(patientId)).thenReturn(patientEntity);
        when(patientDao.getPatientVisits(patientId)).thenReturn(visits);

        // When
        PatientTO result = patientService.findById(patientId);

        // Then
        assertNotNull(result);
        assertEquals(patientId, result.getId());
        assertEquals("Piotr", result.getFirstName());
        assertEquals("Wiśniewski", result.getLastName());
        verify(patientDao, times(1)).findOne(patientId);
        verify(patientDao, times(1)).getPatientVisits(patientId);
    }

    @Test
    void findById_whenPatientDoesNotExist_returnsNull() {
        // Given
        Long patientId = 999L;
        when(patientDao.findOne(patientId)).thenReturn(null);

        // When
        PatientTO result = patientService.findById(patientId);

        // Then
        assertNull(result);
        verify(patientDao, times(1)).findOne(patientId);
    }

    @Test
    void findById_whenPatientExists_hasVisits() {
        // Given
        Long patientId = 1L;
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientId);
        patientEntity.setFirstName("John");
        patientEntity.setLastName("Doe");

        Long visitId = 1L;
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setId(visitId);
        visitEntity.setDescription("Opis wizyty");

        Collection<VisitEntity> visits = new ArrayList<>();
        visits.add(visitEntity);
        when(patientDao.findOne(patientId)).thenReturn(patientEntity);
        when(patientDao.getPatientVisits(patientId)).thenReturn(visits);

        // When
        PatientTO result = patientService.findById(patientId);

        // Then
        verify(patientDao, times(1)).findOne(patientId);
        verify(patientDao, times(1)).getPatientVisits(patientId);
        assertNotNull(result.getVisits());
        assertEquals(1, result.getVisits().size());
    }
}