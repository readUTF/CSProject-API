package com.connor.csprojectapi.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class RevisionSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    String name;
    long ownerId;
    boolean privateSet;

    /**
     * RevisionSet is a collection of revision cards. When the user creates a set
     * they will need to provide a name and can decide if the set can be accessible by
     * other people. Used as a linking entity for cards.
     *
     * @param name    - Name of the set
     * @param ownerId - The owner of the cards database id (foreign key)
     */
    public RevisionSet(String name, long ownerId, boolean privateSet) {
        this.name = name;
        this.ownerId = ownerId;
        this.privateSet = privateSet;
    }
}
