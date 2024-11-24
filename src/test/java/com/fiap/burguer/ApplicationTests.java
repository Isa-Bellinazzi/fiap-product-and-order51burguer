package com.fiap.burguer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")  // Define um perfil de teste para garantir que a configuração de teste seja usada
class ApplicationTests {

//	@Autowired
//	private ApplicationContext applicationContext;
//
//	@Test
//	void contextLoads() {
//		// Verifica se o contexto da aplicação carrega sem erros
//		assertThat(applicationContext).isNotNull();
//	}
//
//	@Test
//	void applicationContextTest() {
//		// Verifica se o contexto da aplicação está carregado corretamente
//		assertThat(applicationContext.getBeanDefinitionCount()).isGreaterThan(0);
//	}
}
