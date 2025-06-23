package ar.edu.unlam.tpi.nexwork_api.service;

import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;

public interface NotificationService {

    /* *
     * Notifica a los proveedores sobre una solicitud de presupuesto.
     *
     * @param budgetRequest la solicitud de presupuesto que se va a notificar
     */
    void notifySuppliersOfBudgetRequest(BudgetRequest budgetRequest);
}