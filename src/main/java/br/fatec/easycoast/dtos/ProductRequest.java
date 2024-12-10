package br.fatec.easycoast.dtos;

import java.util.List;

import br.fatec.easycoast.entities.Addon;
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
  @Size(min = 10, message = "Minimum length for the description is 10 characters")
  String description,

  @NotNull(message = "Price cannot be null")
  @Positive(message = "Price must be positive")
  float price,

  @NotNull(message = "Discount cannot be null")
  @Min(value = 0, message = "Discount must be at least 0")
  @Max(value = 100, message = "Discount cannot be more than 100")
  float discount,

  @NotNull(message = "Availability cannot be null")
  Boolean availability,

  @NotBlank(message = "Category cannot be blank")
  String category,

  @NotBlank(message = "Image URL cannot be blank")
  String imageurl

  // List<Addon> addon
) { }
