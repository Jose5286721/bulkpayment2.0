package py.com.global.spm.GUISERVICE.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CachingConfiguration extends CachingConfigurerSupport {

    @Value("${caffeine.cache.expiration.in-seconds}")
    private Integer expiration;

    @Value("${caffeine.cache.maximum-size}")
    private Integer maximumSize;

    @Bean
    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(expiration, TimeUnit.SECONDS).maximumSize(maximumSize));
        return cacheManager;
    }
}
