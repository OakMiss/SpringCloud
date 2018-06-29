package Oak.acorn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 客户端模块
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"Oak.acorn"})
public class AcornApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AcornApp.class, args);
    }
}
