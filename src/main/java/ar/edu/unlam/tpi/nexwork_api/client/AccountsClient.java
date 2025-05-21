package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.AccountDetailResponse;

public interface AccountsClient {

    AccountDetailResponse getAccountById(AccountDetailRequest request);

}
