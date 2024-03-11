package org.payments.request;

public record PaymentRequest(
        String method,
        String amount,
        String currency,
        String description
) {
}
