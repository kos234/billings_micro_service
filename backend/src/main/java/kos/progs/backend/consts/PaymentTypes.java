package kos.progs.backend.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentTypes {
    REFILL_ACCOUNT("Пополнение баланса аккаунта", false),
    PAY_BILLING("Оплата счёта", true)
    ;
    private final String title;
    private final boolean needBilling;
}
