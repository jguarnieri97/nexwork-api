package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.ContractsFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.DeliveryNoteRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractCreateRequest;
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

    /**
     * Método para crear un nuevo contrato laboral
     *
     * @param request datos del contrato a crear
     * @return datos del contrato creado
     */
    WorkContractResponse createContract(WorkContractCreateRequest request);

    /**
     * Método para finalizar un contrato laboral
     *
     * @param id      id del contrato a finalizar
     * @param request datos del contrato a finalizar
     * @return datos del contrato finalizado
     */
    void finalizeContract(Long id, ContractsFinalizeRequest request);

    /**
     * Método para obtener un contrato laboral por su ID
     *
     * @param id id del contrato a obtener
     * @return datos del contrato obtenido
     */
    WorkContractResponse getContractById(Long id);

    /**
     * Método para crear una nota de entrega
     *
     * @param request datos de la nota de entrega
     * @return datos de la nota de entrega creada
     */
    void createDeliveryNote(DeliveryNoteRequest request);

}
