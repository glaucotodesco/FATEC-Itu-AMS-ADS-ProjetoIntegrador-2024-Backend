package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.SquareRequest;
import br.fatec.easycoast.dtos.SquareResponse;
import br.fatec.easycoast.entities.Square;
import br.fatec.easycoast.mappers.SquareMapper;
import br.fatec.easycoast.repositories.SquareRepository;

@Service
public class SquareService {
    @Autowired
    SquareRepository squareRepository;

    public SquareResponse saveSquare(SquareRequest request){
        Square square = squareRepository.save(SquareMapper.toEntity(request));
        return SquareMapper.toDto(square);
    }

    public void updateSquare(int id, SquareRequest request){
        Square square = squareRepository.getReferenceById(id);

        square.setName(request.Name());

        squareRepository.save(square);
    }
}
