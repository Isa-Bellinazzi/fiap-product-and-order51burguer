package com.fiap.burguer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
 class ApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void mainTest() {
		Application.main(new String[]{});
		assertNotNull(applicationContext, "O contexto da aplicação não foi carregado.");
	}
}
