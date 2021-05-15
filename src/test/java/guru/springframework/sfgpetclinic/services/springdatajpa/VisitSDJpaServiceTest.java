package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepoMock;

    @InjectMocks
    VisitSDJpaService visitSDJpaServ;

    @Test
    void findAll() {
        Visit visit1 = new Visit();
        visit1.setDescription("Visit1");
        Visit visit2 = new Visit();
        visit1.setDescription("Visit2");
        Set<Visit> visits = new HashSet<>(Arrays.asList(visit1, visit2));

        when(visitRepoMock.findAll()).thenReturn(visits);
        Set<Visit> foundVisits = visitSDJpaServ.findAll();


        assertThat(foundVisits).contains(visit1);

    }

    @Test
    void findById() {
        Visit visit = new Visit();
        visit.setDescription("Visit");

        when(visitRepoMock.findById(anyLong())).thenReturn(Optional.of(visit));
        Visit foundVisit = visitSDJpaServ.findById(1L);

        assertThat(foundVisit.getDescription()).isEqualTo("Visit");
    }

    @DisplayName("Test Save Visits")
    @Test
    void save() {
        Visit visit = new Visit();
        visit.setDescription("Visit A");

        when(visitRepoMock.save(any(Visit.class))).thenReturn(visit);
        Visit savedVisit = visitSDJpaServ.save(visit);

        assertThat(savedVisit.getDescription()).isEqualTo("Visit A");
        verify(visitRepoMock, times(1)).save(any(Visit.class));
    }

    @Test
    void delete() {
        Visit visit = new Visit();
        visit.setDescription("Visit B");
        visitSDJpaServ.delete(visit);
        verify(visitRepoMock).delete(visit);
    }

    @Test
    void deleteById() {
        Visit visit = new Visit();
        visit.setDescription("Visit C");
        visitSDJpaServ.deleteById(1L);
        verify(visitRepoMock).deleteById(anyLong());
    }
}