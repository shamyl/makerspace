package com.shf.makerspace;

import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class MakerspaceApplication {

    @Value("${spring.datasource.username}")
    private String dataSourceUserName;
    @Value("${spring.datasource.password}")
    private String dataSourcePassword;
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.maxPoolSize}")
    private Integer maxPoolSize;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MakerspaceApplication.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "http";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (contextPath == null || contextPath.equals("")) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local:\t\t\t{}://localhost:{}{}\n\t" +
                        "External:\t\t{}://{}:{}{}\n\t" +
                        "Profile(s):\t{}\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles()
        );
    }

    @Bean
    public HikariConfig hikariConfig() {

        HikariConfig config = new HikariConfig();
        config.setUsername(dataSourceUserName);
        config.setPassword(dataSourcePassword);
        config.setJdbcUrl(dataSourceUrl);
        config.setMaximumPoolSize(maxPoolSize);
        config.setIdleTimeout(TimeUnit.SECONDS.toMillis(500));
        config.setMinimumIdle(0);

        config.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(500));
        config.setConnectionInitSql("SET NAMES 'utf8mb4';");
        config.setConnectionTestQuery("SELECT 1");

        config.addDataSourceProperty("autoReconnect", true);
        config.addDataSourceProperty("tcpKeepAlive", true);

        config.addDataSourceProperty("maxReconnects", 5);
        return config;
    }

}
