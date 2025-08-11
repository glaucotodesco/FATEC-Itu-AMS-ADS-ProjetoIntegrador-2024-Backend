package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.checkout.CheckoutRequest;
import br.fatec.easycoast.dtos.checkout.CheckoutResponse;
import br.fatec.easycoast.dtos.employee.EmployeeResponse;
import br.fatec.easycoast.entities.Checkout;
import br.fatec.easycoast.entities.Employee;

public class CheckoutMapper {

    public static Checkout toEntity(CheckoutRequest request, Employee employee) {
        Checkout checkout = new Checkout();
        checkout.setOpeningDate(request.openingDate());
        checkout.setClosingDate(request.closingDate());
        checkout.setEntryAmount(request.entryAmount());
        checkout.setExitAmount(request.exitAmount());
        checkout.setEmployee(employee);
        return checkout;
    }

    public static CheckoutResponse toResponse(Checkout checkout) {
        return new CheckoutResponse(
            checkout.getId(),
            checkout.getOpeningDate(),
            checkout.getClosingDate(),
            checkout.getEntryAmount(),
            checkout.getExitAmount(),
            EmployeeMapper.toDto(checkout.getEmployee())
        );
    }
}
