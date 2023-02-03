package hu.ponte.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = "hu.ponte.hr", exclude = {
        MultipartAutoConfiguration.class
})
public class SeniorTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeniorTestApplication.class, args);
    }

}
