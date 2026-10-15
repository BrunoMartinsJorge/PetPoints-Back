package br.com.api.petpoints.shared.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class EmailService {

    private final RestClient restClient;

    public EmailService(@Value("${resend.api-key}") String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.resend.com")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public void enviar(String para, String assunto, String html) {
        var body = Map.of(
                "from", "Pet Points <onboarding@resend.dev>",
                "to", para,
                "subject", assunto,
                "html", html
        );

        restClient.post()
                .uri("/emails")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }
}