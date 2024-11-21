package br.fatec.easycoast.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.SquareRequest;
import br.fatec.easycoast.dtos.SquareResponse;
import br.fatec.easycoast.services.SquareService;

@RestController
@RequestMapping("squares")
public class SquareController {
    @Autowired
    SquareService squareService;

    @PostMapping
    public ResponseEntity<SquareResponse> saveSquare(@RequestBody SquareRequest request){
        SquareResponse square = squareService.saveSquare(request);

        URI location = ServletUriComponentsBuilder
                       .fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(square.Id())
                       .toUri();

        return ResponseEntity.created(location).body(square);
    }
}
