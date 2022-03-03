package com.connor.csprojectapi.repositories;

import com.connor.csprojectapi.data.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;


public interface ProfileRepository extends JpaRepository<Profile, Long> {

    boolean existsProfileByUsername(String username);
    Optional<Profile> findProfileByUsername(String username);
    Profile getProfileByAuthId(long authId);
    Optional<Profile> findProfileById(long id);

}
