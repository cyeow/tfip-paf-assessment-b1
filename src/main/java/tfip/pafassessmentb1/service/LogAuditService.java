package tfip.pafassessmentb1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfip.pafassessmentb1.model.Transfer;
import tfip.pafassessmentb1.repository.LogAuditRepository;

@Service
public class LogAuditService {
    
    @Autowired
    private LogAuditRepository logRepo;

    public void logTransaction(Transfer t) {
        logRepo.logTransaction(t);
    }
}
