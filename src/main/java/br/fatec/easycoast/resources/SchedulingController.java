package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.scheduling.SchedulingRequest;
import br.fatec.easycoast.dtos.scheduling.SchedulingResponse;
import br.fatec.easycoast.services.SchedulingService;

@RestController
@CrossOrigin
@RequestMapping("schedules")
public class SchedulingController {

    @Autowired
    private final SchedulingService schedulingService;

    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @GetMapping
    public ResponseEntity<List<SchedulingResponse>> getAll() {
        return ResponseEntity.ok(schedulingService.getSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulingResponse> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(schedulingService.getScheduling(id));
    }

    @PostMapping
    public ResponseEntity<SchedulingResponse> create(@RequestBody SchedulingRequest request) {
        SchedulingResponse scheduling = schedulingService.saveScheduling(request);

        URI location = ServletUriComponentsBuilder
                       .fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(scheduling.id()) 
                       .toUri();

        return ResponseEntity.created(location).body(scheduling);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody SchedulingRequest request) {
        schedulingService.updateScheduling(id, request);
        return ResponseEntity.ok().build();
    }
}
