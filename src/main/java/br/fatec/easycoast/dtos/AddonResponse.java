package br.fatec.easycoast.dtos;




public record AddonResponse(
    Integer id,
    String name, 
    Float price, 
    Boolean availability, 
    ProductResponse product, 
    ItemResponse item
) {} 