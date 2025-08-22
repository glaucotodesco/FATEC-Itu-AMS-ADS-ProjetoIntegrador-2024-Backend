package br.fatec.easycoast.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.card.CardRequest;
import br.fatec.easycoast.dtos.card.CardResponse;
import br.fatec.easycoast.entities.Card;
import br.fatec.easycoast.mappers.CardMapper;
import br.fatec.easycoast.repositories.CardRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    public CardResponse getCard(int id) {
        Card card = cardRepository.findById(id) // Get the element in the DB
                .orElseThrow(() -> new EntityNotFoundException("Card not found!")); // Needed if the there is no element
                                                                                    // with that id
        return CardMapper.toDto(card); // Transform the element into a DTO and return
    }

    public List<CardResponse> getCards() {
        return cardRepository.findAll() // Get all the elements in the DB, and put in a list
                .stream() // Create a stream with the elements in the list
                .map(c -> CardMapper.toDto(c)) // Create another stream, transforming all the elements to DTOs
                .collect(Collectors.toList()); // Return a new list with the elements of the last stream
    }

    public CardResponse saveCard(CardRequest request) { // Need the base Element, but without the ID
        Card card = cardRepository.save(CardMapper.toEntity(request)); // Save the element, and adding an ID
        return CardMapper.toDto(card); // Transform the element into a DTO
    }

    public List<CardResponse> printCards(int start, int end) {
        List<CardResponse> cards = new ArrayList<CardResponse>();

        for (int i = start; i <= end; i++) {
            try {
                cards.add(this.getCard(i));
            } catch (EntityNotFoundException e) {
                cards.add(this.saveCard(new CardRequest(true, 1)));
            }
        }

        return cards;
    }

    public void updateCard(int id, CardRequest request) {
        try {
            Card card = cardRepository.getReferenceById(id); // Get an reference of the element

            // Set the changes
            card.setActive(request.active());
            card.setCopy(request.copy());

            cardRepository.save(card); // Overwrite the element with the same ID
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Card not found!"); // Needed because the ID could not exist
        }
    }
}
