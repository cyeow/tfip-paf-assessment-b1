package tfip.pafassessmentb1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.json.Json;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class Transfer {

    private String txId;

    @NotNull(message = "\"From\" account cannot be null.")
    @Valid
    private Account from;

    @NotNull(message = "\"To\" account cannot be null.")
    @Valid
    private Account to;

    // c3
    @NotNull(message = "Amount transferred cannot be null.")
    @Positive(message = "Amount transferred cannot be negative.")
    // c4
    @Min(value = 10, message = "Amount transferred has to be minimum $10.")
    private BigDecimal amount;
    private String comments;
    private LocalDate txDate;

    public Transfer() {
        from = new Account();
        to = new Account();
    }

    public Transfer(String txId, Account from, Account to, BigDecimal amount, String comments, LocalDate txDate) {
        this.txId = txId;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.comments = comments;
        this.txDate = txDate;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getTxDate() {
        return txDate;
    }

    public void setTxDate(LocalDate txDate) {
        this.txDate = txDate;
    }

    @Override
    public String toString() {
        return "Transfer [txId=" + txId + ", from=" + from + ", to=" + to + ", amount=" + amount
                + ", comments=" + comments + ", txDate=" + txDate + "]";
    }

    public String toJsonStringLog() {
        return Json.createObjectBuilder()
                .add("transactionId", getTxId())
                .add("date", getTxDate().toString())
                .add("from_account", getFrom().getAccountId())
                .add("to_account", getTo().getAccountId())
                .add("amount", getAmount())
                .build()
                .toString();
    }
}
