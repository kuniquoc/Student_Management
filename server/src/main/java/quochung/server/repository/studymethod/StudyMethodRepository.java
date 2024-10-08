package quochung.server.repository.studymethod;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quochung.server.model.studymethod.StudyMethod;

import java.util.Set;

@Repository
public interface StudyMethodRepository extends JpaRepository<StudyMethod, Long> {
    Set<StudyMethod> findByIdIn(Set<Long> ids);

    Page<StudyMethod> findByType(String type, Pageable pageable);
}
