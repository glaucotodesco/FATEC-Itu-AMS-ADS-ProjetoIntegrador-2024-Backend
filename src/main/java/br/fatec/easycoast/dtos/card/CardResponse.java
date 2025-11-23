package br.fatec.easycoast.dtos.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude; 
import br.fatec.easycoast.dtos.order.OrderResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CardResponse(
    Integer id,
    Boolean active,
    Integer copy,
    @JsonIgnoreProperties("card")
    OrderResponse order 
) {

}