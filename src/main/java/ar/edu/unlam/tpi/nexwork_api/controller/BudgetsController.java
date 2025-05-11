package ar.edu.unlam.tpi.nexwork_api.controller;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para las operaciones relacionadas con Presupuestos
 */
@RequestMapping("nexwork-api/v1/budgets")
public interface BudgetsController {


    /**
     * Recurso para obtener una lista de todos los presupuestos
     * según el solicitante
     *
     * @param applicantId: id del solicitante
     * @return la lista de presupuestos
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<List<BudgetResponse>> getBudgets(@RequestParam Long applicantId);

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<BudgetResponseDetail> getBudgetDetail(@PathVariable Long id);

}
