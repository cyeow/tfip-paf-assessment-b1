package tfip.pafassessmentb1.repository;

public class DBQueries {
    
    public static final String SELECT_ALL_ACCOUNTS_WITHOUT_BALANCE = "SELECT account_id, name FROM accounts;";
    public static final String SELECT_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE account_id = ?;"; 

    public static final String FUND_WITHDRAWAL = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?;";
    public static final String FUND_DEPOSIT = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?;";
}
