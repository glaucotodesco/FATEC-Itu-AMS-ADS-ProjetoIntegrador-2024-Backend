package br.fatec.easycoast.dtos;

public record CategoryResponse(
    Integer id,
    String name,
    Boolean availability
    ) {}
