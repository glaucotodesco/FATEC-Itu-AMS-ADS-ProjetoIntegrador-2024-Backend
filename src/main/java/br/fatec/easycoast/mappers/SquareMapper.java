package br.fatec.easycoast.mappers;


import br.fatec.easycoast.dtos.ItemFilter;
import br.fatec.easycoast.dtos.SquareItemsOnly;
import br.fatec.easycoast.dtos.SquareFilter;
import br.fatec.easycoast.dtos.SquareRequest;
import br.fatec.easycoast.dtos.SquareResponse;
import br.fatec.easycoast.entities.Item;
import br.fatec.easycoast.entities.Square;

public class SquareMapper {
    public static Square toEntity(SquareRequest request){
        Square square = new Square();
        square.setName(request.name());
        square.setItems(request.items());

        return square;
    }


    public static SquareResponse toDtoResponse(Square square){
        SquareResponse squareResponse = new SquareResponse(square.getId(), square.getName(), square.getItemsFilter());

        return squareResponse;
    }

    public static SquareFilter toDtoFilter(Square square){
        

        SquareFilter squareFilter = new SquareFilter(square.getId(), square.getName());
        return squareFilter;
    }

    public static SquareItemsOnly toDtoItemsOnly(Square square){
        SquareItemsOnly itemsOnly = new SquareItemsOnly(square.getId(), square.getName(), square.getItemsOnly());
        return itemsOnly;
    }
}
