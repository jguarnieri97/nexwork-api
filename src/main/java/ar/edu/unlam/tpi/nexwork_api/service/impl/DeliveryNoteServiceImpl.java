package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.*;
import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.DeliveryNoteRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.AccountDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;
import ar.edu.unlam.tpi.nexwork_api.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryNoteServiceImpl implements DeliveryNoteService {

    private final WorkContractClient workContractClient;
    private final AccountsClient accountsClient;
    public static final String SUPPLIER_ACCOUNT = "supplier";
    public static final String APPLICANT_ACCOUNT = "applicant";

    @Override
    public void buildDeliveryNote(Long contractId) {
        log.info("Creando Remito - Buscando contrato con id {}", contractId);
        var contract = workContractClient.getContractById(contractId);
        log.info("Contrato encontrado: {}", Converter.convertToString(contract));

        log.info("Buscando información de cuenta del proveedor con id {}", contract.getSupplierId());
        var supplier = accountsClient.getAccountById(buildAccountRequest(contract.getSupplierId(), SUPPLIER_ACCOUNT));
        log.info("Proveedor encontrado: {}", Converter.convertToString(supplier));

        log.info("Buscando información de cuenta del solicitante con id {}", contract.getApplicantId());
        var applicant = accountsClient.getAccountById(buildAccountRequest(contract.getApplicantId(), APPLICANT_ACCOUNT));
        log.info("Solicitante encontrado: {}", Converter.convertToString(applicant));

        var request = this.buildDeliveryNote(contract, supplier, applicant);

        log.info("Enviando remito a crear en el servicio Contracts");
        workContractClient.createDeliveryNote(request);
        log.info("Remito creado con éxito!");
    }

    private static AccountDetailRequest buildAccountRequest(Long contract, String supplierAccount) {
        return AccountDetailRequest.builder()
                .userId(contract)
                .type(supplierAccount)
                .build();
    }

    private DeliveryNoteRequest buildDeliveryNote(WorkContractResponse contract, AccountDetailResponse supplier, AccountDetailResponse applicant) {
        var supplierData = CompanyData.builder()
                .companyName(supplier.getName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .address(supplier.getAddress())
                .cuit(supplier.getCuit())
                .build();

        var applicantData = CompanyData.builder()
                .companyName(applicant.getName())
                .email(applicant.getEmail())
                .phone(applicant.getPhone())
                .address(applicant.getAddress())
                .cuit(applicant.getCuit())
                .build();


        var item = DescriptionObject.builder()
                .detail(contract.getDetail())
                .price(contract.getPrice())
                .build();

        var body = BodyData.builder()
                .noteNumber("00001")
                .descriptionData(List.of(item))
                .build();

        return DeliveryNoteRequest.builder()
                .contractId(contract.getId())
                .supplierData(supplierData)
                .applicantData(applicantData)
                .bodyData(body)
                .footData(new FootData())
                .build();
    }
}
