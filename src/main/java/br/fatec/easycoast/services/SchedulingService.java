package br.fatec.easycoast.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.scheduling.SchedulingRequest;
import br.fatec.easycoast.dtos.scheduling.SchedulingResponse;
import br.fatec.easycoast.entities.Customer;
import br.fatec.easycoast.entities.Scheduling;
import br.fatec.easycoast.mappers.SchedulingMapper;
import br.fatec.easycoast.repositories.SchedulingRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    public List<SchedulingResponse> getSchedules() {
        //If it is a customer
        if(verifyTheProfile()){
            //Just get their schedules
            return schedulingRepository.findByCustomerId(getTheUser().getId()).stream()
                    .map(SchedulingMapper::toDto)
                    .collect(Collectors.toList());
        } else { //If it is a Employee
            //Get all the schedules
            return schedulingRepository.findAll().stream()
                    .map(SchedulingMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    public SchedulingResponse getScheduling(Integer id) {
        Scheduling scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Scheduling not found!"));
        //Verify if the Scheduling is of the Customer
        if(verifyTheProfile() && !verifyTheUser(scheduling.getId())) throw new IllegalArgumentException("You can't GET this schedule!");
        return SchedulingMapper.toDto(scheduling);
    }

    public SchedulingResponse saveScheduling(SchedulingRequest request) {
        Scheduling scheduling = SchedulingMapper.toEntity(request);
        //Set the customer
        scheduling.setCustomer(getTheUser());

        //Verify if the seat is occupied in that time
        schedulingRepository
                .findByStartsAtAndSeatId(
                        scheduling.getStartsAt(),
                        scheduling.getSeat().getId())
                .ifPresent(s -> {
                    throw new IllegalStateException("The seat is occupied!");
                });
        
        //Verify if the customer is trying to save a schedule in the same time
        schedulingRepository
                .findByCustomerIdAndStartsAt(
                        scheduling.getCustomer().getId(),
                        scheduling.getStartsAt())
                .ifPresent(
                        s -> {
                            throw new IllegalStateException("The customer already scheduled at this time!");
                        });

        List<Scheduling> list = schedulingRepository.findByCustomerId(scheduling.getCustomer().getId());

        for (Scheduling schedule : list) {
            Instant startLimit = schedule.getStartsAt().minus(1, ChronoUnit.HOURS);
            Instant endLimit = schedule.getStartsAt().plus(1, ChronoUnit.HOURS);
            if (!scheduling.getStartsAt().isBefore(startLimit) && !scheduling.getStartsAt().isAfter(endLimit)) {
                throw new IllegalStateException("The Scheduling couldn't be made due to the limit!");
            }
        }

        return SchedulingMapper.toDto(schedulingRepository.save(scheduling));
    }

    public SchedulingResponse updateScheduling(Integer id, SchedulingRequest request) {
        if(!schedulingRepository.existsById(id)) throw new EntityNotFoundException("Scheduling not found!");
        
        Scheduling scheduling = schedulingRepository.getReferenceById(id);

        if(!verifyTheUser(scheduling.getCustomer().getId())) throw new IllegalArgumentException("You can't PUT this scheduling!");

        schedulingRepository
                .findByStartsAtAndSeatId(
                        request.startsAt(),
                        request.seat().getId())
                .ifPresent(s -> {
                    throw new IllegalStateException("The seat is occupied!");
                });

        List<Scheduling> list = schedulingRepository.findByCustomerId(getTheUser().getId());

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
        scheduling.setSeat(request.seat());

        Scheduling saved = schedulingRepository.save(scheduling);

        return SchedulingMapper.toDto(saved);
    }

    //Get the user of the request
    private Customer getTheUser(){
        //Get the authentication of the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Verify if the Authentication is correct
        if(auth != null && auth.getPrincipal() instanceof UserDetails){
            //Get the User
            return (Customer) auth.getPrincipal();
        } else {
            return null;
        }
    }

    private boolean verifyTheUser(int id){
        //Get the authentication of the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Verify if the Authentication is correct
        if(auth != null && auth.getPrincipal() instanceof UserDetails){
            //Get the User
            Customer customer = (Customer) auth.getPrincipal();
            //Check the id
            return id == customer.getId();
        } else {
            return false;
        }
    }

    private boolean verifyTheProfile(){
        //Get the authentication of the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Verify if the Authentication is correct
        if(auth != null && auth.getPrincipal() instanceof UserDetails){
            //Get the User
            UserDetails user = (UserDetails) auth.getPrincipal();
            //Check if it is a customer
            return user.getAuthorities().equals(List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        } else {
            return false;
        }
    }
}
