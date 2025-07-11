package ar.edu.unlam.tpi.nexwork_api.client.impl;

import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.client.error.ErrorHandler;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRejectedRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetDetailFromBudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetSupplierResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
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
        private final ErrorHandler errorHandler;

        @Value("${budgets.host}")
        private String host;

        @Override
        public List<BudgetResponse> getApplicantBudgets(Long applicantId) {
                var response = webClient.get()
                                .uri(host + "user/applicant/" + applicantId)
                                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                .retrieve()
                                .onStatus(HttpStatusCode::is4xxClientError,
                                                clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                                                .flatMap(errorHandler::handle4xxError))
                                .onStatus(HttpStatusCode::is5xxServerError,
                                                clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                                                .flatMap(errorHandler::handle5xxError))
                                .bodyToMono(new ParameterizedTypeReference<GenericResponse<List<BudgetResponse>>>() {
                                })
                                .block();
                assert response != null;
                return response.getData();
        }

        @Override
        public List<BudgetSupplierResponse> getSupplierBudgets(Long supplierId) {
            var response = webClient.get()
                    .uri(host + "user/supplier/" + supplierId)
                    .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                    .flatMap(errorHandler::handle4xxError))
                    .onStatus(HttpStatusCode::is5xxServerError,
                            clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                    .flatMap(errorHandler::handle5xxError))
                    .bodyToMono(new ParameterizedTypeReference<GenericResponse<List<BudgetSupplierResponse>>>() {})
                    .block();
        
            assert response != null;
            return response.getData();
        }

        @Override
        public BudgetDetailFromBudgetsClient getBudgetDetail(String id) {
                var response = webClient.get()
                                .uri(host + "budget/" + id)
                                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                .retrieve()
                                .onStatus(HttpStatusCode::is4xxClientError,
                                                clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                                                .flatMap(errorHandler::handle4xxError))
                                .onStatus(HttpStatusCode::is5xxServerError,
                                                clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                                                .flatMap(errorHandler::handle5xxError))
                                .bodyToMono(new ParameterizedTypeReference<GenericResponse<BudgetDetailFromBudgetsClient>>() {
                                })
                                .block();
                assert response != null;
                return response.getData();
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
                                                                .flatMap(errorHandler::handle4xxError))
                                .onStatus(HttpStatusCode::is5xxServerError,
                                                clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                                                .flatMap(errorHandler::handle5xxError))
                                .bodyToMono(Void.class)
                                .block();
        }
    @Override
    public void updateBudget(String budgetId, Long supplierId, BudgetUpdateDataRequestDto budgetRequest) {
        webClient.put()
                .uri(host + "budget/"+budgetId+"/user/" + supplierId)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(budgetRequest), BudgetUpdateDataRequestDto.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle5xxError))
                .bodyToMono(Void.class)
                .block();
    }



        @Override
        public void finalizeBudget(String id, BudgetFinalizeRequest budgetFinalizeRequest) {
                webClient.put()
                                .uri(host + "budget/" + id)
                                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                .body(Mono.just(budgetFinalizeRequest), BudgetFinalizeRequest.class)
                                .retrieve()
                                .onStatus(HttpStatusCode::is4xxClientError,
                                                clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                                                .flatMap(errorHandler::handle4xxError))
                                .onStatus(HttpStatusCode::is5xxServerError,
                                                clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                                                .flatMap(errorHandler::handle5xxError))
                                .bodyToMono(Void.class)
                                .block();
        }

        @Override
        public void finalizeBudgetRequestState(String budgetId) {
                webClient.put()
                    .uri(host + "budget/" + budgetId + "/finalize-request")
                    .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                    .flatMap(errorHandler::handle4xxError))
                    .onStatus(HttpStatusCode::is5xxServerError,
                            clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                    .flatMap(errorHandler::handle5xxError))
                    .toBodilessEntity()
                    .block();
            }

    @Override
    public void rejectBudget(Long id, BudgetRejectedRequest budgetRejectedRequest) {
        webClient.put()
                .uri(host + "budget/" + id + "/user/" + budgetRejectedRequest.getSupplierId() + "/reject")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(budgetRejectedRequest), BudgetRejectedRequest.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorHandler::handle5xxError))
                .toBodilessEntity()
                .block();
    }
}
