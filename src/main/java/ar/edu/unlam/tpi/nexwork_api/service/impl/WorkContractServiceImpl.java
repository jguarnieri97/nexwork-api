package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractResponse;
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
}