package kos.progs.backend.service;

import kos.progs.backend.consts.BillingStates;
import kos.progs.backend.consts.PaymentTypes;
import kos.progs.backend.entity.Account;
import kos.progs.backend.entity.Billing;
import kos.progs.backend.entity.Payment;
import kos.progs.backend.model.Result;
import kos.progs.backend.repository.JPAPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final BillingsService billingsService;
    private final AccountService accountService;
    private final JPAPaymentRepository jpaPaymentRepository;

    @Transactional
    public Result<Payment> addRefillBalance(int userId, BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) <= 0)
            return Result.failure(AccountErrors.AMOUNT_LESS_OR_EQUAL_ZERO.name(), "amount меньше либо равно 0");
        Payment payment = Payment.builder()
                .uuid(UUID.randomUUID())
                .userId(userId)
                .amount(amount)
                .type(PaymentTypes.REFILL_ACCOUNT)
                .createdAt(Instant.now())
                .build();
        jpaPaymentRepository.save(payment);
        accountService.appendBalance(userId, amount);
        return Result.success(payment);
    }

    @Transactional
    public Result<Payment> addPayBilling(int userId, UUID billingUUID){
        var accountResult = accountService.getAccountByUserId(userId);
        if(accountResult.isFailure())
            return Result.failure(((Result.Failure<Account>)accountResult).code(), ((Result.Failure<Account>)accountResult).message());

        BigDecimal balance = accountResult.get().getBalance();

        if(balance.compareTo(BigDecimal.ZERO) <= 0)
            return Result.failure(AccountErrors.AMOUNT_LESS_OR_EQUAL_ZERO.name(), "amount меньше либо равно 0");

        Result<Billing> billingWrapper = billingsService.findById(billingUUID);
        if(billingWrapper.isFailure())
            return Result.failure(((Result.Failure<Billing>)billingWrapper).code(), ((Result.Failure<Billing>)billingWrapper).message());


        Result<Boolean> canPaid = billingsService.canBillingPaid(billingWrapper.get(), balance);
        if(canPaid.isFailure())
            return Result.failure(((Result.Failure<Boolean>)canPaid).code(), ((Result.Failure<Boolean>)canPaid).message());

        var amount = billingWrapper.get().getAmount().multiply(BigDecimal.valueOf(-1));
        Payment payment = Payment.builder()
                .uuid(UUID.randomUUID())
                .userId(userId)
                .amount(amount)
                .type(PaymentTypes.PAY_BILLING)
                .billing(billingWrapper.get())
                .createdAt(Instant.now())
                .build();

        jpaPaymentRepository.save(payment);
        accountService.appendBalance(userId, amount);
        billingsService.setBillingState(billingUUID, BillingStates.PAID);
        return Result.success(payment);
    }

    public List<Payment> getPayments(int userId, Instant before, Instant after){
        return jpaPaymentRepository.findAllByUserIdAndCreatedAtAfterAndCreatedAtBefore(userId, before, after);
    }

    public enum AccountErrors{
        AMOUNT_LESS_OR_EQUAL_ZERO
    }
}
