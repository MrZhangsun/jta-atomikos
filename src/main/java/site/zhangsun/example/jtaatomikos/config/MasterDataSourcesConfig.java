package site.zhangsun.example.jtaatomikos.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = "site.zhangsun.example.jtaatomikos.mapper.master", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MasterDataSourcesConfig {
    private final MasterDatabaseConfig masterDatabaseConfig;

    public MasterDataSourcesConfig(MasterDatabaseConfig masterDatabaseConfig) {
        this.masterDatabaseConfig = masterDatabaseConfig;
    }

    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource (MasterDatabaseConfig masterDatabaseConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(masterDatabaseConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(masterDatabaseConfig.getPassword());
        mysqlXaDataSource.setUser(masterDatabaseConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSourceClassName(masterDatabaseConfig.getDriverClassName());
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("masterDataSource");
        xaDataSource.setMinPoolSize(masterDatabaseConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(masterDatabaseConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(masterDatabaseConfig.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(masterDatabaseConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(masterDatabaseConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(masterDatabaseConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(masterDatabaseConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(masterDatabaseConfig.getTestQuery());
        return xaDataSource;
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}