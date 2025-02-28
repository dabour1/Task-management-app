package com.tasks.tma.repository;

 import com.tasks.tma.models.User;
 import org.hibernate.annotations.UpdateTimestamp;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
 import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
       Optional<User> findByEmail(String email);

}