package br.fatec.easycoast.dtos;

import java.util.List;

public record ProductResponse(
  Integer id,
  String name,
  String description,
  Float price,
  Float discount,
  Boolean availability,
  String category,
  String imageurl
  // List<AddonResponse> addon
) {}
