package br.fatec.easycoast.dtos.addon;

import br.fatec.easycoast.entities.AddonCategory;
import br.fatec.easycoast.entities.Square;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddonRequest(

        @NotBlank(message = "Name of the addon cant be blank")
        @Size(min = 3, message = "Minimum name size is 3 characters")
        String name,
        Float price,
        @NotNull(message = "Availability can't be null")
        Boolean availability,
        Integer maxQuantity,
        // Item item,
        Square square,
        AddonCategory addonCategory

) {
}