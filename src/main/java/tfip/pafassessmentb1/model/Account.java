package tfip.pafassessmentb1.model;

import java.math.BigDecimal;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.validation.constraints.Size;

public class Account {

    // c1
    @Size(min=10, max=10, message="Account id must have only 10 characters.")
    private String accountId;
    private String name;
    private BigDecimal balance;

    public Account() {
    }

    public Account(String accountId, String name, BigDecimal balance) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", name=" + name + ", balance=" + balance + "]";
    }

    public static Account create(SqlRowSet rs) {
        try {
            return new Account(
                    rs.getString("account_id"),
                    rs.getString("name"),
                    rs.getBigDecimal("balance"));
        } catch (InvalidResultSetAccessException e) {
            return new Account(
                    rs.getString("account_id"),
                    rs.getString("name"),
                    null);
        }
    }

}
