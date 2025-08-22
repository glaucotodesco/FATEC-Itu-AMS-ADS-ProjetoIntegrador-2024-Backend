package br.fatec.easycoast.dtos.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.fatec.easycoast.dtos.square.SquareResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
// Notation to avoid loop.
public record ItemResponse(
        Integer id,
        String name,
        @JsonIgnoreProperties("items") SquareResponse square) {

}
