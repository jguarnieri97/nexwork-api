package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.*;
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
class WorkContractServiceImplTest {

    @Mock
    private WorkContractClient workContractClient;

    @InjectMocks
    private WorkContractServiceImpl workContractService;

    private WorkContractRequest workContractRequest;
    private WorkContractCreateRequest workContractCreateRequest;
    private WorkContractFinalizeRequest workContractFinalizeRequest;
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

        workContractFinalizeRequest = WorkContractFinalizeRequest.builder()
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
    void getContracts_ShouldReturnContractList() {
        // Arrange
        when(workContractClient.getContracts(any(WorkContractRequest.class)))
                .thenReturn(workContractResponseList);

        // Act
        List<WorkContractResponse> result = workContractService.getContracts(workContractRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(workContractResponse.getId(), result.get(0).getId());
        verify(workContractClient, times(1)).getContracts(workContractRequest);
    }

    @Test
    void createContract_ShouldReturnCreatedContract() {
        // Arrange
        when(workContractClient.createContract(any(WorkContractCreateRequest.class)))
                .thenReturn(workContractResponse);

        // Act
        WorkContractResponse result = workContractService.createContract(workContractCreateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(workContractResponse.getId(), result.getId());
        assertEquals(workContractResponse.getPrice(), result.getPrice());
        verify(workContractClient, times(1)).createContract(workContractCreateRequest);
    }

    @Test
    void finalizeContract_ShouldCallClient() {
        // Arrange
        Long contractId = 1L;
        doNothing().when(workContractClient).finalizeContract(anyLong(), any(WorkContractFinalizeRequest.class));

        // Act
        workContractService.finalizeContract(contractId, workContractFinalizeRequest);

        // Assert
        verify(workContractClient, times(1)).finalizeContract(contractId, workContractFinalizeRequest);
    }

    @Test
    void getContractById_ShouldReturnContract() {
        // Arrange
        Long contractId = 1L;
        when(workContractClient.getContractById(anyLong()))
                .thenReturn(workContractResponse);

        // Act
        WorkContractResponse result = workContractService.getContractById(contractId);

        // Assert
        assertNotNull(result);
        assertEquals(workContractResponse.getId(), result.getId());
        assertEquals(workContractResponse.getPrice(), result.getPrice());
        verify(workContractClient, times(1)).getContractById(contractId);
    }
} 