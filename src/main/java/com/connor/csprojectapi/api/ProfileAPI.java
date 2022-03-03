package com.connor.csprojectapi.api;

import com.connor.csprojectapi.data.Profile;
import com.connor.csprojectapi.data.RevisionCard;
import com.connor.csprojectapi.data.RevisionSet;
import com.connor.csprojectapi.data.Token;
import com.connor.csprojectapi.repositories.ProfileRepository;
import com.connor.csprojectapi.repositories.RevisionCardRepository;
import com.connor.csprojectapi.repositories.RevisionSetRepository;
import com.connor.csprojectapi.repositories.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@RestController("/api/profile/")
public class ProfileAPI {

    ProfileRepository profileRepo;
    TokenRepository tokenRepo;
    RevisionSetRepository setRepo;
    RevisionCardRepository cardRepo;

    //Marks API end point for /api/profile/profile
    @GetMapping("profile")
    public ResponseEntity<?> getProfile(UUID token, long profileId) {
        //find profile by provided id
        Optional<Profile> profileById = profileRepo.findProfileById(profileId);
        //check if profile is not found
        if(profileById.isEmpty()) {
            //respond with bad request because no profile is found
            return new ResponseEntity<>("Profile not found", HttpStatus.BAD_REQUEST);
        }
        //value is presented so can now be cast directly to Profile
        Profile profile = profileById.get();
        //check if token provided is a valid authentication token for this profile
        if (!isValidToken(token, profile)) return new ResponseEntity<>("Invalid Auth", HttpStatus.BAD_REQUEST);
        //profile is found, auth is valid, return profile
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("sets")
    public ResponseEntity<?> getSets(@RequestParam("token") UUID token, long profileId) {
        //find profile by the provided id
        Optional<Profile> profileById = profileRepo.findProfileById(profileId);
        //check if a profile is found
        if(profileById.isEmpty()) {
            //no profile found so respond with BAD_REQUEST
            return new ResponseEntity<>("Profile not found", HttpStatus.BAD_REQUEST);
        }

        //value is presented so can be resolved
        Profile profile = profileById.get();
        if (!isValidToken(token, profile)) return new ResponseEntity<>("Invalid Auth", HttpStatus.BAD_REQUEST);
        List<RevisionSet> sets = setRepo.getRevisionSetsByOwnerId(profileId);
        return new ResponseEntity<>(sets, HttpStatus.OK);
    }

    @PutMapping("set")
    public ResponseEntity<?> createSet(@RequestParam("uuid") UUID token, long profileId, String name) {
        Optional<Profile> profileById = profileRepo.findProfileById(profileId);
        if(profileById.isEmpty()) {
            return new ResponseEntity<>("Profile not found", HttpStatus.BAD_REQUEST);
        }
        Profile profile = profileById.get();
        if (!isValidToken(token, profile)) return new ResponseEntity<>("Invalid Auth", HttpStatus.BAD_REQUEST);
        RevisionSet save = setRepo.save(new RevisionSet(name, profileId, true));
        profile.getSavedSets().add(save.getId());
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @DeleteMapping("set")
    public ResponseEntity<?> deleteSet(@RequestParam("uuid") UUID token, long profileId, long setId) {
        Optional<Profile> profileById = profileRepo.findProfileById(profileId);
        if(profileById.isEmpty()) {
            return new ResponseEntity<>("Profile not found", HttpStatus.BAD_REQUEST);
        }
        Profile profile = profileById.get();
        Optional<RevisionSet> optionalRevisionSet = setRepo.findByIdAndOwnerId(setId, profileId);
        if (!isValidToken(token, profile)) return new ResponseEntity<>("Invalid Auth", HttpStatus.BAD_REQUEST);
        if(optionalRevisionSet.isEmpty()) return new ResponseEntity<>("No set found", HttpStatus.OK);
        setRepo.delete(optionalRevisionSet.get());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/set/cards")
    public ResponseEntity<?> getCard(UUID token, long profileId, long setId) {
        Optional<Profile> profileById = profileRepo.findProfileById(profileId);
        if(profileById.isEmpty()) {
            return new ResponseEntity<>("Profile not found", HttpStatus.BAD_REQUEST);
        }
        Profile profile = profileById.get();
        if (!isValidToken(token, profile)) return new ResponseEntity<>("Invalid Auth", HttpStatus.BAD_REQUEST);
        List<RevisionCard> allBySetId = cardRepo.getAllBySetId(setId);
        return new ResponseEntity<>(allBySetId, HttpStatus.OK);
    }

    @PutMapping("/set/card")
    public ResponseEntity<?> addCard(UUID token, long profileId, long setId, String key, String desc) {
        Optional<Profile> profileById = profileRepo.findProfileById(profileId);
        if(profileById.isEmpty()) {
            return new ResponseEntity<>("Profile not found", HttpStatus.BAD_REQUEST);
        }
        Profile profile = profileById.get();
        if (!isValidToken(token, profile)) return new ResponseEntity<>("Invalid Auth", HttpStatus.BAD_REQUEST);
        if(!setRepo.existsByIdAndAndOwnerId(setId, profileId))
            return new ResponseEntity<>("Set not found", HttpStatus.BAD_REQUEST);
        RevisionCard save = cardRepo.save(new RevisionCard(setId, key, desc));
        return new ResponseEntity<>(save, HttpStatus.OK);
    }



    public boolean isValidToken(UUID token, Profile profile) {
        Optional<Token> byToken = tokenRepo.findByToken(token);
        return byToken.isPresent() && byToken.get().getAuthId() == profile.getAuthId();
    }

}
