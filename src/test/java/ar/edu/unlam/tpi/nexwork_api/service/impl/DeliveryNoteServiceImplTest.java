package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.DeliveryNoteRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.UserResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.DeliveryNoteDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.WorkContractDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryNoteServiceImplTest {

    @Mock
    private WorkContractClient workContractClient;

    @Mock
    private AccountsClient accountsClient;

    @InjectMocks
    private DeliveryNoteServiceImpl deliveryNoteService;

    @Test
    void givenValidContractIdWhenBuildDeliveryNoteThenSuccess() {
        // Given
        Long contractId = 1L;
        WorkContractDetailResponse mockContract = WorkContractDataHelper.createWorkContractDetailResponse(contractId);
        UserResponse mockUserResponse = AccountDataHelper.createUserResponse();

        when(workContractClient.getContractById(contractId)).thenReturn(mockContract);
        when(accountsClient.getAccountById(anyList())).thenReturn(mockUserResponse);
        doNothing().when(workContractClient).createDeliveryNote(any(DeliveryNoteRequest.class));

        // When
        assertDoesNotThrow(() -> deliveryNoteService.buildDeliveryNote(contractId));

        // Then
        verify(workContractClient).getContractById(contractId);
        verify(accountsClient).getAccountById(anyList());
        verify(workContractClient).createDeliveryNote(any(DeliveryNoteRequest.class));
    }

    @Test
    void givenInvalidContractIdWhenBuildDeliveryNoteThenThrowException() {
        // Given
        Long contractId = 1L;
        when(workContractClient.getContractById(contractId)).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> deliveryNoteService.buildDeliveryNote(contractId));
        verify(workContractClient).getContractById(contractId);
        verify(accountsClient, never()).getAccountById(anyList());
        verify(workContractClient, never()).createDeliveryNote(any(DeliveryNoteRequest.class));
    }
}
