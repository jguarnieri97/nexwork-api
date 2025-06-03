package ar.edu.unlam.tpi.nexwork_api.controller.impl;

import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractUpdateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.WorkContractClientException;
import ar.edu.unlam.tpi.nexwork_api.service.WorkContractService;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountDataHelper;
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
public class WorkContractControllerImplTest {

    @Mock
    private WorkContractService workContractService;

    @InjectMocks
    private WorkContractControllerImpl workContractController;

    @Test
    void givenValidRequest_whenGetContracts_thenReturnsContractList() {
        // Given
        WorkContractRequest request = WorkContractDataHelper.createWorkContractRequest(1L, "SUPPLIER");
        List<WorkContractResponse> expectedList = WorkContractDataHelper.createWorkContractResponseList();
        when(workContractService.getContracts(request)).thenReturn(expectedList);

        // When
        GenericResponse<List<WorkContractResponse>> response = workContractController.getContracts(request);

        // Then
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals(2, response.getData().size());
        assertEquals(expectedList.get(0).getId(), response.getData().get(0).getId());
        verify(workContractService).getContracts(request);
    }

    @Test
    void givenValidCreateRequest_whenCreateContract_thenReturnsCreatedContract() {
        // Given
        WorkContractCreateRequest request = WorkContractDataHelper.createWorkContractCreateRequest();
        WorkContractResponse expected = WorkContractDataHelper.createWorkContractResponse(10L);
        when(workContractService.createContract(request)).thenReturn(expected);

        // When
        GenericResponse<WorkContractResponse> response = workContractController.createContract(request);

        // Then
        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getCode());
        assertEquals(Constants.CREATED_MESSAGE, response.getMessage());
        assertEquals(expected.getId(), response.getData().getId());
        verify(workContractService).createContract(request);
    }

    @Test
    void givenServiceThrowsException_whenCreateContract_thenThrowsWorkContractClientException() {
        // Given
        WorkContractCreateRequest request = WorkContractDataHelper.createWorkContractCreateRequest();
        when(workContractService.createContract(request)).thenThrow(new WorkContractClientException(ErrorResponse.builder()
                .code(Constants.STATUS_INTERNAL)
                .message(Constants.INTERNAL_ERROR)
                .build()));

        // When & Then
        assertThrows(WorkContractClientException.class, () -> workContractController.createContract(request));
        verify(workContractService).createContract(request);
    }

    @Test
    void givenValidId_whenGetContractById_thenReturnsContractDetail() {
        // Given
        Long id = 123L;
        WorkContractDetailResponse expected = WorkContractDetailResponse.builder()
                .id(id)
                .codeNumber("CONTRACT-123")
                .price(150000.0)
                .dateFrom("2024-03-20")
                .dateTo("2024-03-27")
                .state("ACTIVE")
                .detail("Test contract")
                .suppliers(AccountDataHelper.createUserResponse().getSuppliers())
                .applicants(AccountDataHelper.createUserResponse().getApplicants())
                .workers(List.of(1L, 2L))
                .files(List.of("file1.pdf"))
                .build();
        when(workContractService.getContractById(id)).thenReturn(expected);

        // When
        GenericResponse<WorkContractDetailResponse> response = workContractController.getContractById(id);

        // Then
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals(expected.getId(), response.getData().getId());
        verify(workContractService).getContractById(id);
    }

    @Test
    void givenValidRequest_whenFinalizeContract_thenReturnsSuccessResponse() {
        // Given
        Long id = 123L;
        WorkContractUpdateRequest request = WorkContractDataHelper.createContractsFinalizeRequest();
        doNothing().when(workContractService).finalizeContract(id, request);

        // When
        GenericResponse<Void> response = workContractController.finalizeContract(id, request);

        // Then
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertNull(response.getData());
        verify(workContractService).finalizeContract(id, request);
    }
}
