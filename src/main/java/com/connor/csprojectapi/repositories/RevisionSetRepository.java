package com.connor.csprojectapi.repositories;

import com.connor.csprojectapi.data.RevisionSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RevisionSetRepository extends JpaRepository<RevisionSet, Long> {

    List<RevisionSet> getRevisionSetsByOwnerId(long ownerId);
    Optional<RevisionSet> findByIdAndOwnerId(long id, long ownerId);
    boolean existsByIdAndAndOwnerId(long id, long ownerId);

}
