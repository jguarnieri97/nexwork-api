package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.*;
import ar.edu.unlam.tpi.nexwork_api.dto.response.*;
import ar.edu.unlam.tpi.nexwork_api.exceptions.WorkContractClientException;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;
import ar.edu.unlam.tpi.nexwork_api.service.WorkContractService;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountTypeEnum;
import ar.edu.unlam.tpi.nexwork_api.utils.Converter;
import ar.edu.unlam.tpi.nexwork_api.utils.WorkStateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class WorkContractServiceImpl implements WorkContractService {

    private final WorkContractClient workContractClient;
    private final AccountsClient accountsClient;
    private final DeliveryNoteService deliveryNoteService;
    private final BudgetsClient budgetsClient;

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
        budgetsClient.finalizeBudgetRequestState(request.getBudgetId());

        log.info("Contrato creado con ID: {}", response.getId());
        return response;
    }

    @Override
    public WorkContractDetailResponseDto getContractById(Long id) {
        log.info("Buscando contrato con id {}", id);

        WorkContractDetailResponse contract = workContractClient.getContractById(id);

        List<AccountDetailRequest> accountRequests = new ArrayList<>();
        accountRequests.add(Converter.toAccountRequest(contract.getSupplierId(), AccountTypeEnum.SUPPLIER.getValue()));
        accountRequests.add(Converter.toAccountRequest(contract.getApplicantId(), AccountTypeEnum.APPLICANT.getValue()));

        contract.getWorkers().forEach(worker -> accountRequests.add(
                Converter.toAccountRequest(worker, AccountTypeEnum.WORKER.getValue())));

        UserResponse accountDetails = accountsClient.getAccountById(accountRequests);

        log.info("Detalles de la cuenta obtenidos: {}", accountDetails);

        return Converter.toWorkContractDetailResponseDto(
                contract,
                accountDetails.getSuppliers().get(0),
                accountDetails.getApplicants().get(0),
                accountDetails.getWorkers());
    }

    @Override
    public void finalizeContract(Long id, WorkContractFinalizeRequest request) {
        log.info("Finalizando contrato con id {} - detalle: {}", id, request.getDetail());
        try {
            WorkContractUpdateRequest updateRequest = WorkContractUpdateRequest.builder()
                    .state(WorkStateEnum.FINALIZED.toString())
                    .detail(request.getDetail())
                    .files(request.getFiles())
                    .build();

            workContractClient.updateContractState(id, updateRequest);
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

    @Override
    public void iniciateContract(Long id) {
        try {
            WorkContractUpdateRequest request = WorkContractUpdateRequest.builder()
                    .state(WorkStateEnum.INITIATED.toString())
                    .detail(null)
                    .files(null)
                    .build();
            workContractClient.updateContractState(id, request);
        } catch (Exception ex) {
            log.error("Error al iniciar contrato con id {}: {}", id, ex.getMessage(), ex);
            throw new WorkContractClientException(
                    ErrorResponse.builder()
                            .code(500)
                            .message("CONTRACT_INICIATE_ERROR")
                            .detail("Error al iniciar contrato con ID: " + id)
                            .build()
            );
        }
    }

    @Override
    public DeliveryNoteResponse signatureDeliveryNote(Long id, DeliverySignatureRequest request) {
        try{
            return workContractClient.signDeliveryNote(id,request);
        }catch(Exception ex){
            log.error("Error al firmar remito con id {}: {}", id, ex.getMessage(), ex);
            throw new WorkContractClientException(
                    ErrorResponse.builder()
                            .code(500)
                            .message("DELIVERY_NOTE_SIGNATURE_ERROR")
                            .detail("Error al firmar remito con ID: " + id)
                            .build()
            );
        }
    }

}