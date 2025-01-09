package config;

import controller.RegisterController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile ;

@Configuration
@Profile("dev")
public class DsDevConfig {

    private static final Log log = LogFactory.getLog(RegisterController.class);

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        //ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8&useSSL=false&requireSSL=false");  // SSL을 사용하지 않도록 설정
        ds.setUsername("spring5");
        ds.setPassword("spring5");
        ds.setInitialSize(2);
        ds.setMaxActive(10);
        ds.setTestWhileIdle(true);
        ds.setMinEvictableIdleTimeMillis(60000 * 3);
        ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
        log.info("::::::::::::::::::::::::::::::::::[dev] DsDevConfig 환경을 설정합니다. ");

        return ds;
    }







}
