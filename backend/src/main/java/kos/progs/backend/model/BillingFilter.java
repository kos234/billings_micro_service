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
    @Builder.Default
    private Integer serviceId = null;
    @Builder.Default
    private List<BillingStates> states = List.of();
}
