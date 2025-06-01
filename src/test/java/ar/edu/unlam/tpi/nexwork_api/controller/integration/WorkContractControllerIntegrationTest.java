package ar.edu.unlam.tpi.nexwork_api.controller.integration;


import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;
import ar.edu.unlam.tpi.nexwork_api.utils.WorkContractDataHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkContractControllerIntegrationTest {
/* 
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkContractClient workContractClient;

    @MockBean
    private DeliveryNoteService deliveryNoteService;

    @Test
    void testGetContracts() throws Exception {
        WorkContractRequest request = WorkContractDataHelper.buildRequest(1L, "SUPPLIER");
        List<WorkContractResponse> responseList = WorkContractDataHelper.buildResponseList();

        when(workContractClient.getContracts(request)).thenReturn(responseList);

        mockMvc.perform(post("/nexwork-api/v1/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    void testGetContractById() throws Exception {
        Long id = 1L;
        WorkContractResponse response = WorkContractDataHelper.buildResponse(id);

        when(workContractClient.getContractById(id)).thenReturn(response);

        mockMvc.perform(get("/nexwork-api/v1/contracts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(id));
    }

    @Test
    void testCreateContract() throws Exception {
        WorkContractCreateRequest request = WorkContractDataHelper.buildCreateRequest();
        WorkContractResponse response = WorkContractDataHelper.buildResponse(10L);

        when(workContractClient.createContract(request)).thenReturn(response);

        mockMvc.perform(post("/nexwork-api/v1/contracts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data.id").value(10));
    }*/
}

