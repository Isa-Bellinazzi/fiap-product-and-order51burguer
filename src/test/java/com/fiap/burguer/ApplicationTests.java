package com.fiap.burguer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
 class ApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		// Verifica se o contexto da aplicação carrega sem erros
		assertThat(applicationContext).isNotNull();
	}

	@Test
	void applicationContextTest() {
		// Verifica se o contexto da aplicação está carregado corretamente
		assertThat(applicationContext.getBeanDefinitionCount()).isGreaterThan(0);
	}
}
