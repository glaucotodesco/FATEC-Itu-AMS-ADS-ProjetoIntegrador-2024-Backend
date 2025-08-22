package br.fatec.easycoast.dtos.orderItem;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.fatec.easycoast.dtos.addon.AddonResponse;
import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.entities.Order;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemResponse(
                Integer id,
                Integer quantity,
                String observations,
                Double total,
                @JsonIgnoreProperties("addonCategories") ProductResponse product,
                List<AddonResponse> addons,
                Order order) {
}