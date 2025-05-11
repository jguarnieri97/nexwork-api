package ar.edu.unlam.tpi.nexwork_api.controller;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("nexwork-api/v1/budgets")
public interface BudgetsController {


    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<List<BudgetResponse>> getBudgets(@RequestParam Long applicantId);

}
