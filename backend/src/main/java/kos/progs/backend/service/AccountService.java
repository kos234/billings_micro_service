package kos.progs.backend.service;

import kos.progs.backend.entity.Account;
import kos.progs.backend.model.Result;
import kos.progs.backend.repository.JPAAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final JPAAccountRepository jpaAccountRepository;

    public Result<Account> getAccountByUUID(UUID uuid) {
        var wrapper = jpaAccountRepository.findById(uuid);
        if (wrapper.isEmpty())
            return Result.failure(AccountErrors.ACCOUNT_NOT_FOUND.name(), "Не найден аккаунт по UUID="+uuid);
        return Result.success(wrapper.get());
    }

    public Result<Account> getAccountByUserId(int userId) {
        var wrapper = jpaAccountRepository.findByUserId(userId);
        if (wrapper.isEmpty())
            return Result.failure(AccountErrors.ACCOUNT_NOT_FOUND.name(), "Не найден аккаунт по id="+userId);
        return Result.success(wrapper.get());
    }

    public Result<Void> appendBalance(UUID accoundUUID, BigDecimal amount) {
        return appendBalance(getAccountByUUID(accoundUUID), amount);
    }

    public Result<Void> appendBalance(int userId, BigDecimal amount) {
        return appendBalance(getAccountByUserId(userId), amount);
    }

    private Result<Void> appendBalance(Result<Account> accountWrapper, BigDecimal amount) {
        if (accountWrapper.isFailure())
            return Result.failure(((Result.Failure<Account>) accountWrapper).code(), ((Result.Failure<Account>) accountWrapper).message());

        Account account = accountWrapper.get();
        var newBalance = account.getBalance().add(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0)
            return Result.failure(AccountErrors.NEGATIVE_BALANCE.name(), "Операция вызовет отрицательный баланс, баланс = " + account.getBalance() + ", amount = " + amount);

        account.setBalance(newBalance);
        jpaAccountRepository.save(account);
        return Result.success(null);
    }


    public enum AccountErrors {
        ACCOUNT_NOT_FOUND,
        NEGATIVE_BALANCE
    }
}
