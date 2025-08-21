package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.subcategory.SubcategoryRequest;
import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse;
import br.fatec.easycoast.entities.Subcategory;

public class SubcategoryMapper {

    public static Subcategory toEntity(SubcategoryRequest request) {
        Subcategory subcategory = new Subcategory();
        subcategory.setName(request.name());
        subcategory.setAvailability(request.availability());
        subcategory.setCategory(request.category());
        return subcategory;

    }

    public static SubcategoryResponse toDTO(Subcategory subcategory) {
        return new SubcategoryResponse(
                subcategory.getId(),
                subcategory.getName(),
                subcategory.getAvailability(),
                subcategory.getCategory(),
                subcategory.getProducts()

        );
    }

}
