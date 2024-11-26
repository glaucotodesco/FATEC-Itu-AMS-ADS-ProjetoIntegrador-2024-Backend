package br.fatec.easycoast.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.CategoryRequest;
import br.fatec.easycoast.dtos.CategoryResponse;
import br.fatec.easycoast.entities.Category;
import br.fatec.easycoast.mappers.CategoryMapper;
import br.fatec.easycoast.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
  
  @Autowired
    private CategoryRepository repository;

    public List <CategoryResponse> getCategories(){
      return repository.findAll()
                       .stream()
                       .map(category -> CategoryMapper.toDto(category))
                       .toList();
  }

  public CategoryResponse getCategoryById(int id ){
    Category category = repository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Categoria não Cadastrada")
    );
        return CategoryMapper.toDto(category); 
}

  public CategoryResponse save(CategoryRequest dtoRequestCategory){
    Category category = repository.save(CategoryMapper.toEntity(dtoRequestCategory));
    return CategoryMapper.toDto(category);
  }

  public void deleteById(int id){
    if(repository.existsById(id)){
        repository.deleteById(id);
    }
    else{
        throw new EntityNotFoundException("Categotia não Cadastrada");
    }
}
}
