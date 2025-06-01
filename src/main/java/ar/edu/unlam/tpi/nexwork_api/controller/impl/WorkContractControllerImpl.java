package ar.edu.unlam.tpi.nexwork_api.controller.impl;

import ar.edu.unlam.tpi.nexwork_api.controller.WorkContractController;
import ar.edu.unlam.tpi.nexwork_api.dto.*;
import ar.edu.unlam.tpi.nexwork_api.dto.request.ContractsFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.DeliveryNoteResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.WorkContractService;
import ar.edu.unlam.tpi.nexwork_api.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkContractControllerImpl implements WorkContractController {

    private final WorkContractService workContractService;

    @Override
    public GenericResponse<List<WorkContractResponse>> getContracts(WorkContractRequest request) {
        var contracts = workContractService.getContracts(request);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                contracts
        );
    }

    @Override
    public GenericResponse<WorkContractResponse> createContract(WorkContractCreateRequest request) {
    var contract = workContractService.createContract(request);
    return new GenericResponse<>(
            Constants.STATUS_CREATED,
            Constants.CREATED_MESSAGE,
            contract
    );
}

    @Override
public GenericResponse<Void> finalizeContract(Long id, ContractsFinalizeRequest request) {
    workContractService.finalizeContract(id, request);
    return new GenericResponse<>(
            Constants.STATUS_OK,
            Constants.SUCCESS_MESSAGE,
            null
    );
}

    @Override
    public GenericResponse<WorkContractResponse> getContractById(Long id) {
        var contract = workContractService.getContractById(id);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                contract
            );
        }

        @Override
public GenericResponse<DeliveryNoteResponse> getDeliveryNoteById(Long contractId) {
    var deliveryNote = workContractService.getDeliveryNoteById (contractId);
    return new GenericResponse<>(
            Constants.STATUS_OK,
            Constants.SUCCESS_MESSAGE,
            deliveryNote
    );
}


}
