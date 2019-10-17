package py.com.global.spm.GUISERVICE.dto.redis;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash("Captcha")
public class Captcha {
    @Id
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
