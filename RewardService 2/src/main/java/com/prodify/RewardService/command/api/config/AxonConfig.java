//package com.prodify.RewardService.command.api.config;
//
//import org.axonframework.config.EventProcessingConfiguration;
//import org.axonframework.eventhandling.tokenstore.TokenStore;
//import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
//import org.axonframework.spring.config.AxonConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import jakarta.persistence.EntityManager;
//
//@Configuration
//public class AxonConfig {
//
//    @Bean
//    public TokenStore tokenStore(EntityManager entityManager) {
//        return JpaTokenStore.builder()
//                .entityManagerProvider(() -> entityManager)
//                .build();
//    }
//
//    @Bean
//    public EventProcessingConfiguration eventProcessingConfiguration(AxonConfiguration axonConfiguration) {
//        return axonConfiguration.eventProcessingConfiguration();
//    }
//}
