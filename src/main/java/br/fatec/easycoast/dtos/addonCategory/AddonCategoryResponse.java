package br.fatec.easycoast.dtos.addonCategory;

import br.fatec.easycoast.dtos.addon.AddonResponse;
import br.fatec.easycoast.dtos.product.ProductRefDTO; // <-- MUDANÇA AQUI
import java.util.List;

public record AddonCategoryResponse(
                Integer id,
                String name,
                AddonType type,
                ProductRefDTO product, // <-- MUDANÇA AQUI
                List<AddonResponse> addons // <-- MUDANÇA AQUI
) {
}