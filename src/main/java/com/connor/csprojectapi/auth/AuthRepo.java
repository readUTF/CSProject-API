package com.connor.csprojectapi.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepo extends JpaRepository<Authentication, Long> {

    /**
     * Returns an Authentication Optional for the
     * corresponding account linked to the email
     * @param email - The email a user created their account with
     * @return Authentication Optional
     */
    Optional<Authentication> findAuthenticationByEmail(String email);

}
