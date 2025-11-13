package kos.progs.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisteredService {
    @Id
    private int id;
    @Column(name = "title", length = 128)
    private String title;
}
