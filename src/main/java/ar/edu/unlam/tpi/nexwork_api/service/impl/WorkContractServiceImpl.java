package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.*;
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
    public void finalizeContract(Long id, ContractsFinalizeRequest request) {
        log.info("Finalizando contrato con id {} - detalle: {}", id, request.getDetail());

        var contractsRequest = this.buildFinalizeRequest(request);

        workContractClient.finalizeContract(id, contractsRequest);

        DeliveryNoteRequest remito = buildMockedDeliveryNote(id, request);
    workContractClient.createDeliveryNote(remito);

    log.info("Contrato finalizado y remito generado exitosamente.");
    }

    @Override
    public WorkContractResponse getContractById(Long id) {
        log.info("Buscando contrato con id {}", id);
        var response = workContractClient.getContractById(id);

        log.info("Contrato encontrado con id {}", id);

        return response;
    }

    private ContractsFinalizeRequest buildFinalizeRequest(ContractsFinalizeRequest request) {
        return ContractsFinalizeRequest.builder()
                .state(CONTRACT_FINALIZED)
                .detail(request.getDetail())
                .files(request.getFiles())
                .build();
    }

    private DeliveryNoteRequest buildMockedDeliveryNote(Long contractId, ContractsFinalizeRequest request) {
        CompanyData supplier = new CompanyData();
        supplier.setCompanyName("Proveedor Mock S.A.");
        supplier.setCuit("30-12345678-9");
    
        CompanyData applicant = new CompanyData();
        applicant.setCompanyName("Solicitante Mock SRL");
        applicant.setCuit("20-98765432-1");
    
        DescriptionObject item = new DescriptionObject();
        item.setDetail(request.getDetail());
        item.setPrice(150000.0); // precio simulado
    
        BodyData body = new BodyData();
        body.setNoteNumber("00001");
        body.setDescriptionData(List.of(item));
    
        FootData foot = new FootData();
        foot.setSignature("Firma simulada");
    
        DeliveryNoteRequest remito = DeliveryNoteRequest.builder()
        .contractId(contractId)
        .supplierData(supplier)
        .applicantData(applicant)
        .bodyData(body)
        .footData(foot)
        .build();
    
        return remito;
    }
    
}