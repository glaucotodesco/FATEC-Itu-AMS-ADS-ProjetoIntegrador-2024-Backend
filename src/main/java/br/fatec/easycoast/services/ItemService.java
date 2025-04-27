package br.fatec.easycoast.services;

import java.util.List;
import java.util.stream.Collectors;

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

    public ItemResponse getItem(int id) {
        Item item = itemRepository.findById(id)
                                  .orElseThrow(() -> new EntityNotFoundException("Item not found!"));
        return ItemMapper.toDTO(item);
    }

    public List<ItemResponse> getItems(){
        return itemRepository.findAll()
                             .stream()
                             .map(s -> ItemMapper.toDTO(s))
                             .collect(Collectors.toList());
    }

    public ItemResponse saveItem(ItemRequest request) {
        Item item = itemRepository.save(ItemMapper.toEntity(request));
        return ItemMapper.toDTO(item);
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
