package ar.edu.unlam.tpi.nexwork_api.client.impl;

import ar.edu.unlam.tpi.nexwork_api.client.NotificationsClient;
import ar.edu.unlam.tpi.nexwork_api.client.error.ErrorHandler;
import ar.edu.unlam.tpi.nexwork_api.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationsClientImpl implements NotificationsClient {

    private final WebClient webClient;
    private final ErrorHandler errorHandler;

    @Value("${notifications.host}")
    private String host;

    @Override
    public void createNotification(NotificationCreateRequest request) {
        webClient.post()
                .uri(host + "/notification")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        r -> r.bodyToMono(ErrorResponse.class).flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        r -> r.bodyToMono(ErrorResponse.class).flatMap(errorHandler::handle5xxError))
                .toBodilessEntity()
                .block();
    }

}
