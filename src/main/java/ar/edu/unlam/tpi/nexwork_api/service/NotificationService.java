package ar.edu.unlam.tpi.nexwork_api.service;

import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;

public interface NotificationService {

    /* *
     * Notifica a los proveedores sobre una solicitud de presupuesto.
     *
     * @param budgetRequest la solicitud de presupuesto que se va a notificar
     */
    void notifySuppliersOfBudgetRequest(BudgetRequest budgetRequest);

    /**
     * Notifica al proveedor sobre un contrato de trabajo creado.
     *
     * @param response la respuesta del contrato de trabajo que se va a notificar
     */
    void notifyApplicantOfContract(WorkContractResponse response);

    /**
     * Notifica al solicitante y proveedor sobre los detalles de un contrato de trabajo finalizado.
     *
     * @param response la respuesta del detalle del contrato de trabajo que se va a notificar
     */
    void notifyContractFinalized(WorkContractDetailResponse response);

}