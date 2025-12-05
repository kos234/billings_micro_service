package kos.progs.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class PageInfoDTO {
    private BigDecimal balance;
    private BigDecimal debt;
}
