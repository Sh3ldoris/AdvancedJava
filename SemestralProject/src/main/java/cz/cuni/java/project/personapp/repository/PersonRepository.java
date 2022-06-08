package cz.cuni.java.project.personapp.repository;

import cz.cuni.java.project.personapp.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Page<Person> findAllByIsProfileGenerated(boolean isProfileGenerated, Pageable pageable);
}
