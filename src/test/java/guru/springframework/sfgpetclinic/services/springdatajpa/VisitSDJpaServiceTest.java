package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepoMock;

    @InjectMocks
    VisitSDJpaService visitSDJpaServ;

    @Test
    void findAll() {
        // Given
        Visit visitMock1 = new Visit();
        visitMock1.setDate(LocalDate.of(2021,4,21));
        visitMock1.setDescription("Mock Visit 1");

        Visit visitMock2 = new Visit();
        visitMock2.setDate(LocalDate.of(2021,5,22));
        visitMock2.setDescription("Mock Visit 2");
        when(visitRepoMock.findAll()).thenReturn(new HashSet<>(Arrays.asList(visitMock1,visitMock2)));


        // Then
        Set<Visit> allVisits = visitSDJpaServ.findAll();

        // Then
        assertThat(allVisits).isNotNull()
                             .contains(visitMock1,visitMock2);
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
        assertThat(visitToSave).isNotNull();
        assertThat(visitToSave.getDate()).isEqualTo(visitDate);
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