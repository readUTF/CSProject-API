package com.connor.csprojectapi.repositories;

import com.connor.csprojectapi.data.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Authentication, Long> {

    /**
     * Returns an Authentication Optional for the
     * corresponding account linked to the email
     * @param email - The email a user created their account with
     * @return Authentication Optional
     */
    Optional<Authentication> findAuthenticationByEmail(String email);
    boolean existsAuthenticationByEmail(String email);
}
