package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.ItemRequest;
import br.fatec.easycoast.dtos.ItemResponse;
import br.fatec.easycoast.entities.Item;

public class ItemMapper {
    public static Item toEntity(ItemRequest request){
        Item item = new Item();
        item.setName(request.name());
        item.setSquare(request.square());

        return item;
    }

    public static ItemResponse toDto(Item item){
        return new ItemResponse(item.getId(), item.getName(), item.getSquare());
    }
}
