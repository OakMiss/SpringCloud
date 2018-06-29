package Oak.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册和发现模块
 */
@SpringBootApplication
@EnableEurekaServer     //表示是一个注册服务中心
public class EurekaApp
{
    public static void main(String[] args) {
        SpringApplication.run(EurekaApp.class, args);
    }
}
