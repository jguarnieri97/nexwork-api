package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.CompanyData;
import ar.edu.unlam.tpi.nexwork_api.dto.request.DeliveryNoteRequest;

import java.util.List;

public class DeliveryNoteDataHelper {

    public static DeliveryNoteRequest createDeliveryNoteRequest() {
        return DeliveryNoteRequest.builder()
                .contractId(1L)
                .supplierData(createCompanyDataList("Supplier"))
                .applicantData(createCompanyDataList("Applicant"))
                .build();
    }

    public static List<CompanyData> createCompanyDataList(String type) {
        return List.of(
                CompanyData.builder()
                        .companyName(type + " Company 1")
                        .email(type.toLowerCase() + "1@example.com")
                        .phone("123456789")
                        .address("Address 1")
                        .cuit("20-12345678-1")
                        .build(),
                CompanyData.builder()
                        .companyName(type + " Company 2")
                        .email(type.toLowerCase() + "2@example.com")
                        .phone("987654321")
                        .address("Address 2")
                        .cuit("20-87654321-2")
                        .build()
        );
    }
}
