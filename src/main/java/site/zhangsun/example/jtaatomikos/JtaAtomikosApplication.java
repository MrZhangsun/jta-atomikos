package site.zhangsun.example.jtaatomikos;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import site.zhangsun.example.jtaatomikos.config.MasterDatabaseConfig;
import site.zhangsun.example.jtaatomikos.config.Slave01DatabaseConfig;

@EnableAsync
@SpringBootApplication
@MapperScan(basePackages = {"site.zhangsun.example.jtaatomikos.mapper"})
@EnableConfigurationProperties({MasterDatabaseConfig.class, Slave01DatabaseConfig.class})
public class JtaAtomikosApplication {

    public static void main(String[] args) {
        SpringApplication.run(JtaAtomikosApplication.class, args);
    }

}
