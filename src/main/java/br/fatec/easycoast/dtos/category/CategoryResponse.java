package br.fatec.easycoast.dtos.category;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse; // Importe o DTO de subcategoria

public record CategoryResponse(
    Integer id,
    String name,
    Boolean availability,

    // Ao listar as subcategorias, ignoramos o campo "category" dentro de cada uma delas
    // para evitar o loop infinito: Category -> Subcategory -> Category
    @JsonIgnoreProperties("category")
    List<SubcategoryResponse> subcategories
) {}