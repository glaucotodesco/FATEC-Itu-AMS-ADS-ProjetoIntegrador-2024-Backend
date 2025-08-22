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

import br.fatec.easycoast.dtos.employee.EmployeeRequest;
import br.fatec.easycoast.dtos.employee.EmployeeResponse;
import br.fatec.easycoast.services.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse employeeResponse = employeeService.saveEmployee(employeeRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employeeResponse.id())
                .toUri();
        return ResponseEntity.created(location).body(employeeResponse);
    }

    // Endpoint para listar todos os usuários
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        List<EmployeeResponse> employees = employeeService.getEmployees();
        return ResponseEntity.ok(employees);
    }

    // Endpoint para resgatar um usuário por ID
    @GetMapping("{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Integer id) {
        EmployeeResponse employeeResponse = employeeService.getEmployee(id);
        if (employeeResponse != null) {
            return ResponseEntity.ok(employeeResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable int id, @RequestBody EmployeeRequest request) {
        employeeService.updateEmployee(id, request);
        return ResponseEntity.ok().build();
    }
}
