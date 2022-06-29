package com.connor.csprojectapi.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class RevisionCard {

    @Id
    @GeneratedValue
    long id;
    long setId;

    String keyword;
    String description;
    int fails;
    int successes;

    /**
     * This entity will also be used in the learning algorithm and uses the
     * success and failure values to find the next best card
     * Fails and successes are set at 0 on creation as they update over time
     *
     * @param setId       - Foreign key linking the current card to the set it's contained in
     * @param description
     */
    public RevisionCard(long setId, String keyword, String description) {
        this.setId = setId;
        this.description = description;
        this.fails = 0;
        this.successes = 0;
        this.keyword = keyword;

    }
}
