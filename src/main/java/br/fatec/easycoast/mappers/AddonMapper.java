package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.AddonRequest;
import br.fatec.easycoast.dtos.AddonResponse;
import br.fatec.easycoast.entities.Addon;


public class AddonMapper {




    public static Addon toEntity(AddonRequest request) {
  
        Addon addon = new Addon();
        addon.setName(request.name());
        addon.setPrice(request.price());
        addon.setAvailability(request.avaliability());
        addon.setProduct(request.product());
        addon.setItem(request.item());
        return addon;
    }




    public static AddonResponse toDTO(Addon addon) {
   
        return new 
        AddonResponse(
            addon.getId(), 
            addon.getName(),  
            addon.getPrice(), 
            addon.getAvailability(), 
            ProductMapper.toDTO(addon.getProduct()), 
            ItemMapper.toDTO(addon.getItem())
        );

    }

  


    
}
