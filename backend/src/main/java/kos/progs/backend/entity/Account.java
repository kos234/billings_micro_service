package kos.progs.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account", indexes = {
        @Index(name = "unique_user_id", columnList = "user_id", unique = true)
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    private UUID id;
    private int userId;
    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance;
}
