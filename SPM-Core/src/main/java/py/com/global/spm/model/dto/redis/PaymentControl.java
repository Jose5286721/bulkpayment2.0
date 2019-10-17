package py.com.global.spm.model.dto.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("PaymentControl")
public class PaymentControl implements Serializable {
    private static final long serialVersionUID = -77469808963862433L;

    @Id
    long coreProcessId;
    int maxQueueLength;
    int available;

    public long getCoreProcessId() {
        return coreProcessId;
    }

    public void setCoreProcessId(long coreProcessId) {
        this.coreProcessId = coreProcessId;
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }

    public void setMaxQueueLength(int maxQueueLength) {
        this.maxQueueLength = maxQueueLength;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "PaymentControl{" +
                "coreProcessId=" + coreProcessId +
                ", maxQueueLength=" + maxQueueLength +
                ", available=" + available +
                '}';
    }
}
