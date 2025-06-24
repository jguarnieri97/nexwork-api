package ar.edu.unlam.tpi.nexwork_api.service.impl;
import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractUpdateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.*;
import ar.edu.unlam.tpi.nexwork_api.exceptions.WorkContractClientException;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;
import ar.edu.unlam.tpi.nexwork_api.service.NotificationService;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.WorkContractDataHelper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkContractServiceImplTest {

    @Mock
    private WorkContractClient workContractClient;

    @Mock
    private AccountsClient accountsClient;

    @Mock
    private BudgetsClient budgetsClient;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private WorkContractServiceImpl workContractService;

    @Mock
    private DeliveryNoteService deliveryNoteService;


    @Test
    void givenValidRequestWhenGetContractsThenReturnContractList() {
        // Given
        WorkContractRequest request = WorkContractDataHelper.createWorkContractRequest(1L, "worker");
        List<WorkContractResponse> expectedContracts = WorkContractDataHelper.createWorkContractResponseList();
        when(workContractClient.getContracts(request)).thenReturn(expectedContracts);

        // When
        List<WorkContractResponse> result = workContractService.getContracts(request);

        // Then
        assertNotNull(result);
        assertEquals(expectedContracts.size(), result.size());
        assertEquals(expectedContracts.get(0).getId(), result.get(0).getId());
        verify(workContractClient, times(1)).getContracts(request);
    }

    @Test
    void givenValidRequestWhenCreateContractThenReturnCreatedContract() {
        // Given
        WorkContractCreateRequest request = WorkContractDataHelper.createWorkContractCreateRequest();
        WorkContractResponse expectedResponse = WorkContractDataHelper.createWorkContractResponse(99L);
        when(workContractClient.createContract(request)).thenReturn(expectedResponse);
        doNothing().when(budgetsClient).finalizeBudgetRequestState(request.getBudgetId());
        when(accountsClient.getAccountById(anyList())).thenReturn(AccountDataHelper.createUserResponse());

        // When
        WorkContractResponse result = workContractService.createContract(request);

        // Then
        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getPrice(), result.getPrice());
        verify(workContractClient, times(1)).createContract(request);
        verify(budgetsClient, times(1)).finalizeBudgetRequestState(request.getBudgetId());
    }

    @Test
    void givenValidIdWhenGetContractByIdThenReturnWorkContractDetailResponse() {
        // Given
        Long contractId = 1L;
        WorkContractDetailResponse mockContract = WorkContractDataHelper.createWorkContractDetailResponse(contractId);
        UserResponse mockUserResponse = AccountDataHelper.createUserResponse();

        when(workContractClient.getContractById(contractId)).thenReturn(mockContract);
        when(accountsClient.getAccountById(anyList())).thenReturn(mockUserResponse);

        // When
        WorkContractDetailResponseDto result = workContractService.getContractById(contractId);

        // Then
        assertNotNull(result);
        verify(workContractClient).getContractById(contractId);
        verify(accountsClient).getAccountById(anyList());
    }

    @Test
    void givenValidIdAndRequestWhenFinalizeContractThenSuccess() {
        // Given
        Long contractId = 1L;
        WorkContractFinalizeRequest request = WorkContractDataHelper.createContractsFinalizeRequest();

        doNothing().when(workContractClient).updateContractState(eq(contractId), any(WorkContractUpdateRequest.class));
        doNothing().when(deliveryNoteService).buildDeliveryNote(contractId);
        doNothing().when(notificationService).notifyContractFinalized(any());

        // When
        assertDoesNotThrow(() -> workContractService.finalizeContract(contractId, request));

        // Then
        verify(workContractClient).updateContractState(eq(contractId), any(WorkContractUpdateRequest.class));
        verify(deliveryNoteService).buildDeliveryNote(contractId);
    }

    @Test
    void givenErrorWhenFinalizeContractThenThrowException() {
        // Given
        Long contractId = 1L;
        WorkContractFinalizeRequest request = WorkContractDataHelper.createContractsFinalizeRequest();

        doThrow(new RuntimeException("Error")).when(workContractClient).updateContractState(eq(contractId), any(WorkContractUpdateRequest.class));

        // When & Then
        WorkContractClientException exception = assertThrows(WorkContractClientException.class, () -> workContractService.finalizeContract(contractId, request));
        assertEquals("CONTRACT_FINALIZATION_ERROR", exception.getMessage());
        verify(workContractClient).updateContractState(eq(contractId), any(WorkContractUpdateRequest.class));
        verify(deliveryNoteService, never()).buildDeliveryNote(contractId);
    }


    @Test
    void givenValidContractIdWhenGetDeliveryNoteByIdThenReturnDeliveryNoteResponse() {
        // Given
        Long contractId = 1L;
        DeliveryNoteResponse mockResponse = new DeliveryNoteResponse();
        when(workContractClient.getDeliveryNoteById(contractId)).thenReturn(mockResponse);

        // When
        DeliveryNoteResponse result = workContractService.getDeliveryNoteById(contractId);

        // Then
        assertNotNull(result);
        verify(workContractClient).getDeliveryNoteById(contractId);
    }

    @Test
    void givenInvalidContractIdWhenGetDeliveryNoteByIdThenThrowException() {
        // Given
        Long contractId = 1L;
        when(workContractClient.getDeliveryNoteById(contractId)).thenReturn(null);

        // When & Then
        WorkContractClientException exception = assertThrows(WorkContractClientException.class, () -> workContractService.getDeliveryNoteById(contractId));
        assertEquals("DELIVERY_NOTE_NOT_FOUND", exception.getMessage());
        verify(workContractClient).getDeliveryNoteById(contractId);
    }

}