package au.edu.rmit.sept.webapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class TestWebappApplication {

    @Test
    void contextLoads() {
        // Stays empty just checks if spring loads
    }

    @Test
    void mainMethodRunsWithoutErrors() {
        assertDoesNotThrow(() -> WebappApplication.main(new String[]{}));
    }
}
