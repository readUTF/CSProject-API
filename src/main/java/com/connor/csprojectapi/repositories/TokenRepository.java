package com.connor.csprojectapi.repositories;

import com.connor.csprojectapi.data.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(UUID token);

}
