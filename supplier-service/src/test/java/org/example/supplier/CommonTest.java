package org.example.supplier;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test-h2")
public class CommonTest {
    protected final String apiUrl = "/api/v1/";
}
