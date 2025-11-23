package br.fatec.easycoast.dtos.orderItem;

import br.fatec.easycoast.dtos.addon.AddonOrderResponse;

public record OrderItemAddonOrderResponse(
    AddonOrderResponse addon,
    Integer quantity
) {

}