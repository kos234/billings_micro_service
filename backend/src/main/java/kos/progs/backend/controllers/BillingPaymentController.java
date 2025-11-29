package kos.progs.backend.controllers;

import kos.progs.backend.consts.BillingStates;
import kos.progs.backend.entity.Billing;
import kos.progs.backend.entity.Payment;
import kos.progs.backend.model.Result;
import kos.progs.backend.service.BillingsService;
import kos.progs.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BillingPaymentController {

    private final BillingsService billingsService;
    private final PaymentService paymentService;


    @GetMapping("/accounts/{userId}/invoices")
    public ResponseEntity<?> getInvoices(
            @PathVariable int userId,
            @RequestParam(required = false) List<Integer> serviceId,
            @RequestParam(required = false) List<String> states) {

        List<BillingStates> billingStates = (states == null) ? List.of() :
                states.stream()
                        .map(String::toUpperCase)
                        .map(BillingStates::valueOf)
                        .collect(Collectors.toList());

        List<Billing> billings = billingsService.getBillings(userId, serviceId, billingStates);
        return ResponseEntity.ok(billings);
    }


    @PostMapping("/payments/refill")
    public ResponseEntity<?> refillBalance(@RequestBody Map<String, Object> payload) {
        try {
            int userId = ((Number) payload.get("userId")).intValue();
            BigDecimal amount = new BigDecimal(payload.get("amount").toString());

            Result<Payment> result = paymentService.addRefillBalance(userId, amount);

            if (result.isFailure()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", ((Result.Failure<?>) result).code(),
                        "message", ((Result.Failure<?>) result).message()
                ));
            }

            return ResponseEntity.ok(Map.of("paymentUuid", result.get().getUuid().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "INVALID_REQUEST",
                    "message", "Ошибка в формате запроса: " + e.getMessage()
            ));
        }
    }


    @PostMapping("/payments/pay")
    public ResponseEntity<?> payBilling(@RequestBody Map<String, Object> payload) {
        try {
            int userId = ((Number) payload.get("userId")).intValue();
            UUID billingUuid = UUID.fromString(payload.get("billingUuid").toString());

            Result<Payment> result = paymentService.addPayBilling(userId, billingUuid);

            if (result.isFailure()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", ((Result.Failure<?>) result).code(),
                        "message", ((Result.Failure<?>) result).message()
                ));
            }

            return ResponseEntity.ok(Map.of("paymentUuid", result.get().getUuid().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "INVALID_REQUEST",
                    "message", "Ошибка в формате запроса: " + e.getMessage()
            ));
        }
    }


 //   @PostMapping("/payments/webhook/success")
 //   public ResponseEntity<?> handleWebhook(@RequestBody Map<String, Object> payload) {
 //       // А что здесь я не знаваю
 //   }

    @GetMapping("/payments")
    public ResponseEntity<?> getPayments(
            @RequestParam int userId,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Long after) {

        Instant beforeInstant = (before != null) ? Instant.ofEpochMilli(before) : Instant.now();
        Instant afterInstant = (after != null) ? Instant.ofEpochMilli(after) : Instant.EPOCH;

        List<Payment> payments = paymentService.getPayments(userId, beforeInstant, afterInstant);
        return ResponseEntity.ok(payments);
    }
}