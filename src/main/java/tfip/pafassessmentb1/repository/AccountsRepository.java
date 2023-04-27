package tfip.pafassessmentb1.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import tfip.pafassessmentb1.model.Account;
import static tfip.pafassessmentb1.repository.DBQueries.*;

@Repository
public class AccountsRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Account> getAccountListWithoutBalance() {
        SqlRowSet rs = jdbc.queryForRowSet(SELECT_ALL_ACCOUNTS_WITHOUT_BALANCE);
        List<Account> accounts = new ArrayList<>();

        while(rs.next()) {
            accounts.add(Account.create(rs));
        }
        
        return accounts;
    }

    public Account getAccountById(String accountId) {
        SqlRowSet rs = jdbc.queryForRowSet(SELECT_ACCOUNT_BY_ID, accountId);
        if(rs.next()) {
            return Account.create(rs);
        }
        return null;
    }

    public void depositFunds(Account to, BigDecimal amount) {
        Integer rowsAffected = jdbc.update(FUND_DEPOSIT, amount, to.getAccountId());
        System.out.println("Deposit rows affected: " + rowsAffected);
    }

    public void withdrawFunds(Account from, BigDecimal amount) {
        Integer rowsAffected = jdbc.update(FUND_WITHDRAWAL, amount, from.getAccountId());
        System.out.println("Withdrawal rows affected: " + rowsAffected);
    }
    
}
