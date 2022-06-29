package com.connor.csprojectapi.repositories;

import com.connor.csprojectapi.data.RevisionCard;
import com.connor.csprojectapi.data.RevisionSet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RevisionCardRepository extends JpaRepository<RevisionCard, Long> {

    List<RevisionCard> getAllBySetId(long setId);
    boolean existsBySetIdAndId(long setId, long id);

    @Transactional
    void deleteBySetIdAndId(long setId, long id);


}
