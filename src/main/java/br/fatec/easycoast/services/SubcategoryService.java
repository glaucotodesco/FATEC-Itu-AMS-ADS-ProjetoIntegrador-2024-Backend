package br.fatec.easycoast.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.subcategory.SubcategoryRequest;
import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse;
import br.fatec.easycoast.entities.Subcategory;
import br.fatec.easycoast.mappers.SubcategoryMapper;
import br.fatec.easycoast.repositories.SubcategoryRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public SubcategoryResponse getSubcategory(Integer id) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found subcategory!"));
        return SubcategoryMapper.toDto(subcategory);
    }

    public List<SubcategoryResponse> getSubcategories() {
        List<SubcategoryResponse> subcategories = subcategoryRepository.findAll().stream()
                .map(subcategory -> SubcategoryMapper.toDto(subcategory)).toList();
        return subcategories;
    }

    public SubcategoryResponse saveSubcategory(SubcategoryRequest request) {
        Subcategory subcategory = subcategoryRepository.save(SubcategoryMapper.toEntity(request));
        return SubcategoryMapper.toDto(subcategory);

    }

    public void deleteSubcategory(int id) {
        if (subcategoryRepository.existsById(id)) {
            subcategoryRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Subcategory not found!");
        }
    }

    public void updateSubcategory(SubcategoryRequest request, Integer id) {
        Subcategory subcategory = subcategoryRepository.getReferenceById(id);
        subcategory.setName(request.name());
        subcategory.setAvailability(request.availability());
        subcategory.setCategory(request.category());
        subcategoryRepository.save(subcategory);
    }

}
