package site.zhangsun.example.jtaatomikos.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.zhangsun.example.jtaatomikos.config.Slave01DatabaseConfig;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Functions: Application Data Source Configurations
 *
 * @author Murphy Zhang Sun
 * @date 2019-07-03 14:19
 * @since v4.0.1
 */
@Data
@Configuration
public class Slaver01DataSourcesConfig {

    private final Slave01DatabaseConfig slave01DatabaseConfig;

    public Slaver01DataSourcesConfig(Slave01DatabaseConfig slave01DatabaseConfig) {
        this.slave01DatabaseConfig = slave01DatabaseConfig;
    }

    @Bean(name = "slaver01DataSource")
    public DataSource slaver01DataSource (Slave01DatabaseConfig slave01DatabaseConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(slave01DatabaseConfig.getUrl());
        mysqlXaDataSource.setPassword(slave01DatabaseConfig.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUser(slave01DatabaseConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSourceClassName(slave01DatabaseConfig.getDriverClassName());
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setMinPoolSize(slave01DatabaseConfig.getMinPoolSize());
        xaDataSource.setUniqueResourceName("slaver01DataSource");
        xaDataSource.setMaxPoolSize(slave01DatabaseConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(slave01DatabaseConfig.getMaxLifetime());
        xaDataSource.setLoginTimeout(slave01DatabaseConfig.getLoginTimeout());
        xaDataSource.setBorrowConnectionTimeout(slave01DatabaseConfig.getBorrowConnectionTimeout());
        xaDataSource.setMaintenanceInterval(slave01DatabaseConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(slave01DatabaseConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(slave01DatabaseConfig.getTestQuery());
        return xaDataSource;
    }

    @Bean(name = "slaver01SqlSessionFactory")
    public SqlSessionFactory slaver01SqlSessionFactory(@Qualifier("slaver01DataSource") DataSource slaver01DataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(slaver01DataSource);
        return bean.getObject();
    }

    @Bean(name = "slaver01SqlSessionTemplate")
    public SqlSessionTemplate slaver01SqlSessionTemplate(@Qualifier("slaver01SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}