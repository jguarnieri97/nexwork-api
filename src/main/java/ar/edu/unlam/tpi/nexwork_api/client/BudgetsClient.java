package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;

import java.util.List;

/**
 * Cliente para conectar con el servicio Budgets API
 */
public interface BudgetsClient {

    /**
     * Método para obtener todos los presupuestos por id del solicitante
     * desde el servicio Budgets API
     *
     * @param applicantId id del solicitante
     * @return la lista de presupuestos
     */
    List<BudgetResponse> getBudgets(Long applicantId);

}
