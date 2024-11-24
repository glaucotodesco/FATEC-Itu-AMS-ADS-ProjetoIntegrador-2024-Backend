package br.fatec.easycoast.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.fatec.easycoast.dtos.SquareItemsOnly;

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



    public SquareItemsOnly getSquare(int id) {
        return squareRepository.findById(id)
        .map(s -> SquareMapper.toDtoItemsOnly(s))
        .orElseThrow(() -> new EntityNotFoundException("Square not found!"));
    }


    public  List<SquareItemsOnly> getSquares() {
        return squareRepository.findAll()
        .stream()
        .map(s -> SquareMapper.toDtoItemsOnly(s))
        .collect(Collectors.toList());

        
    }


    public SquareResponse saveSquare(SquareRequest request) {
        Square square = squareRepository.save(SquareMapper.toEntity(request));
        return SquareMapper.toDtoResponse(square);
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
