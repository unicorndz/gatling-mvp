package io.unicorn;

import io.undertow.servlet.api.DeploymentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GatlingTargetApiApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatlingTargetApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GatlingTargetApiApplication.class, args);
    }

    @GetMapping("/hello")
    ResponseEntity<String> sayHello() {
        LOGGER.info("calling service");
        return ResponseEntity.ok().body("Hello there !");
    }

    @GetMapping("/bad")
    ResponseEntity<?> bad() {
        return ResponseEntity.badRequest().build();
    }

    @Bean
    @ConditionalOnClass(name = "io.undertow.servlet.api.DeploymentInfo")
    UndertowEmbeddedServletContainerFactory factory() {
        UndertowEmbeddedServletContainerFactory f = new UndertowEmbeddedServletContainerFactory();
        f.addDeploymentInfoCustomizers((UndertowDeploymentInfoCustomizer) deploymentInfo -> deploymentInfo.setAllowNonStandardWrappers(true));
        return f;
    }
}
