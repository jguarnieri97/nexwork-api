package ar.edu.unlam.tpi.nexwork_api.controller.impl;


import ar.edu.unlam.tpi.nexwork_api.dto.*;
import ar.edu.unlam.tpi.nexwork_api.dto.request.ContractsFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.WorkContractService;
import ar.edu.unlam.tpi.nexwork_api.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkContractControllerImplTest {

    @Mock
    private WorkContractService workContractService;

    @InjectMocks
    private WorkContractControllerImpl workContractController;

    private WorkContractRequest workContractRequest;
    private WorkContractCreateRequest workContractCreateRequest;
    private ContractsFinalizeRequest workContractFinalizeRequest;
    private WorkContractResponse workContractResponse;
    private List<WorkContractResponse> workContractResponseList;

    @BeforeEach
    void setUp() {
        // Configuración de datos de prueba
        workContractRequest = WorkContractRequest.builder()
                .id(1L)
                .accountType("worker")
                .limit(true)
                .build();

        workContractCreateRequest = WorkContractCreateRequest.builder()
                .price(150000.0)
                .dateFrom("2025-05-16T09:00:00Z")
                .dateTo("2025-05-22T18:00:00Z")
                .detail("Trabajo de pintura")
                .supplierId(1L)
                .applicantId(2L)
                .workers(Arrays.asList(4L, 5L, 6L))
                .build();

        workContractFinalizeRequest = ContractsFinalizeRequest.builder()
                .detail("Trabajo finalizado correctamente")
                .build();

        workContractResponse = WorkContractResponse.builder()
                .id(1L)
                .price(150000.0)
                .dateFrom("2025-05-16T09:00:00Z")
                .dateTo("2025-05-22T18:00:00Z")
                .state("ACTIVE")
                .detail("Trabajo de pintura")
                .supplierId(1L)
                .applicantId(2L)
                .build();

        workContractResponseList = Arrays.asList(workContractResponse);
    }

    @Test
    void getContracts_ShouldReturnGenericResponseWithContractList() {
        // Arrange
        when(workContractService.getContracts(any(WorkContractRequest.class)))
                .thenReturn(workContractResponseList);

        // Act
        GenericResponse<List<WorkContractResponse>> result = workContractController.getContracts(workContractRequest);

        // Assert
        assertNotNull(result);
        assertEquals(Constants.STATUS_OK, result.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, result.getMessage());
        assertEquals(workContractResponseList, result.getData());
        verify(workContractService, times(1)).getContracts(workContractRequest);
    }

    @Test
    void createContract_ShouldReturnGenericResponseWithCreatedContract() {
        // Arrange
        when(workContractService.createContract(any(WorkContractCreateRequest.class)))
                .thenReturn(workContractResponse);

        // Act
        GenericResponse<WorkContractResponse> result = workContractController.createContract(workContractCreateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(Constants.STATUS_CREATED, result.getCode());
        assertEquals(Constants.CREATED_MESSAGE, result.getMessage());
        assertEquals(workContractResponse, result.getData());
        verify(workContractService, times(1)).createContract(workContractCreateRequest);
    }

    @Test
    void finalizeContract_ShouldCallService() {
        // Arrange
        Long contractId = 1L;
        doNothing().when(workContractService).finalizeContract(anyLong(), any());

        // Act
        workContractController.finalizeContract(contractId, workContractFinalizeRequest);

        // Assert
        verify(workContractService, times(1)).finalizeContract(contractId, workContractFinalizeRequest);
    }

    @Test
    void getContractById_ShouldReturnGenericResponseWithContract() {
        // Arrange
        Long contractId = 1L;
        when(workContractService.getContractById(anyLong()))
                .thenReturn(workContractResponse);

        // Act
        GenericResponse<WorkContractResponse> result = workContractController.getContractById(contractId);

        // Assert
        assertNotNull(result);
        assertEquals(Constants.STATUS_OK, result.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, result.getMessage());
        assertEquals(workContractResponse, result.getData());
        verify(workContractService, times(1)).getContractById(contractId);
    }
} 