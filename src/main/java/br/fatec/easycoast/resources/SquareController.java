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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.SquareResponse;
import br.fatec.easycoast.dtos.SquareItemsOnly;
import br.fatec.easycoast.dtos.SquareRequest;
import br.fatec.easycoast.dtos.SquareResponse;
import br.fatec.easycoast.services.SquareService;

@RestController
@CrossOrigin
@RequestMapping("squares")
public class SquareController {
    @Autowired
    SquareService squareService;

    @GetMapping
    public ResponseEntity<List<SquareItemsOnly>> getSquares(){
        return ResponseEntity.ok(squareService.getSquares());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SquareItemsOnly> getSquare(@PathVariable int id){
        return ResponseEntity.ok(squareService.getSquare(id));
    }

    @PostMapping
    public ResponseEntity<SquareResponse> saveSquare(@RequestBody SquareRequest request){
        SquareResponse square = squareService.saveSquare(request);

        URI location = ServletUriComponentsBuilder
                       .fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(square.id())
                       .toUri();

        return ResponseEntity.created(location).body(square);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSquare(@PathVariable int id, @RequestBody SquareRequest request){
        squareService.updateSquare(id, request);
        return ResponseEntity.ok().build();
    }
}
