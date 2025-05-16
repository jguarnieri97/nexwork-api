package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractResponse;

import java.util.List;

public interface WorkContractClient {

     /**
     * Método para obtener contratos laborales según el tipo de cuenta
     *
     * @param request parámetros de búsqueda
     * @return lista de contratos obtenidos
     */
    List<WorkContractResponse> getContracts(WorkContractRequest request);

}
