package kos.progs.backend.entity;

import jakarta.persistence.*;
import kos.progs.backend.consts.BillingStates;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
public class Billing {
    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private RegisteredService registeredService;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private BillingStates state;

    @Column(columnDefinition = "TIMESTAMPTZ")
    private Instant createdAt;

    @ElementCollection
    @CollectionTable(
            name = "billing_users",
            joinColumns = @JoinColumn(name = "billing_uuid")
    )
    @Column(name = "user_id")
    private List<Integer> userIds;
}
