package br.fatec.easycoast.dtos;

public record ProductResponse(
  Integer id,
  String name,
  String description,
  Float price,
  Float discount,
  Boolean availability,
  String category,
  String imageurl
) {}
