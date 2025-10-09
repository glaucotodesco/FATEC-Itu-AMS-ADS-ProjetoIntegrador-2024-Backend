package br.fatec.easycoast.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Category name can't be blank")
        @Size(min = 3, message = "Minimum name size is 3 characters")
        String name,
        @NotNull(message = "Avalilability can't be null")
        Boolean availability
) {

}
