package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.*;
import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.DeliveryNoteRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.AccountDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.UserResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountTypeEnum;
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

    @Override
    public void buildDeliveryNote(Long contractId) {
        log.info("Creando Remito - Buscando contrato con id {}", contractId);
        WorkContractDetailResponse contract = workContractClient.getContractById(contractId);
        log.info("Contrato encontrado: {}", Converter.convertToString(contract));

        List<AccountDetailRequest> accountRequests = List.of(
                Converter.toAccountRequest(contract.getSupplierId(), AccountTypeEnum.SUPPLIER.getValue()),
                Converter.toAccountRequest(contract.getApplicantId(), AccountTypeEnum.APPLICANT.getValue())
        );

        log.info("Buscando información de cuenta del proveedor con id {}", contract.getSupplierId());
        UserResponse accounts = accountsClient.getAccountById(accountRequests);
        log.info("Proveedor encontrado: {}", Converter.convertToString(accounts.getSuppliers()));

        DeliveryNoteRequest request = this.buildDeliveryNote(contract, accounts.getSuppliers().get(0), accounts.getApplicants().get(0));

        log.info("Enviando remito a crear en el servicio Contracts");
        workContractClient.createDeliveryNote(request);
        log.info("Remito creado con éxito!");
    }

    private DeliveryNoteRequest buildDeliveryNote(WorkContractDetailResponse contract, AccountDetailResponse s, AccountDetailResponse a) {

        return DeliveryNoteRequest.builder()
                .contractId(contract.getId())
                .supplierData(CompanyData.builder()
                        .companyName(s.getName())
                        .email(s.getEmail())
                        .phone(s.getPhone())
                        .address(s.getAddress())
                        .cuit(s.getCuit())
                        .build())
                .applicantData(CompanyData.builder()
                        .companyName(a.getName())
                        .email(a.getEmail())
                        .phone(a.getPhone())
                        .address(a.getAddress())
                        .cuit(a.getCuit())
                        .build())
                .build();
    }
}
