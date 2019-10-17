package py.com.global.spm.model.config;



import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.web.client.RestTemplate;
import py.com.global.spm.model.dto.redis.PaymentOrderCache;
import py.com.global.spm.model.services.SystemParameterService;
import py.com.global.spm.model.systemparameters.GeneralParameters;
import py.com.global.spm.model.systemparameters.OPManagerParameters;
import py.com.global.spm.model.util.SpmProcesses;


import javax.jms.ConnectionFactory;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Arrays;


@Configuration
@EntityScan("py.com.global.spm.domain.entity")
@ComponentScan(basePackages = {"py.com.global.spm.domain","py.com.global.spm.model"})
@EnableCaching
@EnableRedisRepositories(basePackages = "py.com.global.spm.model.repository.redis")
public class AppConfiguration {
    private static final Logger log = LoggerFactory
            .getLogger(AppConfiguration.class);

    @Autowired
    SystemParameterService systemParameterService;

    @Value("${spring.redis.cluster.nodes}")
    String redisClusterNodes;


    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(t -> log.error("Error in JMS listener: {}", t.getMessage()));
        factory.setConcurrency(systemParameterService.
                getParameterValue(GeneralParameters.JMS_LISTENER_CONCURRENCY.getIdProcess(),
                        GeneralParameters.JMS_LISTENER_CONCURRENCY.getParameterName(), "3"));
        // You could still override some of Boot's default if necessary.
        return factory;
    }


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = null;
        try {
            String[] nodes = redisClusterNodes.split(";");
            int redisPort = 6379;
            String redisHost = "127.0.0.1";
            //Standalone Config
            if (nodes.length != 0) {
                if (nodes.length == 1) {
                    //Configure the Pool
                    connectionFactory = new JedisConnectionFactory();
                    String[] standalone = nodes[0].split(":");
                    if(connectionFactory.getPoolConfig()!=null) {
                        connectionFactory.getPoolConfig().setMinIdle(Integer.valueOf(systemParameterService.
                                getParameterValue(GeneralParameters.MEMORY_DATABASE_POOL_MIN_IDLE_CONNECTIONS.getIdProcess(),
                                        GeneralParameters.MEMORY_DATABASE_POOL_MIN_IDLE_CONNECTIONS.getParameterName(), "5")));
                        connectionFactory.getPoolConfig().setMaxIdle(Integer.valueOf(systemParameterService.
                                getParameterValue(GeneralParameters.MEMORY_DATABASE_POOL_MAX_IDLE_CONNECTIONS.getIdProcess(),
                                        GeneralParameters.MEMORY_DATABASE_POOL_MAX_IDLE_CONNECTIONS.getParameterName(), "33")));
                        connectionFactory.getPoolConfig().setMaxTotal(Integer.valueOf(systemParameterService.
                                getParameterValue(GeneralParameters.MEMORY_DATABASE_POOL_MAX_TOTAL_CONNECTONS.getIdProcess(),
                                        GeneralParameters.MEMORY_DATABASE_POOL_MAX_TOTAL_CONNECTONS.getParameterName(), "100")));
                        log.info("Redis Database Pool Min Idle Connections: {}", connectionFactory.getPoolConfig().getMinIdle());
                        log.info("Redis Database Pool Max Idle Connections: {} ", connectionFactory.getPoolConfig().getMaxIdle());
                        log.info("Redis Database Pool Max Total Connections: {}", connectionFactory.getPoolConfig().getMaxTotal());
                    }

                    try {
                        if (standalone.length == 2) {
                            redisHost = standalone[0];
                            redisPort = Integer.valueOf(standalone[1]);
                        }
                    } catch (Exception e) {
                        log.error("Error al parsear el Puerto Redis :  ", e);
                    } finally {
                        if (connectionFactory.getStandaloneConfiguration() != null) {
                            connectionFactory.getStandaloneConfiguration().setHostName(redisHost);
                            connectionFactory.getStandaloneConfiguration().setPort(redisPort);
                        }
                        log.info("Redis Host: {}", connectionFactory.getHostName());
                        log.info("Redis Port: {}", connectionFactory.getPort());
                    }

                } else {
                    RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(Arrays.asList(nodes));
                    connectionFactory = new JedisConnectionFactory(clusterConfiguration);
                    if (connectionFactory.getClusterConfiguration() != null)
                        log.info("Redis Cluster Nodes: {}", connectionFactory.getClusterConfiguration().getClusterNodes());
                }
            } else {
                log.error("Redis Error, no se especifica ningun host:port en la configuracion ");
            }
        } catch (Exception e) {
            log.error("Error in JRedis Connection Factory", e);
        }
        return connectionFactory;
    }

    @Bean(name = "SimpleRestTemplate")
    public RestTemplate restTemplate() {
        log.info("Creando Singleton RestTemplate, para conexiones HTTP... ");
        return new RestTemplate(getClientHttpRequestFactory());
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int conectTimeout = Integer.parseInt(systemParameterService.getParameterValue(SpmProcesses.SPM_GENERAL,
                GeneralParameters.CONNECT_TIMEOUT_MS.name(), "2000"));
        int readTimeout = Integer.parseInt(systemParameterService.getParameterValue(SpmProcesses.SPM_GENERAL,
                GeneralParameters.CONNECT_READ_TIMEOUT_MS.name(), "60000"));
        int maxConnection = Integer.parseInt(systemParameterService.getParameterValue(SpmProcesses.SPM_GENERAL,
                GeneralParameters.MAX_CONNECTIONS.name(), "33"));
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setDefaultMaxPerRoute(maxConnection);
        connManager.setMaxTotal(maxConnection);
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(client);
        clientHttpRequestFactory.setConnectTimeout(conectTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        log.info("HTTP CONNECT_TIMEOUT: {} ms" , conectTimeout);
        log.info("HTTP READ_TIMEOUT: {} ms", readTimeout);
        log.info("HTTP MAX_CONNECTIONS: {}" ,connManager.getMaxTotal());
        return clientHttpRequestFactory;
    }
    @Bean
    public String getTime() {
        return systemParameterService.getParameterValue(OPManagerParameters.SLEEP_TIME.getIdProcess(),
                OPManagerParameters.SLEEP_TIME.getParameterName(),OPManagerParameters.SLEEP_TIME.getDefaultValue());
    }
}

