package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.scheduling.SchedulingRequest;
import br.fatec.easycoast.dtos.scheduling.SchedulingResponse;
import br.fatec.easycoast.entities.Scheduling;
import br.fatec.easycoast.entities.Customer;

public class SchedulingMapper {

    public static Scheduling toEntity(SchedulingRequest request, Customer customer) {
        Scheduling scheduling = new Scheduling();

        scheduling.setStartsAt(request.startsAt());
        scheduling.setQuantity(request.quantity());
        scheduling.setCustomer(customer);

        return scheduling;
    }

    public static SchedulingResponse toDto(Scheduling scheduling) {
        return new SchedulingResponse(
            scheduling.getId(),
            scheduling.getStartsAt(),
            scheduling.getQuantity(),
            scheduling.getCustomer().getName()  
        );
    }
}

