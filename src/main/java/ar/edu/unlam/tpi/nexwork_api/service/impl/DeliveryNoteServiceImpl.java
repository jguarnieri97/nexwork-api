package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.*;
import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.DeliveryNoteRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.AccountDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.UserResponse;
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
        WorkContractResponse contract = workContractClient.getContractById(contractId);
        log.info("Contrato encontrado: {}", Converter.convertToString(contract));

        List<AccountDetailRequest> accountRequests = List.of(
                buildAccountRequest(contract.getSupplierId(), SUPPLIER_ACCOUNT),
                buildAccountRequest(contract.getApplicantId(), APPLICANT_ACCOUNT)
        );

        log.info("Buscando información de cuenta del proveedor con id {}", contract.getSupplierId());
        UserResponse accounts = accountsClient.getAccountById(accountRequests);
        log.info("Proveedor encontrado: {}", Converter.convertToString(accounts.getSuppliers()));

        DeliveryNoteRequest request = this.buildDeliveryNote(contract, accounts.getSuppliers(), accounts.getApplicants());

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

    private DeliveryNoteRequest buildDeliveryNote(WorkContractResponse contract, List<AccountDetailResponse> supplier, List<AccountDetailResponse> applicant) {

        List<CompanyData> supplierData = supplier.stream()
                .map(s -> CompanyData.builder()
                        .companyName(s.getName())
                        .email(s.getEmail())
                        .phone(s.getPhone())
                        .address(s.getAddress())
                        .cuit(s.getCuit())
                        .build())
                .toList();

        List<CompanyData> applicantData = applicant.stream()
                .map(a -> CompanyData.builder()
                        .companyName(a.getName())
                        .email(a.getEmail())
                        .phone(a.getPhone())
                        .address(a.getAddress())
                        .cuit(a.getCuit())
                        .build())
                .toList();


        DescriptionObject item = DescriptionObject.builder()
                .detail(contract.getDetail())
                .price(contract.getPrice())
                .build();

        BodyData body = BodyData.builder()
                .noteNumber("00001")
                .descriptionData(List.of(item))
                .build();

        return DeliveryNoteRequest.builder()
                .contractId(contract.getId())
                .supplierData(supplierData)
                .applicantData(applicantData)
                .build();
    }
}
