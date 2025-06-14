package com.tenpo.challenge.gateway;

import com.tenpo.challenge.controller.exception.ServiceUnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockPercentageClientTest {

    private RestTemplate restTemplate;
    private MockPercentageClient client;

    @BeforeEach
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        client = new MockPercentageClient(
                restTemplate,
                "http://mock-url/percentage",
                new ConcurrentMapCacheManager("percentage")
        );
    }

    @Test
    public void testGetPercentage_Succeed() {
        when(restTemplate.getForEntity(anyString(), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(Map.of("value", 15.0)));
        double result = client.getPercentage();
        assertEquals(15.0, result);
    }

    @Test
    public void testGetPercentage_FailsAfterRetries_ShouldThrow() {
        when(restTemplate.getForEntity(anyString(), eq(Map.class)))
                .thenThrow(new RuntimeException("All retries failed"));
        ServiceUnavailableException ex = assertThrows(ServiceUnavailableException.class, () -> {
            client.getPercentage();
        });
        assertEquals("Can't get percentage from service or cache.", ex.getMessage());
    }
}
