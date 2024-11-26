package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.ItemRequest;
import br.fatec.easycoast.dtos.ItemResponse;
import br.fatec.easycoast.entities.Item;
import br.fatec.easycoast.mappers.ItemMapper;
import br.fatec.easycoast.repositories.ItemRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public ItemResponse saveItem(ItemRequest request) {
        Item item = itemRepository.save(ItemMapper.toEntity(request));
        return ItemMapper.toDto(item);
    }

    public void updateItem(int id, ItemRequest request) {
        try {
            Item item = itemRepository.getReferenceById(id);

            item.setName(request.name());
            item.setSquare(request.square());

            itemRepository.save(item);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Item not found!");
        }
    }
}
