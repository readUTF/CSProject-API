package com.connor.csprojectapi.card;

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

    String keyWord;
    String description;
    int fails;
    int successes;

    /**
     * This entity will also be used in the learning algorithm and uses the
     * success and failure values to find the next best card
     * Fails and successes are set at 0 on creation as they update over time
     *
     * @param setId       - Foreign key linking the current card to the set it's contained in
     * @param keyWord     - The keyword for the revision card, decided by the user
     * @param description - The description (back of card), decided by the user
     */
    public RevisionCard(long setId, String keyWord, String description) {
        this.setId = setId;
        this.keyWord = keyWord;
        this.description = description;
        this.fails = 0;
        this.successes = 0;

    }
}
