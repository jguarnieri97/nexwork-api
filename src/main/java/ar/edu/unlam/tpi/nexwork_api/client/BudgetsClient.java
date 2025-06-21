    package ar.edu.unlam.tpi.nexwork_api.client;

    import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetFinalizeRequest;
    import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRejectedRequest;
    import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
    import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetUpdateDataRequestDto;
    import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetDetailFromBudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetSupplierResponse;

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
        List<BudgetResponse> getApplicantBudgets(Long applicantId);

        /**
         * Método para obtener todos los presupuestos por id del solicitante
         * desde el servicio Budgets API
         *
         * @param applicantId id del solicitante
         * @return la lista de presupuestos
         */
        List<BudgetSupplierResponse> getSupplierBudgets(Long supplierId);

        /**
         * Método para obtener el detalle de un presupuesto por su id
         * desde el servicio Budgets API
         *
         * @param id id del presupuesto
         * @return el presupuesto detallado
         */
        BudgetDetailFromBudgetsClient getBudgetDetail(String id);

        /**
         * Método para crear un nuevo presupuesto
         * desde el servicio Budgets API
         *
         * @param budgetRequest el presupuesto a crear
         */
        void createBudget(BudgetRequest budgetRequest);

        /**
         * Método para finalizar un presupuesto y contratar un proveedor
         * desde el servicio Budgets API
         *
         * @param id id del presupuesto
         * @param budgetFinalizeRequest el presupuesto seleccionado para contratar
         */
        void finalizeBudget(String id, BudgetFinalizeRequest budgetFinalizeRequest);
        
        /**
     * Método para actualizar un presupuesto
     * desde el servicio Budgets API
     *
     * @param budgetId id del presupuesto a actualizar
     * @param supplierId id del proveedor que actualiza el presupuesto
     * @param budgetRequest los datos a actualizar en el presupuesto
     */
    
    void updateBudget(String budgetId, Long supplierId, BudgetUpdateDataRequestDto budgetRequest);

        /**
         * Método para finalizar el estado de una solicitud de presupuesto
         * desde el servicio Budgets API
         *
         * @param budgetId id del presupuesto
         */
        void finalizeBudgetRequestState(String budgetId);

        /**
         * Método para rechazar un presupuesto
         * desde el servicio Budgets API
         *
         * @param id id del presupuesto
         * @param budgetRejectedRequest contiene el id del supplier
         */
        void rejectBudget(Long id, BudgetRejectedRequest budgetRejectedRequest);

    }
