package icu.agony.clm.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.config.properties.ClmCaptchaProperties;
import icu.agony.clm.config.properties.ClmDefaultProperties;
import org.hibernate.validator.HibernateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Properties;

@SpringBootConfiguration
@EnableCaching
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableConfigurationProperties({ClmAuthProperties.class, ClmDefaultProperties.class, ClmCaptchaProperties.class})
public class ClmConfiguration {

    @Bean
    Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory()
            .getValidator();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    DefaultKaptcha kaptcha() throws IOException {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        ClassPathResource file = new ClassPathResource("config/kaptcha.properties");
        Properties properties = new Properties();
        properties.load(file.getInputStream());
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
