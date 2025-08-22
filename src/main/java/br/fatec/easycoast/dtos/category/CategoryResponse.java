package br.fatec.easycoast.dtos.category;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.entities.Product;

//Modificado para retornar Product mas sem Category para não dar loop e ficar redundante. 
public record CategoryResponse(

        Integer id,
        String name,
        Boolean availability,
        @JsonIgnoreProperties("category") List<Product> products) {

}
