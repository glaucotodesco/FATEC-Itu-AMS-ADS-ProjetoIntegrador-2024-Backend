package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.square.SquareRequest;
import br.fatec.easycoast.dtos.square.SquareResponse;
import br.fatec.easycoast.entities.Square;

public class SquareMapper {
    public static Square toEntity(SquareRequest request) {
        Square square = new Square();
        square.setName(request.name());

        return square;
    }

    public static SquareResponse toDto(Square square) {
        SquareResponse squareResponse = new SquareResponse(square.getId(), square.getName(), square.getItems());
        return squareResponse;
    }

}
