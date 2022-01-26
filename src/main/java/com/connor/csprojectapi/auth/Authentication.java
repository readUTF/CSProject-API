package com.connor.csprojectapi.auth;


import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Authentication Entity
 *
 * @Entity Marks the class as an object to be serialised and stored in a database
 * @NoArgsConstructor Tells lombok to generate a class constructor with no parameters when compiled
 *
 * This is the entity class for authentication data, this is the main entity that all other
 * entities link back to as shown earlier in our database diagrams
 *
 */
@Entity @NoArgsConstructor
public class Authentication {

    /**
     * @Id Marks the variable as the database's primary key
     * @GeneratedValue allows for the variable to be atomically assigned and incremented value on instantiation
     */
    @Id @GeneratedValue(strategy = GenerationType.TABLE) private long id;

    String email;
    String password;


    public Authentication(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
