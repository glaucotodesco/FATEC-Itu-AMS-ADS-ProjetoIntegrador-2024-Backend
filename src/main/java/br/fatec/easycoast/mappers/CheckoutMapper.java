package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.checkout.CheckoutRequest;
import br.fatec.easycoast.dtos.checkout.CheckoutResponse;
import br.fatec.easycoast.entities.Checkout;

public class CheckoutMapper {

    public static Checkout toEntity(CheckoutRequest request) {
        Checkout checkout = new Checkout();
        checkout.setOpeningDate(request.openingDate());
        checkout.setClosingDate(request.closingDate());
        checkout.setEntryAmount(request.entryAmount());
        if (request.exitAmount() != null) {
            checkout.setExitAmount(request.exitAmount());
        }
        if (request.changeAmount() != null) {
            checkout.setChangeAmount(request.changeAmount());
        }
        checkout.setEmployee(request.employee());
        return checkout;
    }

    public static CheckoutResponse toResponse(Checkout checkout) {
        return new CheckoutResponse(
            checkout.getId(),
            checkout.getOpeningDate(),
            checkout.getClosingDate(),
            checkout.getEntryAmount(),
            checkout.getExitAmount(),
            checkout.getChangeAmount(),
            EmployeeMapper.toDto(checkout.getEmployee())
        );
    }
}
