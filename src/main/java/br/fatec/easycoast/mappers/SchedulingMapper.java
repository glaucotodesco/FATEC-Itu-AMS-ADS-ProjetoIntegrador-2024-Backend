package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.scheduling.SchedulingRequest;
import br.fatec.easycoast.dtos.scheduling.SchedulingResponse;
import br.fatec.easycoast.entities.Scheduling;

public class SchedulingMapper {

    public static Scheduling toEntity(SchedulingRequest request) {
        Scheduling scheduling = new Scheduling();
        scheduling.setStartsAt(request.startsAt());
        scheduling.setQuantity(request.quantity());
        scheduling.setSeat(request.seat());
        scheduling.setObservations(request.observations());
        scheduling.setCustomer(request.customer());
        return scheduling;
    }

    public static SchedulingResponse toDto(Scheduling scheduling) {
        return new SchedulingResponse(
                scheduling.getId(),
                scheduling.getStartsAt(),
                scheduling.getQuantity(),
                scheduling.getObservations(),
                scheduling.getCustomer(),
                scheduling.getSeat());
    }
}
