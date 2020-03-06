package org.albaslug.pokedraw.service;

import org.albaslug.pokedraw.entity.E621Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class E621IntegrationService {

    @Value("${e621.useragent}")
    private String userAgent;

    private final String REQUEST_TEMPLATE = "/tags?commit=Search&search[category]=4&search[hide_empty]=yes&search[order]=count&limit=1000&page=%d";

    private WebClient e621Client;

    private Map<String, E621Tag> characters;

    @PostConstruct
    private void buildCharacterMap() {
        e621Client = buildE621Client();
        characters = getAllCharacters();
    }

    public List<String> getDisneyCharacters() {
        return characters.values().stream()
                .filter(e -> e.getRelated_tags().contains("disney"))
                .map(E621Tag::getName)
                .collect(Collectors.toList());
    }

    private WebClient buildE621Client() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 10)).build();
        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl("https://e621.net")
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private Map<String, E621Tag> getAllCharacters() {
        e621Client = buildE621Client();
        WebClient.RequestHeadersSpec<?> uri2 = e621Client
                .get()
                .uri(String.format(REQUEST_TEMPLATE, 1));
        List<E621Tag> o = uri2.exchange().block().bodyToFlux(E621Tag.class).collectList().block();
        if (o == null) {
            return null;
        }
        return o.stream()
                .collect(Collectors.toMap(E621Tag::getName, e -> e));
    }



}
