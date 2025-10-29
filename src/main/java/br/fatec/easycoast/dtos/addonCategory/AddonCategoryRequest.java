package br.fatec.easycoast.dtos.addonCategory;

import br.fatec.easycoast.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddonCategoryRequest(
    @NotBlank(message = "AddonCategory name can't be null")
    String name,
    @NotNull(message = "Type can't be null")
    AddonType type,
    @NotNull(message = "Product can't be null")
    Product product) {
}