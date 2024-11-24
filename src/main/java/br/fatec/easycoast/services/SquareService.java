package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.SquareRequest;
import br.fatec.easycoast.dtos.SquareResponse;
import br.fatec.easycoast.entities.Square;
import br.fatec.easycoast.mappers.SquareMapper;
import br.fatec.easycoast.repositories.SquareRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SquareService {
    @Autowired
    SquareRepository squareRepository;

    public SquareResponse saveSquare(SquareRequest request) {
        Square square = squareRepository.save(SquareMapper.toEntity(request));
        return SquareMapper.toDto(square);
    }

    public void updateSquare(int id, SquareRequest request){
        try{
            Square square = squareRepository.getReferenceById(id);

            square.setName(request.name());

            squareRepository.save(square);
        } catch(EntityNotFoundException e) {
            throw new EntityNotFoundException("Square not found!");
        }
    }
}
