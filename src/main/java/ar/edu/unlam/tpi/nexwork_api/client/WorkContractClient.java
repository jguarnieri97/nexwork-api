package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractUpdateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.DeliveryNoteRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.DeliveryNoteResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;

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
     * Método para actualizar un contrato laboral
     *
     * @param id      id del contrato a actualizar
     * @param request datos del contrato a actualizar
     * @return datos del contrato actualizado
     */
    void updateContractState(Long id, WorkContractUpdateRequest request);

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

    /**
     * Método para obtener una nota de entrega por su ID
     *
     * @param id id de la nota de entrega a obtener
     * @return datos de la nota de entrega obtenida
     */
    DeliveryNoteResponse getDeliveryNoteById(Long id);

}
