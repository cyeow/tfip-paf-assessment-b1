package tfip.pafassessmentb1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import tfip.pafassessmentb1.model.Transfer;

@Repository
public class LogAuditRepository {
    
    @Autowired
    private RedisTemplate<String, String> redis;

    public void logTransaction(Transfer t) {
        redis.opsForValue().set(t.getTxId(), t.toJsonStringLog());
    }
}
