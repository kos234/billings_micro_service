package kos.progs.backend.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BillingStates {
    NOT_PAID("Не оплачено"),
    PAID("Оплачено")
    ;
    private final String title;
}
