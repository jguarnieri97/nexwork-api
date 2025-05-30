package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.*;
import ar.edu.unlam.tpi.nexwork_api.dto.request.ContractsFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.DeliveryNoteResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;
import ar.edu.unlam.tpi.nexwork_api.service.WorkContractService;
import ar.edu.unlam.tpi.nexwork_api.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkContractServiceImpl implements WorkContractService {

    private final WorkContractClient workContractClient;
    private final DeliveryNoteService deliveryNoteService;
    public static final String CONTRACT_FINALIZED = "FINALIZED";

    @Override
    public List<WorkContractResponse> getContracts(WorkContractRequest request) {
        log.info("Buscando contratos para tipo de cuenta '{}' con id {}", request.getAccountType(), request.getId());

        var contracts = workContractClient.getContracts(request);

        log.info("Contratos obtenidos: {}", contracts.size());

        return contracts;
    }

    @Override
    public WorkContractResponse createContract(WorkContractCreateRequest request) {
        log.info("Creando nuevo contrato de trabajo: {}", Converter.convertToString(request));

        var response = workContractClient.createContract(request);

        log.info("Contrato creado con ID: {}", response.getId());

        return response;
    }


    @Override
    public WorkContractResponse getContractById(Long id) {
        log.info("Buscando contrato con id {}", id);
        var response = workContractClient.getContractById(id);

        log.info("Contrato encontrado con id {}", id);

        return response;
    }

    @Override
    public void finalizeContract(Long id, ContractsFinalizeRequest request) {
        log.info("Finalizando contrato con id {} - detalle: {}", id, request.getDetail());

        var contractsRequest = this.buildFinalizeRequest(request);

        workContractClient.finalizeContract(id, contractsRequest);

        deliveryNoteService.buildDeliveryNote(id);

        log.info("Contrato finalizado y remito generado exitosamente.");
    }

    @Override
    public DeliveryNoteResponse getDeliveryNoteById(Long contractId) {
        log.info("Consultando remito para contrato id {}", contractId);
        return workContractClient.getDeliveryNoteById(contractId);
    }

    private ContractsFinalizeRequest buildFinalizeRequest(ContractsFinalizeRequest request) {
        return ContractsFinalizeRequest.builder()
                .state(CONTRACT_FINALIZED)
                .detail(request.getDetail())
                .files(request.getFiles())
                .build();
    }


}