package site.zhangsun.example.jtaatomikos.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.sql.XADataSource;

/**
 * Function:
 *
 * @author zhangsunjiankun - 2019/6/11 下午10:50
 */
@Configuration
public class DataSourcesConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db01")
    public DataSource master() {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        return mysqlXADataSource;
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db02")
    public DataSource slave() {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        return mysqlXADataSource;
    }

    @Bean
    public DataSource xaDataSource(DataSource master, DataSource slave) {
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource((XADataSource) master);
        xaDataSource.setUniqueResourceName("xaDataSource");
        return xaDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("xaDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
