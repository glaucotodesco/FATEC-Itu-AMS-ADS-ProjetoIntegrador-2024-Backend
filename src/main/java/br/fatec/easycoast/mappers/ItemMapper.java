package br.fatec.easycoast.mappers;

import java.util.List;

import br.fatec.easycoast.dtos.item.ItemRequest;
import br.fatec.easycoast.dtos.item.ItemResponse;
import br.fatec.easycoast.entities.Item;

public class ItemMapper {
    public static Item toEntity(ItemRequest request) {
        Item item = new Item();
        item.setName(request.name());
        item.setSquare(request.square());

        return item;
    }

    public static ItemResponse toDTO(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                SquareMapper.toDto(item.getSquare()));
    }

    public static List<ItemResponse> toListDTO(List<Item> items) {
        List<ItemResponse> itemsResponses = items.stream()
                .map(item -> toDTO(item))
                .toList();
        return itemsResponses;
    }

}
