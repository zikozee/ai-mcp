package com.zee.systems;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
@SpringBootTest(classes = DeploymentServiceApplication.class)
public class DeploymentServiceTest {

    @Test
    public void testApp()
    {
        assertTrue( true );
    }
}
