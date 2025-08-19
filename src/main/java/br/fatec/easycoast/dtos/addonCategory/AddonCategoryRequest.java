package br.fatec.easycoast.dtos.addonCategory;

import br.fatec.easycoast.entities.Product;
import jakarta.validation.constraints.NotBlank;

public record AddonCategoryRequest(
    @NotBlank(message = "AddonCategory name can't be null")
    String name,
    AddonType type,
    Product product) {
}