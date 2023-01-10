package icu.agony.clm.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.config.properties.ClmDefaultProperties;
import org.hibernate.validator.HibernateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@SpringBootConfiguration
@EnableCaching
@EnableTransactionManagement
@EnableConfigurationProperties({ClmAuthProperties.class, ClmDefaultProperties.class})
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
        File file = ResourceUtils.getFile("classpath:config/kaptcha.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
