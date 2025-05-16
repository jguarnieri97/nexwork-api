package ar.edu.unlam.tpi.nexwork_api.client.impl;

import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.exceptions.WorkContractClientException;
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
public class WorkContractClientImpl implements WorkContractClient {

    private final WebClient webClient;

    @Value("${contracts.host}")
    private String host;

    @Override
    public List<WorkContractResponse> getContracts(WorkContractRequest request) {
        String accountType = request.getAccountType().toLowerCase();
        String url = host + "accounts/" + accountType + "/" + request.getId();

        return webClient.get()
                .uri(url)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(ErrorResponse.class)
                                .flatMap(WorkContractClientImpl::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> response.bodyToMono(ErrorResponse.class)
                                .flatMap(WorkContractClientImpl::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<List<WorkContractResponse>>>() {
                })
                .block()
                .getData();
    }

    @Override
    public WorkContractResponse createContract(WorkContractCreateRequest request) {
        String url = host + "work-contract";
    
        return webClient.post()
                .uri(url)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(ErrorResponse.class)
                                .flatMap(WorkContractClientImpl::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> response.bodyToMono(ErrorResponse.class)
                                .flatMap(WorkContractClientImpl::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<WorkContractResponse>>() {})
                .block()
                .getData();
    }
    

    private static Mono<Throwable> handle4xxError(ErrorResponse error) {
        log.error("Error del cliente externo Contracts API (4xx): {}", error);
        return Mono.error(new WorkContractClientException(error));
    }

    private static Mono<Throwable> handle5xxError(ErrorResponse error) {
        log.error("Error del servidor externo Contracts API (5xx): {}", error);
        return Mono.error(new WorkContractClientException(error));
    }
}
