package br.fatec.easycoast.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.scheduling.SchedulingRequest;
import br.fatec.easycoast.dtos.scheduling.SchedulingResponse;
import br.fatec.easycoast.entities.Customer;
import br.fatec.easycoast.entities.Scheduling;
import br.fatec.easycoast.mappers.SchedulingMapper;
import br.fatec.easycoast.repositories.CustomerRepository;
import br.fatec.easycoast.repositories.SchedulingRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;
    // private final SeatRepository seatRepository; 
    private final CustomerRepository customerRepository;

    public SchedulingService(
        SchedulingRepository schedulingRepository,
        /* SeatRepository seatRepository, */
        CustomerRepository customerRepository
    ) {
        this.schedulingRepository = schedulingRepository;
        // this.seatRepository = seatRepository; /
        this.customerRepository = customerRepository;
    }

    public List<SchedulingResponse> getSchedules() {
        return schedulingRepository.findAll().stream()
                .map(SchedulingMapper::toDto)
                .collect(Collectors.toList());
    }

    public SchedulingResponse getScheduling(Integer id) {
        Scheduling scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
        return SchedulingMapper.toDto(scheduling);
    }

    public SchedulingResponse saveScheduling(SchedulingRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Scheduling scheduling = SchedulingMapper.toEntity(request, customer);
        Scheduling saved = schedulingRepository.save(scheduling);
        return SchedulingMapper.toDto(saved);
    }

    public SchedulingResponse updateScheduling(Integer id, SchedulingRequest request) {
    Scheduling scheduling = schedulingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
    Customer customer = customerRepository.findById(request.customerId())
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    scheduling.setStartsAt(request.startsAt());  
    scheduling.setQuantity(request.quantity());  

    // Parte comentada, aguardando implementação do Seat
    // Seat seat = seatRepository.findById(request.getSeatId())
    //     .orElseThrow(() -> new EntityNotFoundException("Assento não encontrado"));

    // scheduling.setSeat(seat); // Comentado, aguardando implementação do Seat

    scheduling.setCustomer(customer);

    Scheduling saved = schedulingRepository.save(scheduling);
    
    return SchedulingMapper.toDto(saved);
}

}
