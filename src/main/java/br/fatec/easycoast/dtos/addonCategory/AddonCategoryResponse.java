package br.fatec.easycoast.dtos.addonCategory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import br.fatec.easycoast.dtos.addon.AddonResponse;
import br.fatec.easycoast.dtos.product.ProductRefDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public record AddonCategoryResponse(
    Integer id,
    String name,
    AddonType type,
    @JsonIgnore ProductRefDTO product,
    @JsonIgnoreProperties("addonCategory") List<AddonResponse> addons) {
}