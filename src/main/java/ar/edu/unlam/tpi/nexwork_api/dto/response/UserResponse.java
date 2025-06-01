package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private List<AccountDetailResponse> applicants;
    private List<AccountDetailResponse> suppliers;
    private List<AccountDetailResponse> workers;
}