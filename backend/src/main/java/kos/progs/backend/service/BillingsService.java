package kos.progs.backend.service;

import kos.progs.backend.consts.BillingStates;
import kos.progs.backend.entity.Billing;
import kos.progs.backend.model.BillingFilter;
import kos.progs.backend.model.Result;
import kos.progs.backend.repository.BillingSpecifications;
import kos.progs.backend.repository.JPABillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillingsService {
    private final JPABillingRepository jpaBillingRepository;
    private final RegisterServicesService registerServicesService;

    public Result<Billing> findById(UUID uuid) {
        var billingWrapper = jpaBillingRepository.findById(uuid);
        if(billingWrapper.isEmpty())
            return Result.failure(BillingsErrors.BILLING_NOT_FOUND.name(), "Счёт с айди " + uuid + " не найден");
        return Result.success(billingWrapper.get());
    }

    public Result<Billing> addBillingToAccount(int serviceId, BigDecimal amount, List<Integer> userIds){
        var serviceWrapper = registerServicesService.getRegisteredServiceById(serviceId);
        if(serviceWrapper.isEmpty())
            return Result.failure(BillingsErrors.UNKNOWN_SERVICE.name(), "Неизвестный сервис: " + serviceId);

        if(amount.compareTo(BigDecimal.ZERO) <= 0)
            return Result.failure(BillingsErrors.AMOUNT_LESS_OR_EQUAL_ZERO.name(), "Сумма счёта меньше или равна 0, amount = " + amount);

        if (userIds.isEmpty())
            return Result.failure(BillingsErrors.EMPTY_USER_LIST.name(), "Не передан список пользователей для выставления счёта");

        Billing billing = Billing.builder()
                .uuid(UUID.randomUUID())
                .registeredService(serviceWrapper.get())
                .amount(amount)
                .state(BillingStates.NOT_PAID)
                .createdAt(Instant.now())
                .userIds(userIds)
                .build();

        jpaBillingRepository.save(billing);
        return Result.success(billing);
    }

    public Result<Void> setBillingState(UUID uuid, BillingStates state){
        if(state == null)
            return Result.failure(BillingsErrors.BILLING_STATE_NULL.name(), "Не передан статус счёта");

        var billingWrapper = jpaBillingRepository.findById(uuid);
        if(billingWrapper.isEmpty())
            return Result.failure(BillingsErrors.BILLING_NOT_FOUND.name(), "Счёт с айди " + uuid + " не найден");


        var billing = billingWrapper.get();
        billing.setState(state);
        jpaBillingRepository.save(billing);
        return Result.success(null);
    }

    public Result<Boolean> canBillingPaid(UUID billingUUID, BigDecimal amount){
        var billingWrapper = jpaBillingRepository.findById(billingUUID);
        if(billingWrapper.isEmpty())
            return Result.failure(BillingsErrors.BILLING_NOT_FOUND.name(), "Счёт с айди " + billingUUID + " не найден");
        return canBillingPaid(billingWrapper.get(), amount);
    }

    public Result<Boolean> canBillingPaid(Billing billing, BigDecimal amount){
        return Result.success(billing.getAmount().compareTo(amount) <= 0);
    }

    public List<Billing> getBillings(int userId, List<Integer> serviceId, List<BillingStates> states){
        return jpaBillingRepository.findAll(BillingSpecifications.withFilters(userId, serviceId, states));
    }

    public enum BillingsErrors{
        UNKNOWN_SERVICE,
        AMOUNT_LESS_OR_EQUAL_ZERO,
        EMPTY_USER_LIST,
        BILLING_NOT_FOUND,
        BILLING_STATE_NULL,
    }
}
