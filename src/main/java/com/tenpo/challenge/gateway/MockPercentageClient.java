    package com.tenpo.challenge.gateway;

    import com.tenpo.challenge.controller.exception.ServiceUnavailableException;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.cache.Cache;
    import org.springframework.cache.CacheManager;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import org.springframework.web.client.RestTemplate;

    import java.util.Map;

    @Component
    public class MockPercentageClient implements PercentageClient {
        private final RestTemplate restTemplate;
        private final String externalUrl;
        private final CacheManager cacheManager;

        public MockPercentageClient(RestTemplate restTemplate,
                                    @Value("${external.percentage.url}") String externalUrl,
                                    CacheManager cacheManager) {
            this.restTemplate = restTemplate;
            this.externalUrl = externalUrl;
            this.cacheManager = cacheManager;
        }

        private static final String CACHE_NAME = "percentage";
        private static final String CACHE_KEY = "value";

        @Override
        public double getPercentage() {
            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (cache != null) {
                Double cached = cache.get(CACHE_KEY, Double.class);
                if (cached != null) {
                    return cached;
                }
            }
            try {
                double value = this.fetchPercentageWithRetry();
                if (cache != null) {
                    cache.put(CACHE_KEY, value);
                }
                return value;
            } catch (Exception e) {
                throw new ServiceUnavailableException("Can't get percentage from service or cache.");
            }
        }

        public double fetchPercentageWithRetry() {
            ResponseEntity<Map> response = restTemplate.getForEntity(externalUrl, Map.class);
            if (response.getBody() == null || !response.getBody().containsKey("value")) {
                throw new RuntimeException("Can't get percentage from service");
            }
            return Double.parseDouble(response.getBody().get("value").toString());
        }
    }
