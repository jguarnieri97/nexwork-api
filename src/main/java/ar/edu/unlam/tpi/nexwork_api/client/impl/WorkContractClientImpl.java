package ar.edu.unlam.tpi.nexwork_api.client.impl;

import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.client.error.ErrorHandler;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractUpdateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.DeliveryNoteRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.DeliveryNoteResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkContractClientImpl implements WorkContractClient {

    private final WebClient webClient;
    private final ErrorHandler errorHandler;

    @Value("${contracts.host}")
    private String host;

    @Override
    public List<WorkContractResponse> getContracts(WorkContractRequest request) {
        String accountType = request.getAccountType().toLowerCase();
        String url = UriComponentsBuilder.fromUriString(host + "accounts/" + accountType + "/" + request.getId())
        .queryParam("limit", request.getLimit())
        .build()
        .toUriString();

        var response = webClient.get()
                .uri(url)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        serverResponse -> serverResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<List<WorkContractResponse>>>() {
                })
                .block();

                assert response != null;
                return response.getData();
               
    }

    @Override
    public WorkContractResponse createContract(WorkContractCreateRequest request) {
        String url = host + "work-contract";
    
        var response = webClient.post()
                .uri(url)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        serverResponse -> serverResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<WorkContractResponse>>() {})
                .block();
                  
                assert response != null;
                return response.getData();
    }
    
    @Override
    public void updateContractState(Long id, WorkContractUpdateRequest request) {
        String url = host + "work-contract/" + id;

        webClient.put()
                .uri(url)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> response.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle5xxError))
                .bodyToMono(Void.class)
                .block();
        }



    @Override
    public WorkContractResponse getContractById(Long id) {
        String url = host + "work-contract/" + id;

        var response = webClient.get()
                .uri(url)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        serverResponse -> serverResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<WorkContractResponse>>() {})
                .block();

                assert response != null;
                return response.getData();
    }


    @Override
    public void createDeliveryNote(DeliveryNoteRequest request) {
        webClient.post()
                .uri(host + "delivery-note")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        r -> r.bodyToMono(ErrorResponse.class).flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        r -> r.bodyToMono(ErrorResponse.class).flatMap(errorHandler::handle5xxError))
                .bodyToMono(Void.class)
                .block();
        }


    @Override
    public DeliveryNoteResponse getDeliveryNoteById(Long contractId) {
        String url = host + "delivery-note/" + contractId;

        return webClient.get()
                .uri(url)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        r -> r.bodyToMono(ErrorResponse.class).flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        r -> r.bodyToMono(ErrorResponse.class).flatMap(errorHandler::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<DeliveryNoteResponse>>() {})
                .block()
                .getData();
                }
}
