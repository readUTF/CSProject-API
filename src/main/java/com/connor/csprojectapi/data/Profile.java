package com.connor.csprojectapi.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @JsonSerialize
public class Profile {

    @Id
    @GeneratedValue
    private Long id;

    private Long authId;
    private String username;
    LocalDateTime lastUsernameChange;
    int completedCards;
    long revisionTime;

    @ElementCollection
    List<Long> savedRevisionCards;

    /**
     * The profile class stores personalised user data instead of data
     * involved in the authentication process.
     *
     * @param authId             - Foreign key from the Authentication database
     * @param username           - The users username, auto generated on account generation, but changeable after
     * @param lastUsernameChange - Stores the date of the last name change to limit name changes
     * @param completedCards     - Stores data on how many revision cards completed (stats & leaderboard)
     * @param revisionTime       - Time spent revising (stats & leaderboard)
     */
    public Profile(Long authId, String username, LocalDateTime lastUsernameChange, int completedCards, long revisionTime) {
        this.authId = authId;
        this.username = username;
        this.lastUsernameChange = lastUsernameChange;
        this.completedCards = completedCards;
        this.revisionTime = revisionTime;
    }

}
//    public Authentication getLinkedAuthentication(AuthenticationRepository authenticationRepository) {
//        return authenticationRepository.getById(authId);
//    }
//
//    public List<Long> getSavedRevisionCards() {
//        return savedRevisionCards == null ? savedRevisionCards = new ArrayList<>() : savedRevisionCards;
//    }
//}
