package ar.edu.unlam.tpi.nexwork_api.client.impl;

import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.BudgetsClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class BudgetsClientImpl implements BudgetsClient {

    private final WebClient webClient;

    @Value("${budgets.host}")
    private String host;

    @Override
    public List<BudgetResponse> getBudgets(Long applicantId) {
        return webClient.get()
                .uri(host + "budget/user/" + applicantId)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(BudgetsClientImpl::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(BudgetsClientImpl::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<List<BudgetResponse>>() {
                })
                .block();
    }

    @Override
    public BudgetResponseDetail getBudgetDetail(Long id) {
        return webClient.get()
                .uri(host + "budget/" + id)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(BudgetsClientImpl::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(BudgetsClientImpl::handle5xxError))
                .bodyToMono(BudgetResponseDetail.class)
                .block();
    }

    @Override
    public void createBudget(BudgetRequest budgetRequest) {
        webClient.post()
                .uri(host + "budget")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(budgetRequest), BudgetRequest.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(BudgetsClientImpl::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(BudgetsClientImpl::handle5xxError))
                .bodyToMono(Void.class)
                .block();
    }

    private static Mono<Throwable> handle4xxError(ErrorResponse error) {
        log.error("Error del cliente externo Budgets API: {}", error);
        return Mono.error(new BudgetsClientException(error));
    }

    private static Mono<Throwable> handle5xxError(ErrorResponse error) {
        log.error("Error del servidor externo Budgets API: {}", error);
        return Mono.error(new BudgetsClientException(error));
    }

}
