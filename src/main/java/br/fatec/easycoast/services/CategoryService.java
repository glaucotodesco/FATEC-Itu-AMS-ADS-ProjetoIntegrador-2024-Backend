package br.fatec.easycoast.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.fatec.easycoast.entities.Category;
import br.fatec.easycoast.repositories.CategoryRepository;

@Service
public class CategoryService {
  
  @Autowired
    private CategoryRepository repository;

    public List <Category> getCategories(){
        return repository.findAll();
    }
}
