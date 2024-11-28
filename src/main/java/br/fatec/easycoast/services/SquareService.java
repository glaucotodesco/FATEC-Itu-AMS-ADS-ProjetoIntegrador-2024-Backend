package br.fatec.easycoast.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.SquareItems;
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

    public SquareItems getSquare(int id) {
        Square square = squareRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Square not found!"));
        return SquareMapper.toDtoItems(square);
    }

    public  List<SquareItems> getSquares() {
        return squareRepository.findAll()
                               .stream()
                               .map(s -> SquareMapper.toDtoItems(s))
                               .collect(Collectors.toList());
    }
  
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
