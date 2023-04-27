package tfip.pafassessmentb1.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tfip.pafassessmentb1.model.Transfer;
import tfip.pafassessmentb1.repository.AccountsRepository;

@Service
public class FundsTransferService {

    @Autowired
    private AccountsService accSvc;

    @Autowired
    private LogAuditService logSvc;

    @Autowired
    private AccountsRepository accRepo;
    
    @Transactional(rollbackFor=DataAccessException.class)
    public void transferFunds(Transfer t) throws DataAccessException {
        // update account details
        t.setFrom(accSvc.getAccount(t.getFrom().getAccountId()));
        t.setTo(accSvc.getAccount(t.getTo().getAccountId()));

        // execute transaction
        accRepo.withdrawFunds(t.getFrom(), t.getAmount());
        accRepo.depositFunds(t.getTo(), t.getAmount());

        // set transaction details
        t.setTxId(generateTxId());
        t.setTxDate(LocalDate.now());

        // logging
        logSvc.logTransaction(t);
    }

    private String generateTxId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0,8);
    }
}
