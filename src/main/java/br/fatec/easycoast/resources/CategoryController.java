package br.fatec.easycoast.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import br.fatec.easycoast.entities.Category;
import br.fatec.easycoast.services.CategoryService;

@RestController
public class CategoryController {

  @Autowired
  private CategoryService service;

  @GetMapping("categories")
  public List <Category> getCategories(){
    return service.getCategories();
  }

  
}
