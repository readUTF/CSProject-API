package com.connor.csprojectapi.repositories;

import com.connor.csprojectapi.data.RevisionSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RevisionSetRepository extends JpaRepository<RevisionSet, Long> {

    boolean existsByNameAndOwnerId(String name, long ownerId);
    Optional<RevisionSet> findByIdAndOwnerId(long id, long ownerId);

}
