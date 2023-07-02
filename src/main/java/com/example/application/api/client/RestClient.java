package com.example.application.api.client;

import com.example.application.api.dto.instance.Instance;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RestClient {
    WebClient client;

    public Mono<Instance> fetchInstance(String url) {
        client = WebClient.create();

        return client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Instance.class);
    }
}
