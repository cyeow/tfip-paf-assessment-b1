package tfip.pafassessmentb1.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfip.pafassessmentb1.model.Account;
import tfip.pafassessmentb1.repository.AccountsRepository;

@Service
public class AccountsService {

    @Autowired
    private AccountsRepository repo;

    public List<Account> getAccountList() {
        return null;
    }

    public List<Account> getAccountListWithoutBalance() {
        return repo.getAccountListWithoutBalance();
    }

    public boolean hasSufficientBalance(Account from, BigDecimal amount) {
        if (from.getBalance() == null) {
            from = getAccount(from.getAccountId());
        }

        // if balance is same as amount it will be accepted
        return from.getBalance().compareTo(amount) >= 0 ? true : false;
    }

    public boolean isValidAccount(Account acc) {
        return getAccount(acc.getAccountId()) != null ? true : false;
    }

    public Account getAccount(String accountId) {
        return repo.getAccountById(accountId);
    }


}
