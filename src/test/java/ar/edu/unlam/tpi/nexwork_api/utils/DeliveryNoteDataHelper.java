package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.CompanyData;
import ar.edu.unlam.tpi.nexwork_api.dto.request.DeliveryNoteRequest;

import java.util.List;

public class DeliveryNoteDataHelper {

    public static DeliveryNoteRequest createDeliveryNoteRequest() {
        return DeliveryNoteRequest.builder()
                .contractId(1L)
                .supplierData(createCompanyData("Supplier"))
                .applicantData(createCompanyData("Applicant"))
                .build();
    }

    public static CompanyData createCompanyData(String type) {
        return CompanyData.builder()
                .companyName(type + " Company 1")
                .email(type.toLowerCase() + "1@example.com")
                .phone("123456789")
                .address("Address 1")
                .cuit("20-12345678-1")
                .build();
    }
}
