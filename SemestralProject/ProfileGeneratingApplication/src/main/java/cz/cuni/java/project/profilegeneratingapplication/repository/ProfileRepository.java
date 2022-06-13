package cz.cuni.java.project.profilegeneratingapplication.repository;

import cz.cuni.java.project.profilegeneratingapplication.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
}
