package com.dd5.common;

import com.dd5.entity.effets.AttaqueDeBaseEntity;
import com.dd5.entity.effets.ConditionsEntity;
import com.dd5.entity.testdifficulte.TestAttaquantCA;
import com.dd5.entity.testdifficulte.TestDefenseurEvasion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(new NamedType(TestAttaquantCA.class, "attaque"));
        mapper.registerSubtypes(new NamedType(TestDefenseurEvasion.class, "evasion"));

        mapper.registerSubtypes(new NamedType(AttaqueDeBaseEntity.class, "degats"));
        mapper.registerSubtypes(new NamedType(ConditionsEntity.class, "condition"));

        return mapper;
    }
}
