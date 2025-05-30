package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.AccountDetailResponse;

public interface AccountsClient {

    AccountDetailResponse getAccountById(AccountDetailRequest request);

}
