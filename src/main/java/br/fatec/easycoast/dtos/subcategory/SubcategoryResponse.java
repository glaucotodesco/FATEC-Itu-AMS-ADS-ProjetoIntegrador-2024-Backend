package br.fatec.easycoast.dtos.subcategory;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.fatec.easycoast.dtos.category.CategoryResponse; // Importe o DTO de categoria
import br.fatec.easycoast.dtos.product.ProductResponse;   // Importe o DTO de produto

public record SubcategoryResponse(
    Integer id,
    String name,
    Boolean availability,

    // Ignora o campo "subcategories" dentro da categoria pai para evitar loop
    @JsonIgnoreProperties("subcategories")
    CategoryResponse category,

    // Ignora o campo "subcategory" dentro de cada produto para evitar loop
    @JsonIgnoreProperties("subcategory")
    List<ProductResponse> products
) {}