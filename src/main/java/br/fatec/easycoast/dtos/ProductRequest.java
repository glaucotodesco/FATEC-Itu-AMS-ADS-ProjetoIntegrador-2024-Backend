package br.fatec.easycoast.dtos;

public record ProductRequest(
  String name,
  String description,
  Float price,
  Float discount,
  Boolean availability,
  String category
) {}
