package dlg.lk.bms;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public Docket demoApi() {
        return new Docket(DocumentationType.SWAGGER_2)//<3>
                .select()//<4>
                .apis(RequestHandlerSelectors.any())//<5>
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//<6>, regex must be in double quotes.
                .build();
    }
}
