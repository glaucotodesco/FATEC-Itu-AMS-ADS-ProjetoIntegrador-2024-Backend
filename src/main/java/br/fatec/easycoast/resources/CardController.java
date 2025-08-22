package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.card.CardRequest;
import br.fatec.easycoast.dtos.card.CardResponse;
import br.fatec.easycoast.services.CardService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin // Needed for the Frontend
@RequestMapping
public class CardController {
    @Autowired
    CardService cardService;

    @GetMapping("cards")
    public ResponseEntity<List<CardResponse>> getCards(@RequestParam(name = "_start", required = false) Integer start,
                                                       @RequestParam(name = "_end", required = false) Integer end,
                                                       HttpServletRequest request) { // Response Entity to return the HTTP Status
        if (request.getParameterMap().containsKey("_start") && request.getParameterMap().containsKey("_end")) { //Check if has the start and end
            return ResponseEntity.ok(cardService.printCards(start, end));
        } else {
            return ResponseEntity.ok(cardService.getCards()); // Ok == Status code 200
        }
    }

    @GetMapping("cards/{id}")
    public ResponseEntity<CardResponse> getCard(@PathVariable int id) { // PathVariable to get the id in the URL
        return ResponseEntity.ok(cardService.getCard(id));
    }

    @PostMapping("cards")
    public ResponseEntity<CardResponse> saveCard(@RequestBody CardRequest request) {
        CardResponse response = cardService.saveCard(request); // Save and the get the element with ID

        // Creating the URI of the new element
        URI location = ServletUriComponentsBuilder // Class to create URI
                .fromCurrentRequest() // Get the current URI
                .path("/{id}") // Add /{id} at the and of the URI
                .buildAndExpand(response.id()) // Replace {id} with the id of the element
                .toUri(); // Create the new URI

        // Returning the response entity with new element
        return ResponseEntity.created(location).body(response); // Created == 201
    }

    @PutMapping("cards/{id}")
    public ResponseEntity<Void> updateCard(@PathVariable int id, @RequestBody CardRequest request) {
        cardService.updateCard(id, request);
        return ResponseEntity.ok().build(); // Return the status code 200, with no content
    }
}
