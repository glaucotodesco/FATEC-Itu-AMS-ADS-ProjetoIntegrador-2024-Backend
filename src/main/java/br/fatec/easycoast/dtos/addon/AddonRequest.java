package br.fatec.easycoast.dtos.addon;

import br.fatec.easycoast.entities.AddonCategory;
import br.fatec.easycoast.entities.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddonRequest(

                @NotBlank(message = "Nome do adicional não pode ser em branco") @Size(min = 3, message = "Tamanho mínimo para nome dp adicional é 3") String name,
                Float price,
                @NotNull(message = "A disponibilidade não pode ser nula aqui.") Boolean availability,
                Item item,
                AddonCategory addonCategory

) {
}