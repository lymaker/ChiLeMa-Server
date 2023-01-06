package icu.agony.clm.config;

import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.config.properties.ClmDefaultProperties;
import org.hibernate.validator.HibernateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.validation.Validation;
import javax.validation.Validator;

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

}
