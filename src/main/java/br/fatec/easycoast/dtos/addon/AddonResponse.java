package br.fatec.easycoast.dtos.addon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;
import br.fatec.easycoast.dtos.item.ItemResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddonResponse(
                Integer id,
                String name,
                Float price,
                Boolean availability,
                ItemResponse item,
                @JsonIgnoreProperties("addons") AddonCategoryResponse addonCategory) {
}