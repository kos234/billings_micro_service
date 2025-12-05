package kos.progs.backend.controllers;

import kos.progs.backend.consts.BillingStates;
import kos.progs.backend.consts.PaymentTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/constants")
public class ConstantsController {
    @PostMapping("/billing_states")
    public ResponseEntity<?> getBillingStates(){
        return ResponseEntity.ok(
                Arrays.stream(BillingStates.values())
                        .collect(Collectors.toMap(Enum::name, BillingStates::getTitle))
        );
    }
    @PostMapping("/payment_types")
    public ResponseEntity<?> getPaymentTypes(){
        return ResponseEntity.ok(
                Arrays.stream(PaymentTypes.values())
                        .collect(Collectors.toMap(Enum::name, PaymentTypes::getTitle))
        );
    }
}
