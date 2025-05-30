package ar.edu.unlam.tpi.nexwork_api.client.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.AccountDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.AccountsClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountsClientImpl implements AccountsClient {

    private final WebClient webClient;

    @Value("${accounts.host}")
    private String host;

    @Override
    public AccountDetailResponse getAccountById(AccountDetailRequest request) {
        var response = webClient.post()
                .uri(host + "users")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        r -> r.bodyToMono(ErrorResponse.class).flatMap(AccountsClientImpl::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        r -> r.bodyToMono(ErrorResponse.class).flatMap(AccountsClientImpl::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<AccountDetailResponse>>() {})
                .block();

        assert response != null;
        return response.getData();
    }

    private static Mono<Throwable> handle4xxError(ErrorResponse error) {
        log.error("Error del cliente externo Accounts API (4xx): {}", error);
        return Mono.error(new AccountsClientException(error));
    }

    private static Mono<Throwable> handle5xxError(ErrorResponse error) {
        log.error("Error del servidor externo Accounts API (5xx): {}", error);
        return Mono.error(new AccountsClientException(error));
    }
}
