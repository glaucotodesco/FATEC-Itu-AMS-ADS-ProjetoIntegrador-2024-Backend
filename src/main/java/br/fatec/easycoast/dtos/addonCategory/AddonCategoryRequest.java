package br.fatec.easycoast.dtos.addonCategory;

import br.fatec.easycoast.entities.Product;
import jakarta.validation.constraints.NotBlank;

public record AddonCategoryRequest(

                @NotBlank(message = "Nome da categoria de adicionais não pode ser vazio. ") String name,
                AddonType type,
                Product product) {
}