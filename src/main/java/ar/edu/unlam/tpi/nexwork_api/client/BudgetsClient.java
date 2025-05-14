package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponseDetail;

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

    /**
     * Método para obtener el detalle de un presupuesto por su id
     * desde el servicio Budgets API
     *
     * @param id id del presupuesto
     * @return el presupuesto detallado
     */
    BudgetResponseDetail getBudgetDetail(String id);

    /**
     * Método para crear un nuevo presupuesto
     * desde el servicio Budgets API
     *
     * @param budgetRequest el presupuesto a crear
     */
    void createBudget(BudgetRequest budgetRequest);

}
