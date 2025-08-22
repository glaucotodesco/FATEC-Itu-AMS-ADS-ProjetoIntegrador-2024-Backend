package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.card.CardRequest;
import br.fatec.easycoast.dtos.card.CardResponse;
import br.fatec.easycoast.entities.Card;

public class CardMapper {
    public static Card toEntity(CardRequest request) {
        Card card = new Card();
        card.setActive(request.active());
        card.setCopy(request.copy());
        return card;
    }

    public static CardResponse toDto(Card card) {
        return new CardResponse(card.getId(), card.getActive(), card.getCopy());
    }
}
