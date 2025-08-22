package br.fatec.easycoast.mappers;

import java.util.List;

import br.fatec.easycoast.dtos.item.ItemRequest;
import br.fatec.easycoast.dtos.item.ItemResponse;
import br.fatec.easycoast.dtos.item.ItemsOnly;
import br.fatec.easycoast.entities.Item;

public class ItemMapper {
    public static Item toEntity(ItemRequest request) {
        Item item = new Item();
        item.setName(request.name());
        item.setSquare(request.square());

        return item;
    }

    public static ItemResponse toDTO(Item item) {
        return new ItemResponse(item.getId(), item.getName(),
                item.getSquare() == null ? null : SquareMapper.toDto(item.getSquare()));
    }

    public static List<ItemsOnly> toListDTO(List<Item> items) {
        List<ItemsOnly> itemOnly = items.stream()
                .map(item -> toDtoItemsOnly(item))
                .toList();
        return itemOnly;
    }

    public static List<ItemResponse> toListItemResponseDTO(List<Item> items) {
        List<ItemResponse> itemResponses = items.stream()
                .map(item -> toDTO(item))
                .toList();
        return itemResponses;
    }

    public static ItemsOnly toDtoItemsOnly(Item item) {
        return new ItemsOnly(item.getId(), item.getName());
    }
}
