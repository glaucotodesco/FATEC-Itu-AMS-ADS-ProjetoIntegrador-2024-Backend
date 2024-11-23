package br.fatec.easycoast.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.ItemRequest;
import br.fatec.easycoast.dtos.ItemResponse;
import br.fatec.easycoast.services.ItemService;

@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> saveItem(@RequestBody ItemRequest request){
        ItemResponse item = itemService.saveItem(request);

        URI location = ServletUriComponentsBuilder
                       .fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(item.id())
                       .toUri();

        return ResponseEntity.created(location).body(item);
    }
}
