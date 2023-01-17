package app.repositories;

import app.domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByTeacherId(Long teacherId);

    Optional<Classroom> findByName(String name);

    List<Classroom> findManyByTeacherId(Long teacherId);
}
