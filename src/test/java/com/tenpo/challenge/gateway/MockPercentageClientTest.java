package com.tenpo.challenge.gateway;

import com.tenpo.challenge.controller.exception.ServiceUnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@EnableRetry
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
    public void testGetPercentage_ShouldRetryAndEventuallySucceed() {
        Map<String, Object> body = new HashMap<>();
        body.put("value", 15.0);

        when(restTemplate.getForEntity(anyString(), eq(Map.class)))
                .thenThrow(new RuntimeException("Temporary failure 1"))
                .thenThrow(new RuntimeException("Temporary failure 2"))
                .thenReturn(ResponseEntity.ok(body)); // Third attempt succeeds

        double result = client.getPercentage();

        assertEquals(15.0, result);
        verify(restTemplate, times(3)).getForEntity(anyString(), eq(Map.class));
    }

    @Test
    public void testGetPercentage_FailsAfterRetries_ShouldThrow() {
        when(restTemplate.getForEntity(anyString(), eq(Map.class)))
                .thenThrow(new RuntimeException("All retries failed"));

        ServiceUnavailableException ex = assertThrows(ServiceUnavailableException.class, () -> {
            client.getPercentage();
        });

        assertEquals("Can't get percentage from service or cache.", ex.getMessage());
        verify(restTemplate, times(3)).getForEntity(anyString(), eq(Map.class));
    }
}
