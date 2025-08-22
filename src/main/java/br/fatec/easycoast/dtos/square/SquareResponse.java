package br.fatec.easycoast.dtos.square;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.entities.Item;

//Notation to avoid loop. 
public record SquareResponse(
                Integer id,
                String name,
                @JsonIgnoreProperties("square") List<Item> items) {

}
