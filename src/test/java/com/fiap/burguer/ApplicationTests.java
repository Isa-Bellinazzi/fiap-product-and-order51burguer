package com.fiap.burguer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
 class ApplicationTests {

    @Test
    void main() {
        Application.main(new String[]{});
    }
}
