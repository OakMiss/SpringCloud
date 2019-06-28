package Oak.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 注册服务中心
 */
@SpringBootApplication
@EnableConfigServer    //表明为注册服务中心
@EnableEurekaClient    //表明自己是一个eurekaclient.
public class ConfigApp {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApp.class, args);
    }

}