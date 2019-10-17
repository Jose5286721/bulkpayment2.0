package py.com.global.spm.GUISERVICE.dto.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("UserPinLogin")
public class UserPinLogin {

    @Id
    private Long iduserPk;
    private String pinLoginChr;
    private LocalDateTime pinLoginValidateTim;
    private Integer pinLoginAttemptNum;

    public UserPinLogin() {
    }

    public UserPinLogin(Long iduserPk, String pinLoginChr, LocalDateTime pinLoginValidateTim, Integer pinLoginAttemptNum) {
        this.iduserPk = iduserPk;
        this.pinLoginChr = pinLoginChr;
        this.pinLoginValidateTim = pinLoginValidateTim;
        this.pinLoginAttemptNum = pinLoginAttemptNum;
    }

    public Long getIduserPk() {
        return iduserPk;
    }

    public void setIduserPk(Long iduserPk) {
        this.iduserPk = iduserPk;
    }

    public String getPinLoginChr() {
        return pinLoginChr;
    }

    public void setPinLoginChr(String pinLoginChr) {
        this.pinLoginChr = pinLoginChr;
    }

    public LocalDateTime getPinLoginValidateTim() {
        return pinLoginValidateTim;
    }

    public void setPinLoginValidateTim(LocalDateTime pinLoginValidateTim) {
        this.pinLoginValidateTim = pinLoginValidateTim;
    }

    public Integer getPinLoginAttemptNum() {
        return pinLoginAttemptNum;
    }

    public void setPinLoginAttemptNum(Integer pinLoginAttemptNum) {
        this.pinLoginAttemptNum = pinLoginAttemptNum;
    }
}
