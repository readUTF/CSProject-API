package com.connor.csprojectapi.tokens;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Authentication Entity
 *
 * @Entity Marks the class as an object to be serialised and stored in a database
 * @NoArgsConstructor Tells lombok to generate a class constructor with no parameters when compiled
 * @Getter Tells lombok to generate Getter functions for all variables within the class
 * @Setter Tells lombok to generate Setter function for all variables within the class
 *
 * This is the entity class for authentication data, this is the main entity that all other
 * entities link back to as shown earlier in our database diagrams
 *
 */
@Entity @Getter @Setter @NoArgsConstructor
public class Token {

    @Id @GeneratedValue private Long id;
    long authId;
    UUID token;
    LocalDateTime generatedTime;
    String ipAddress;

    /**
     * This entity is used to store api authentication tokens
     * As outline in the project summery, an authentication token is used to grant a client
     * access to the database without an email and password being exchanged each time.
     *
     * Tokens are linked to the user ip-address to prevent tokens from being extracted from memory or local storage
     * and are time limited, expiring after 30 days.
     *
     * @param authId The id of the corresponding authentication entity
     * @param token The token itself, provided with each api request to authenticate
     * @param generatedTime The time at which the token was generated
     * @param ipAddress The ip of the client who generated the token
     */
    public Token(long authId, UUID token, LocalDateTime generatedTime, String ipAddress) {
        this.authId = authId;
        this.token = token;
        this.generatedTime = generatedTime;
        this.ipAddress = ipAddress;
    }

    public boolean isExpired() {
        return Duration.between(LocalDateTime.now(), generatedTime).toDays() > 30;
    }

}
