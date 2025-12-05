package kos.progs.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class GetInvoicesDTO {
    private List<Integer> serviceId = List.of();
    private List<String> states = List.of();
}
