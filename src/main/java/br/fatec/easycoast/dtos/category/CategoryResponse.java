package br.fatec.easycoast.dtos.category;

import java.util.List;

import br.fatec.easycoast.dtos.product.ProductNoCategory;

//Modificado para retornar Product mas sem Category para não dar loop e ficar redundante. 
public record CategoryResponse(

        Integer id,
        String name,
        Boolean availability,
        List<ProductNoCategory> products) {

}
