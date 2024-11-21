package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.SquareRequest;
import br.fatec.easycoast.dtos.SquareResponse;
import br.fatec.easycoast.entities.Square;

public class SquareMapper {
    public static Square toEntity(SquareRequest request){
        Square square = new Square();
        square.setName(request.Name());

        return square;
    }

    public static SquareResponse toDto(Square square){
        return new SquareResponse(square.getId(), square.getName());
    }
}
