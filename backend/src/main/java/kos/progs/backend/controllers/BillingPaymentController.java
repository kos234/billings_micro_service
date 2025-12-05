package kos.progs.backend.controllers;

import kos.progs.backend.consts.BillingStates;
import kos.progs.backend.dto.GetInvoicesDTO;
import kos.progs.backend.dto.GetPaymentsDTO;
import kos.progs.backend.dto.PageInfoDTO;
import kos.progs.backend.dto.QueryFromServerDTO;
import kos.progs.backend.entity.Billing;
import kos.progs.backend.entity.Payment;
import kos.progs.backend.entity.RegisteredService;
import kos.progs.backend.model.Result;
import kos.progs.backend.service.AccountService;
import kos.progs.backend.service.BillingsService;
import kos.progs.backend.service.PaymentService;
import kos.progs.backend.service.RegisterServicesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BillingPaymentController {
    private final AccountService accountService;
    private final BillingsService billingsService;
    private final PaymentService paymentService;
    private final RegisterServicesService registerServicesService;

    @PostMapping("/accounts/main")
    public ResponseEntity<?> getMainInfo(@AuthenticationPrincipal Jwt jwt) {
        int userId = Integer.parseInt(jwt.getSubject());

        var accountWrapper = accountService.getAccountByUserId(userId);
        if (accountWrapper.isFailure())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(accountWrapper.error().message());

        var debt = billingsService.getUserDebt(userId);

        var pageInfo = new PageInfoDTO();
        pageInfo.setDebt(debt);
        pageInfo.setBalance(accountWrapper.get().getBalance());
        return ResponseEntity.ok(pageInfo);
    }


    @GetMapping("/accounts/{userId}/invoices")
    @PostMapping("/accounts/{userId}/invoices")
    public ResponseEntity<?> getInvoices(@PathVariable int userId, QueryFromServerDTO<GetInvoicesDTO> queryFromServerDTO) {
        if(!registerServicesService.hasRegisteredServiceById(queryFromServerDTO.getServerId()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Сервис не зарегистрирован");

        var statesWrapper = billingsService.parseStatesFromString(queryFromServerDTO.getData().getStates());
        if (statesWrapper.isFailure())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(statesWrapper.error().message());

        List<Billing> billings = billingsService.getBillings(userId, queryFromServerDTO.getData().getServiceId(), statesWrapper.get());
        return ResponseEntity.ok(billings);
    }


    @PostMapping("/accounts/invoices")
    public ResponseEntity<?> getInvoices(@AuthenticationPrincipal Jwt jwt, GetInvoicesDTO getInvoicesDTO) {
        int userId = Integer.parseInt(jwt.getSubject());

        var statesWrapper = billingsService.parseStatesFromString(getInvoicesDTO.getStates());
        if (statesWrapper.isFailure())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(statesWrapper.error().message());


        List<Billing> billings = billingsService.getBillings(userId, getInvoicesDTO.getServiceId(), statesWrapper.get());
        return ResponseEntity.ok(billings);
    }

    @PostMapping("/payments/all")
    public ResponseEntity<?> getPayments(@AuthenticationPrincipal Jwt jwt, GetPaymentsDTO getPaymentsDTO) {
        int userId = Integer.parseInt(jwt.getSubject());

        List<Payment> payments = paymentService.getPayments(userId, getPaymentsDTO.getAfter(), getPaymentsDTO.getBefore());
        return ResponseEntity.ok(payments);
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
}