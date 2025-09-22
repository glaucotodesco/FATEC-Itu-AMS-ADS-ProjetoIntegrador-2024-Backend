package br.fatec.easycoast.dtos.payment;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

// DTO para a requisição do endpoint POST /orders/{id}/pay
public record ProcessPaymentRequest(
    @NotEmpty
    @Valid
    List<PaymentPart> payments
) {
    // Inner record para representar cada "pedaço" do pagamento
    public record PaymentPart(
        PaymentMethod method,
        Double amount
    ) {}
}

