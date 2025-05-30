package ar.edu.unlam.tpi.nexwork_api.service.impl;
import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.ContractsFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;
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
class WorkContractServiceImplTest {

    @Mock
    private WorkContractClient workContractClient;

    @Mock
    private DeliveryNoteService deliveryNoteService;


    @InjectMocks
    private WorkContractServiceImpl workContractService;

    @Test
    void getContracts_ShouldReturnContractList() {
        WorkContractRequest request = WorkContractDataHelper.buildRequest(1L, "worker");
        List<WorkContractResponse> expected = WorkContractDataHelper.buildResponseList();

        when(workContractClient.getContracts(any(WorkContractRequest.class))).thenReturn(expected);

        List<WorkContractResponse> result = workContractService.getContracts(request);

        assertNotNull(result);
        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0).getId(), result.get(0).getId());
    }

    @Test
    void createContract_ShouldReturnCreatedContract() {
        WorkContractCreateRequest request = WorkContractDataHelper.buildCreateRequest();
        WorkContractResponse expected = WorkContractDataHelper.buildResponse(99L);

        when(workContractClient.createContract(any(WorkContractCreateRequest.class))).thenReturn(expected);

        WorkContractResponse result = workContractService.createContract(request);

        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getPrice(), result.getPrice());
    }

    @Test
void finalizeContract_ShouldCallClient() {
    Long id = 1L;
    ContractsFinalizeRequest request = WorkContractDataHelper.buildFinalizeRequest();

    doNothing().when(workContractClient).finalizeContract(anyLong(), any());
    doNothing().when(deliveryNoteService).buildDeliveryNote(id); // 👈 mock necesario

    workContractService.finalizeContract(id, request);

    verify(workContractClient, times(1)).finalizeContract(eq(id), any());
    verify(deliveryNoteService, times(1)).buildDeliveryNote(id);
}

    @Test
    void getContractById_ShouldReturnContract() {
        Long id = 1L;
        WorkContractResponse expected = WorkContractDataHelper.buildResponse(id);

        when(workContractClient.getContractById(anyLong())).thenReturn(expected);

        WorkContractResponse result = workContractService.getContractById(id);

        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getPrice(), result.getPrice());
    }
}