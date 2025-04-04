package br.com.thefood.pagamentos.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigModelMapper {

    @Bean
    public ModelMapper metodoQueCriaUmModelMapper(){
        return new ModelMapper();

    }
}
