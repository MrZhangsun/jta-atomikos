package site.zhangsun.example.jtaatomikos.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Functions: Master Database Configurations
 *
 * @author Murphy Zhang Sun
 * @date 2019-07-03 14:02
 * @since v4.0.1
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.master")
public class MasterDatabaseConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
    private String testQuery;
}