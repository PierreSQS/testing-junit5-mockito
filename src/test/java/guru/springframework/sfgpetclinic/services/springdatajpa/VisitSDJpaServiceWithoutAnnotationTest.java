package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VisitSDJpaServiceWithoutAnnotationTest {

    @Mock
    VisitRepository visitRepoMock;

    VisitSDJpaService visitSDJpaServ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        visitSDJpaServ = new VisitSDJpaService(visitRepoMock);

    }

    @Test
    void findAll() {
        // Given
        Visit visitMock1 = new Visit();
        visitMock1.setId(1L);
        visitMock1.setDescription("Mock Visit1");
        visitMock1.setDate(LocalDate.of(2021, 4, 3));

        Visit visitMock2 = new Visit();
        visitMock2.setId(2L);
        visitMock2.setDescription("Mock Visit1");
        visitMock2.setDate(LocalDate.of(2021, 4, 3));
        when(visitSDJpaServ.findAll()).thenReturn(new HashSet<>(Arrays.asList(visitMock1,visitMock2)));

        // When
        Set<Visit> allVisits = visitSDJpaServ.findAll();
        List<Visit> visitList = new ArrayList<>(allVisits);

        // Then
        assertThat(visitList.get(0).getDate()).isEqualTo("2021-04-03");
        assertThat(visitList).contains(visitMock1,visitMock2);
    }
    @Test
    void findById() {
        // Given
        Long aLong = 2L;
        Visit visitMock = new Visit();
        visitMock.setDescription("Visit Mock");
        visitMock.setId(aLong);
        when(visitRepoMock.findById(aLong)).thenReturn(Optional.of(visitMock));
        // When
        Visit aVisitById = visitSDJpaServ.findById(aLong);

        // Then
        assertThat(aVisitById).isEqualTo(visitMock);
    }

    @Test
    void save() {
        // Given
        Long aLong = 3L;
        LocalDate visitDate = LocalDate.of(2021, 3, 4);
        Visit visitToSave = new Visit();
        visitToSave.setId(aLong);
        visitToSave.setDescription("Mock Visit");
        visitToSave.setDate(visitDate);
        when(visitRepoMock.save(visitToSave)).thenReturn(visitToSave);

        // When
        Visit savedVisit = visitSDJpaServ.save(visitToSave);

        // Then
        assertThat(savedVisit).isNotNull();
        assertThat(savedVisit.getDate()).isEqualTo(visitDate);
    }

    @Test
    void delete() {
        // Given
        Long aLong = 5L;
        Visit visitToDelete = new Visit();
        visitToDelete.setDescription("Mock Visit");
        visitToDelete.setId(aLong);

        // When
        visitSDJpaServ.delete(visitToDelete);

        // Then
        verify(visitRepoMock).delete(visitToDelete);
    }

    @Test
    void deleteById() {
        // Given
        Long aLong = 7L;

        // When
        visitSDJpaServ.deleteById(aLong);

        // Then
        verify(visitRepoMock).deleteById(aLong);
    }
}