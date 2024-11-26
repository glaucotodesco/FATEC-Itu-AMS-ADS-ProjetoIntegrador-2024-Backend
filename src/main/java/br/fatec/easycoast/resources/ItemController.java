package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.ItemFilter;
import br.fatec.easycoast.dtos.ItemRequest;
import br.fatec.easycoast.services.ItemService;


@RestController
@CrossOrigin
@RequestMapping("items")
public class ItemController {
    @Autowired
    ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemFilter> saveItem(@RequestBody ItemRequest request){
        ItemFilter item = itemService.saveItem(request);

        URI location = ServletUriComponentsBuilder
                       .fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(item.id())
                       .toUri();

        return ResponseEntity.created(location).body(item);
    }

    @GetMapping
    public ResponseEntity<List<ItemFilter>> getItems(){
        return ResponseEntity.ok(itemService.getItems());
        
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemFilter> getItem(@PathVariable int id){
        return ResponseEntity.ok(itemService.getItem(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateItem(@PathVariable int id, @RequestBody ItemRequest request){
        itemService.updateItem(id, request);
        return ResponseEntity.ok().build();
    }
}
