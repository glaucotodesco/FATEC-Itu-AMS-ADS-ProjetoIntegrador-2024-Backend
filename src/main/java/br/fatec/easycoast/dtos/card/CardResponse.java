package br.fatec.easycoast.dtos.card;

import br.fatec.easycoast.entities.Order;

public record CardResponse(
    Integer id,
    Boolean active,
    Integer copy,
    Order order
) {

}