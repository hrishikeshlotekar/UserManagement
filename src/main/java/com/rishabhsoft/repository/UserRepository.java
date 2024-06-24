package com.rishabhsoft.repository;

import com.rishabhsoft.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * It is responsible for handling the user repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    User findByEmail(String email);

    Page<User> findAll( Pageable pageable);
}
