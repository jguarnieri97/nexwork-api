package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.ContractsFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.*;
import ar.edu.unlam.tpi.nexwork_api.exceptions.WorkContractClientException;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;
import ar.edu.unlam.tpi.nexwork_api.service.WorkContractService;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountTypeEnum;
import ar.edu.unlam.tpi.nexwork_api.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class WorkContractServiceImpl implements WorkContractService {

    private final WorkContractClient workContractClient;
    private final AccountsClient accountsClient;
    private final DeliveryNoteService deliveryNoteService;
    public static final String CONTRACT_FINALIZED = "FINALIZED";

    @Override
    public List<WorkContractResponse> getContracts(WorkContractRequest request) {
        log.info("Buscando contratos para tipo de cuenta '{}' con id {}", request.getAccountType(), request.getId());

        List<WorkContractResponse> contracts = workContractClient.getContracts(request);

        log.info("Contratos obtenidos: {}", contracts.size());
        return contracts;
    }

    @Override
    public WorkContractResponse createContract(WorkContractCreateRequest request) {
        log.info("Creando nuevo contrato de trabajo: {}", Converter.convertToString(request));

        WorkContractResponse response = workContractClient.createContract(request);

        log.info("Contrato creado con ID: {}", response.getId());
        return response;
    }

    @Override
    public WorkContractDetailResponse getContractById(Long id) {
        log.info("Buscando contrato con id {}", id);

        WorkContractResponse contract = workContractClient.getContractById(id);

        List<AccountDetailRequest> accountRequests = List.of(
                Converter.toAccountRequest(contract.getSupplierId(), AccountTypeEnum.SUPPLIER.getValue()),
                Converter.toAccountRequest(contract.getApplicantId(), AccountTypeEnum.APPLICANT.getValue())
        );

        UserResponse accountDetails = accountsClient.getAccountById(accountRequests);

        log.info("Detalles de la cuenta obtenidos: {}", accountDetails);

        return Converter.toWorkContractDetailResponse(contract, accountDetails.getSuppliers(), accountDetails.getApplicants());
    }

    @Override
    public void finalizeContract(Long id, ContractsFinalizeRequest request) {
        log.info("Finalizando contrato con id {} - detalle: {}", id, request.getDetail());

        ContractsFinalizeRequest finalRequest = this.buildFinalizeRequest(request);

        try {
            workContractClient.finalizeContract(id, finalRequest);
            deliveryNoteService.buildDeliveryNote(id);
            log.info("Contrato finalizado y remito generado exitosamente.");
        } catch (Exception ex) {
            log.error("Error al finalizar contrato con id {}: {}", id, ex.getMessage(), ex);
            throw new WorkContractClientException(
                    ErrorResponse.builder()
                            .code(500)
                            .message("CONTRACT_FINALIZATION_ERROR")
                            .detail("Error al finalizar contrato con ID: " + id)
                            .build()
            );
        }
    }

    @Override
    public DeliveryNoteResponse getDeliveryNoteById(Long contractId) {
        log.info("Consultando remito para contrato id {}", contractId);

        return Optional.ofNullable(workContractClient.getDeliveryNoteById(contractId))
                .orElseThrow(() -> {
                    log.error("No se encontró remito para contrato id {}", contractId);
                    return new WorkContractClientException(
                            ErrorResponse.builder()
                                    .code(404)
                                    .message("DELIVERY_NOTE_NOT_FOUND")
                                    .detail("Remito no encontrado para contrato ID: " + contractId)
                                    .build()
                    );
                });
    }

    private ContractsFinalizeRequest buildFinalizeRequest(ContractsFinalizeRequest request) {
        return ContractsFinalizeRequest.builder()
                .state(CONTRACT_FINALIZED)
                .detail(request.getDetail())
                .files(request.getFiles())
                .build();
    }
}