package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.request.ContractsFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;

import java.time.LocalDate;
import java.util.List;

public class WorkContractDataHelper {

    public static WorkContractRequest buildRequest(Long id, String accountType) {
        return WorkContractRequest.builder()
                .id(id)
                .accountType(accountType)
                .build();
    }

    public static WorkContractCreateRequest buildCreateRequest() {
        return WorkContractCreateRequest.builder()
                .price(150000.0)
                .dateFrom(LocalDate.now().toString())
                .dateTo(LocalDate.now().plusDays(7).toString())
                .detail("Instalación eléctrica general")
                .supplierId(1L)
                .applicantId(2L)
                .workers(List.of(3L, 4L))
                .build();
    }

    public static ContractsFinalizeRequest buildFinalizeRequest() {
        return ContractsFinalizeRequest.builder()
                .detail("Trabajo finalizado correctamente.")
                .files(List.of("base64file1", "base64file2"))
                .build();
    }

    public static WorkContractResponse buildResponse(Long id) {
        return WorkContractResponse.builder()
                .id(id)
                .price(150000.0)
                .detail("Instalación eléctrica general")
                .supplierId(1L)
                .applicantId(2L)
                .workers(List.of(3L, 4L))
                .build();
    }

    public static List<WorkContractResponse> buildResponseList() {
        return List.of(
                buildResponse(1L),
                buildResponse(2L)
        );
    }
}
