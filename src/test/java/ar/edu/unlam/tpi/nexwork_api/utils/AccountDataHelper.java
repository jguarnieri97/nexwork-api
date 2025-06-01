package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.response.AccountDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.UserResponse;

import java.util.List;

public class AccountDataHelper {

    public static UserResponse createUserResponse() {
        return UserResponse.builder()
                .applicants(createAccountDetailList("Applicant"))
                .suppliers(createAccountDetailList("Supplier"))
                .workers(createAccountDetailList("Worker"))
                .build();
    }

    private static List<AccountDetailResponse> createAccountDetailList(String type) {
        return List.of(
                AccountDetailResponse.builder()
                        .id(1L)
                        .name(type + " 1")
                        .email(type.toLowerCase() + "1@example.com")
                        .build(),
                AccountDetailResponse.builder()
                        .id(2L)
                        .name(type + " 2")
                        .email(type.toLowerCase() + "2@example.com")
                        .build()
        );
    }

}
