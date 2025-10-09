package br.fatec.easycoast.dtos.subcategory;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.fatec.easycoast.dtos.category.CategoryResponse; 
import br.fatec.easycoast.dtos.product.ProductResponse;   

public record SubcategoryResponse(
    Integer id,
    String name,
    Boolean availability,
    @JsonIgnoreProperties("subcategories")
    CategoryResponse category,
    @JsonIgnoreProperties("subcategory")
    List<ProductResponse> products
) {}