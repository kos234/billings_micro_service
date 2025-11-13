package kos.progs.backend.entity;

import jakarta.persistence.*;
import kos.progs.backend.consts.PaymentTypes;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    private UUID uuid;
    private int userId;
    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentTypes type;
    @OneToOne()
    @JoinColumn(name = "billing_uuid")
    private Billing billing;
    @Column(columnDefinition = "TIMESTAMPTZ")
    private Instant createdAt;
}
