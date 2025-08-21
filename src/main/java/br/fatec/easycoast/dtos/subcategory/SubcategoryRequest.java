package br.fatec.easycoast.dtos.subcategory;

import br.fatec.easycoast.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubcategoryRequest(
        @NotBlank(message = "The name of subcategory cannot be blank.")
        String name,
        
        @NotNull(message = "The availability cannot be null")
        Boolean availability,
        
        @NotNull(message = "The category cannot by null")
        Category category
) {
}