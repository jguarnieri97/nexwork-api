package ar.edu.unlam.tpi.nexwork_api.utils;

import org.springframework.stereotype.Component;

import ar.edu.unlam.tpi.nexwork_api.dto.request.ContractsFinalizeRequest;

@Component
public class WorkContractFinalizeBuilder {
    

    private static final String CONTRACT_FINALIZED = "FINALIZED";
    
        public ContractsFinalizeRequest buildFinalizeRequest(ContractsFinalizeRequest request) {
            return ContractsFinalizeRequest.builder()
                    .state(CONTRACT_FINALIZED)
                .detail(request.getDetail())
                .files(request.getFiles())
                .build();
    }
}
