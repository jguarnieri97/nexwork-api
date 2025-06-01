package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.UserResponse;

import java.util.List;

public interface AccountsClient {

    UserResponse getAccountById(List<AccountDetailRequest> request);

}
