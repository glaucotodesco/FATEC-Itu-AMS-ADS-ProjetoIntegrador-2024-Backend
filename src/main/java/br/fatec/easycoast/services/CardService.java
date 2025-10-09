package br.fatec.easycoast.services;

import java.util.ArrayList;
import java.util.LinkedList;
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
        if (start < 0 || end < 0) throw new IllegalArgumentException("'start' and 'end' params needs to be positive!");
        //The cards that will be returned
        List<CardResponse> cards = new ArrayList<CardResponse>();

        //If it is a valid interval
        if (start <= end && end > 0) {
            //The card to be added in the list
            CardResponse aux = null;

            //get the last card created in the database
            int last = new LinkedList<CardResponse>(getCards()).getLast().id();
            //Setting the card to start the loop
            int i = last >= start ? start : last + 1;
            //The loop will last until it pass the last id in the interval
            for (; i <= end; i++) {
                if (i > 0) {
                    try {
                        //Get the existing card
                        aux = this.getCard(i);
                        //Update its copy value
                        this.updateCard(i, new CardRequest(
                            aux.active(),
                            aux.copy() + 1
                        )); 

                        //add to list
                        cards.add(this.getCard(i));
                    //If the card with that id doest exist
                    } catch (EntityNotFoundException e) {
                        //Create the new card
                        aux = this.saveCard(new CardRequest(true, i >= start ? 1 : 0));
                        //If its in the interval
                        if (aux.id() >= start) {
                            //add to list
                            cards.add(aux);
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("'end' param needs to be higher than 'start' param!");
        }

        //return the list
        return cards;
    }

    public List<CardResponse> filterCards(int start, int end) {
        if (start < 0 || end < 0) throw new IllegalArgumentException("'start' and 'end' params needs to be positive!");
        //The cards that will be returned
        List<CardResponse> cards = new ArrayList<CardResponse>();

        //If it is a valid interval
        if (start <= end && end > 0) {
            //The card to be added in the list
            CardResponse aux = null;

            //get the last card created in the database
            int last = new LinkedList<CardResponse>(getCards()).getLast().id();
            //Setting the card to start the loop
            int i = last >= start ? start : last + 1;
            //The loop will last until it pass the last id in the interval
            for (; i <= end; i++) {
                if (i > 0) {
                    try {
                        //Get the existing card, and add to the list
                        cards.add(this.getCard(i));
                    //If the card with that id doest exist
                    } catch (EntityNotFoundException e) {
                        //Create the new card
                        aux = this.saveCard(new CardRequest(true, 0));
                        //If its in the interval
                        if (aux.id() >= start) {
                            //add to list
                            cards.add(aux);
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("'end' param needs to be higher than 'start' param!");
        }

        //return the list
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
