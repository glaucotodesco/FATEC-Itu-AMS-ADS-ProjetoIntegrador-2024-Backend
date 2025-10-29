package br.fatec.easycoast.dtos.orderItem;

import br.fatec.easycoast.dtos.addon.AddonResponse;

public record OrderItemAddonResponse(
    AddonResponse addon,
    Integer quantity
) {

}
