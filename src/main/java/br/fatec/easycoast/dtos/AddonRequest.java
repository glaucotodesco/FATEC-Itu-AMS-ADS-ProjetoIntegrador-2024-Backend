package br.fatec.easycoast.dtos;

import br.fatec.easycoast.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddonRequest(

        @NotBlank(message = "Nome do adicional não pode ser em branco") 
        @Size(min = 3, message = "Tamanho mínimo para nome dp adicional é 3") 
        String name,
        Float price,

        @NotNull(message = "Disponibilidade não pode ser nulo")
        Boolean avaliability,

        @NotNull(message = "Produto não pode ser nulo")
        Product product

) {
}