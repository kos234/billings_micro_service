package kos.progs.backend.model;

import kos.progs.backend.consts.BillingStates;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingFilter {
    private Integer serviceId = null;
    private List<BillingStates> states = List.of();
}
