package br.fatec.easycoast.dtos.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.entities.Order;

public record CardResponse(
    Integer id,
    Boolean active,
    Integer copy,
    @JsonIgnoreProperties("card")
    Order order 
) {

}