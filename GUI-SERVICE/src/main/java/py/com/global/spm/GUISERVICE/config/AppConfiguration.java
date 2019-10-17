package py.com.global.spm.GUISERVICE.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import py.com.global.spm.GUISERVICE.services.SystemParameterService;
import py.com.global.spm.GUISERVICE.utils.RedisParameters;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import javax.jms.ConnectionFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
//@EnableJpaRepositories(basePackages = "py.com.global.spm.GUISERVICE.dao")
public class AppConfiguration {

    private static final Logger logger = LoggerFactory
            .getLogger(AppConfiguration.class);

    @Autowired
    SystemParameterService systemParameterService;

    @Value("${spring.redis.cluster.nodes}")
    String redisClusterNodes;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean(name = "SimpleRestTemplate")
    public RestTemplate restTemplate() {
        logger.info("Creando Singleton RestTemplate, para conexiones HTTP... ");
        return new RestTemplate(getClientHttpRequestFactory());
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int conectTimeout = Integer.parseInt(systemParameterService.getSystemParameterValue(SPM_GUI_Constants.PROCESS_SPM_GENERAL,
                SPM_GUI_Constants.SYSTEM_PARAMETERS_CONNECT_TIMEOUT,SPM_GUI_Constants.DEFAULT_TIMEOUT_MS));

        int readTimeout = Integer.parseInt(systemParameterService.getSystemParameterValue(SPM_GUI_Constants.PROCESS_SPM_GENERAL,
                SPM_GUI_Constants.SYSTEM_PARAMETERS_READ_TIMEOUT,SPM_GUI_Constants.DEFAULT_TIMEOUT_MS));

        int maxConnection = Integer.parseInt(systemParameterService.getSystemParameterValue(SPM_GUI_Constants.PROCESS_SPM_GENERAL,
                SPM_GUI_Constants.SYSTEM_PARAMETERS_MAX_CONNECTIONS,SPM_GUI_Constants.DEFAULT_MAX_CONNECTIONS));
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setDefaultMaxPerRoute(maxConnection);
        connManager.setMaxTotal(maxConnection);
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(client);
        clientHttpRequestFactory.setConnectTimeout(conectTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        logger.info("HTTP CONNECT_TIMEOUT: "+conectTimeout+ " ms");
        logger.info("HTTP READ_TIMEOUT: "+readTimeout+" ms");
        logger.info("HTTP MAX_CONNECTIONS: "+connManager.getMaxTotal());
        return clientHttpRequestFactory;
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
                                getSystemParameterValue(RedisParameters.MEMORY_DATABASE_POOL_MIN_IDLE_CONNECTIONS.getIdProcess(),
                                        RedisParameters.MEMORY_DATABASE_POOL_MIN_IDLE_CONNECTIONS.getParameterName(), "5")));
                        connectionFactory.getPoolConfig().setMaxIdle(Integer.valueOf(systemParameterService.
                                getSystemParameterValue(RedisParameters.MEMORY_DATABASE_POOL_MAX_IDLE_CONNECTIONS.getIdProcess(),
                                        RedisParameters.MEMORY_DATABASE_POOL_MAX_IDLE_CONNECTIONS.getParameterName(), "33")));
                        connectionFactory.getPoolConfig().setMaxTotal(Integer.valueOf(systemParameterService.
                                getSystemParameterValue(RedisParameters.MEMORY_DATABASE_POOL_MAX_TOTAL_CONNECTONS.getIdProcess(),
                                        RedisParameters.MEMORY_DATABASE_POOL_MAX_TOTAL_CONNECTONS.getParameterName(), "100")));
                        logger.info("Redis Database Pool Min Idle Connections: {}", connectionFactory.getPoolConfig().getMinIdle());
                        logger.info("Redis Database Pool Max Idle Connections: {} ", connectionFactory.getPoolConfig().getMaxIdle());
                        logger.info("Redis Database Pool Max Total Connections: {}", connectionFactory.getPoolConfig().getMaxTotal());
                    }

                    try {
                        if (standalone.length == 2) {
                            redisHost = standalone[0];
                            redisPort = Integer.valueOf(standalone[1]);
                        }
                    } catch (Exception e) {
                        logger.error("Error al parsear el Puerto Redis :  ", e);
                    } finally {
                        if (connectionFactory.getStandaloneConfiguration() != null) {
                            connectionFactory.getStandaloneConfiguration().setHostName(redisHost);
                            connectionFactory.getStandaloneConfiguration().setPort(redisPort);
                        }
                        logger.info("Redis Host: {}", connectionFactory.getHostName());
                        logger.info("Redis Port: {}", connectionFactory.getPort());
                    }

                } else {
                    RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(Arrays.asList(nodes));
                    connectionFactory = new JedisConnectionFactory(clusterConfiguration);
                    if (connectionFactory.getClusterConnection() != null)
                        logger.info("Redis Cluster Nodes: {}", connectionFactory.getClusterConfiguration().getClusterNodes());
                }
            } else {
                logger.error("Redis Error, no se especifica ningun host:port en la configuracion ");
            }
        } catch (Exception e) {
            logger.error("Error in JRedis Connection Factory", e);
        }
        return connectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(t -> logger.error("Error in JMS listener: {}", t.getMessage()));
//        factory.setConcurrency(systemParameterService.
//                getSystemParameterValue(SPM_GUI_Constants.PROCESS_SPM_GENERAL,
//                        SPM_GUI_Constants.SYSTEM_PARAMETERS_JMS_LISTENER_CONCURRENCY, "3"));
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds((int) TimeUnit.HOURS.toSeconds(1));
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
}
