package org.max.seminar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.max.seminar.exception.DeckEmptyException;

import java.util.ArrayList;
import java.util.List;

public class DeckTest {

    @Test
    void testDeckException() throws DeckEmptyException {
        //given
        DeckService deckService = new DeckService();
        Deck deck = deckService.getNewDeck();
        //when
        for(int i = 0; i < 52; i++) {
            deck.getCard();
        }
        //then
        Assertions.assertThrows(DeckEmptyException.class, () -> deck.getCard());
    }
}
