package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.SquareItems;
import br.fatec.easycoast.dtos.SquareRequest;
import br.fatec.easycoast.dtos.SquareResponse;
import br.fatec.easycoast.entities.Square;

public class SquareMapper {
    public static Square toEntity(SquareRequest request){
        Square square = new Square();
        square.setName(request.name());

        return square;
    }

    public static SquareResponse toDto(Square square){
        SquareResponse squareResponse = new SquareResponse(square.getId(), square.getName());
        return squareResponse;
    }

    public static SquareItems toDtoItems(Square square){
        SquareItems squareItems = new SquareItems(square.getId(), square.getName(), square.getItems());
        return squareItems;
    }
}
