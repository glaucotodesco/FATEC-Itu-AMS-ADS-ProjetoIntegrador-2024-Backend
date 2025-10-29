package br.fatec.easycoast.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.scheduling.SchedulingRequest;
import br.fatec.easycoast.dtos.scheduling.SchedulingResponse;
import br.fatec.easycoast.entities.Scheduling;
import br.fatec.easycoast.mappers.SchedulingMapper;
import br.fatec.easycoast.repositories.SchedulingRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    public List<SchedulingResponse> getSchedules() {
        return schedulingRepository.findAll().stream()
                .map(SchedulingMapper::toDto)
                .collect(Collectors.toList());
    }

    public SchedulingResponse getScheduling(Integer id) {
        Scheduling scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found!"));
        return SchedulingMapper.toDto(scheduling);
    }

    public SchedulingResponse saveScheduling(SchedulingRequest request) {

        schedulingRepository
                .findByStartsAtAndSeatId(
                        request.startsAt(),
                        request.seat().getId())
                .ifPresent(s -> {
                    throw new IllegalStateException("The seat is occupied!");
                });

        schedulingRepository
                .findByCustomerIdAndStartsAt(
                        request.customer().getId(),
                        request.startsAt())
                .ifPresent(
                        s -> {
                            throw new IllegalStateException("The customer already scheduled at this time!");
                        });

        List<Scheduling> list = schedulingRepository.findByCustomerId(request.customer().getId());

        for (Scheduling schedule : list) {
            Instant startLimit = schedule.getStartsAt().minus(1, ChronoUnit.HOURS);
            Instant endLimit = schedule.getStartsAt().plus(1, ChronoUnit.HOURS);
            if (!request.startsAt().isBefore(startLimit) && !request.startsAt().isAfter(endLimit)) {
                throw new IllegalStateException("Não pode ser feito a reserva devido ao limite!");
            }
        }

        return SchedulingMapper.toDto(schedulingRepository.save(SchedulingMapper.toEntity(request)));
    }

    public SchedulingResponse updateScheduling(Integer id, SchedulingRequest request) {
        if(!schedulingRepository.existsById(id)) throw new EntityNotFoundException("Scheduling not found!");
        
        Scheduling scheduling = schedulingRepository.getReferenceById(id);

        schedulingRepository
                .findByStartsAtAndSeatId(
                        request.startsAt(),
                        request.seat().getId())
                .ifPresent(s -> {
                    throw new IllegalStateException("The seat is occupied!");
                });

        List<Scheduling> list = schedulingRepository.findByCustomerId(request.customer().getId());

        for (Scheduling schedule : list) {
            Instant startLimit = schedule.getStartsAt().minus(1, ChronoUnit.HOURS);
            Instant endLimit = schedule.getStartsAt().plus(1, ChronoUnit.HOURS);
            if (!request.startsAt().isBefore(startLimit) && !request.startsAt().isAfter(endLimit)
                    && schedule.getId() != id) {
                throw new IllegalStateException("The Scheduling couldn't be made due to the limit!");
            }
        }

        scheduling.setStartsAt(request.startsAt());
        scheduling.setQuantity(request.quantity());
        scheduling.setCustomer(request.customer());
        scheduling.setSeat(request.seat());

        Scheduling saved = schedulingRepository.save(scheduling);

        return SchedulingMapper.toDto(saved);
    }

}
