package br.fatec.easycoast.dtos.orderItem;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.fatec.easycoast.dtos.addon.AddonResponse;
import br.fatec.easycoast.dtos.item.ItemResponse;
import br.fatec.easycoast.dtos.order.OrderResponse;
import br.fatec.easycoast.dtos.product.ProductResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemResponseWithOrder(
        Integer id,
        Integer quantity,
        String observations,
        Double total,
        Boolean reversed,
        @JsonIgnoreProperties("addonCategories") ProductResponse product,
        List<AddonResponse> addons,
        List<ItemResponse> removable,
        @JsonIgnoreProperties("orderItems") OrderResponse order
) {
}