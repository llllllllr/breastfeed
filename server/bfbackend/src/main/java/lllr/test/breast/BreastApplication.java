package lllr.test.breast;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;
import org.mybatis.spring.annotation.MapperScan;


@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"lllr.test.breast"})
@RestController
@MapperScan("lllr.test.breast.dao.mapper")
public class BreastApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreastApplication.class, args);
    }



}
