package app.repositories;

import app.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByTeacherId(Long teacherId);

    Optional<Grade> findByStudentId(Long studentId);
}
