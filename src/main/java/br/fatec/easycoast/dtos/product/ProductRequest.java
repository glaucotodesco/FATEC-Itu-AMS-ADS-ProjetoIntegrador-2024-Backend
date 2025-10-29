package br.fatec.easycoast.dtos.product;

import java.util.List;

import br.fatec.easycoast.entities.AddonCategory;
import br.fatec.easycoast.entities.Subcategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequest(
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 3, message = "Minimum length for the name is 3 characters")
    String name,
    
    @NotBlank(message = "Description cannot be blank")
    String description,
    
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    Double price,
    
    @NotNull(message = "Discount cannot be null")
    @Min(value = 0, message = "Discount must be at least 0")
    @Max(value = 100, message = "Discount cannot be more than 100")
    Double discount,

    @NotNull(message = "Availability cannot be null")
    Boolean availability,

    @NotNull(message = "Subcategory cannot be null")
    Subcategory subcategory,

                List<AddonCategory> addonCategories,
                List<ProductItem> items

) {
}
