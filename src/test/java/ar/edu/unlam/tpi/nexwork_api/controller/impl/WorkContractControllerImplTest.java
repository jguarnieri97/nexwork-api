package ar.edu.unlam.tpi.nexwork_api.controller.impl;

import ar.edu.unlam.tpi.nexwork_api.dto.request.ContractsFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.WorkContractService;
import ar.edu.unlam.tpi.nexwork_api.utils.Constants;
import ar.edu.unlam.tpi.nexwork_api.utils.WorkContractDataHelper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkContractControllerImplTest {
/* 
    @Mock
    private WorkContractService workContractService;

    @InjectMocks
    private WorkContractControllerImpl workContractController;

    @Test
    void testGetContracts() {
        WorkContractRequest request = WorkContractDataHelper.buildRequest(1L, "SUPPLIER");
        List<WorkContractResponse> expectedList = WorkContractDataHelper.buildResponseList();

        when(workContractService.getContracts(request)).thenReturn(expectedList);

        GenericResponse<List<WorkContractResponse>> response = workContractController.getContracts(request);

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals(2, response.getData().size());
    }

    @Test
    void testCreateContract() {
        WorkContractCreateRequest request = WorkContractDataHelper.buildCreateRequest();
        WorkContractResponse expected = WorkContractDataHelper.buildResponse(10L);

        when(workContractService.createContract(request)).thenReturn(expected);

        GenericResponse<WorkContractResponse> response = workContractController.createContract(request);

        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getCode());
        assertEquals(expected.getId(), response.getData().getId());
    }

    @Test
    void testGetContractById() {
        Long id = 123L;
        WorkContractResponse expected = WorkContractDataHelper.buildResponse(id);

        when(workContractService.getContractById(id)).thenReturn(expected);

        GenericResponse<WorkContractResponse> response = workContractController.getContractById(id);

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(id, response.getData().getId());
    }

    @Test
    void testFinalizeContract() {
        Long id = 123L;
        ContractsFinalizeRequest request = WorkContractDataHelper.buildFinalizeRequest();

        doNothing().when(workContractService).finalizeContract(id, request);

        GenericResponse<Void> response = workContractController.finalizeContract(id, request);

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
    }*/
}
