package br.fatec.easycoast.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.ItemFilter;
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

    public List<ItemFilter> getItems(){
        return itemRepository.findAll()
        .stream()
        .map(s -> ItemMapper.toDto(s))
        .collect(Collectors.toList());
    }

    public ItemFilter getItem(int id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found!"));
        return ItemMapper.toDto(item);
    }

    public ItemFilter saveItem(ItemRequest request) {
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
