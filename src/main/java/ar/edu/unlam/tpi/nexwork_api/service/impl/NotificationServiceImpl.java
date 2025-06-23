package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.NotificationsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetDataRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.EmailCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.NotificationService;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountTypeEnum;
import ar.edu.unlam.tpi.nexwork_api.utils.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationsClient notificationsClient;

    @Value("${notification.url}")
    private String notificationUrl;

    @Override
    public void notifySuppliersOfBudgetRequest(BudgetRequest budgetRequest) {
        for (BudgetDataRequest request : budgetRequest.getSuppliers()) {
            Map<String, String> templateVars = buildTemplateVarsBudget(budgetRequest, request);
            NotificationCreateRequest notification = buildNotificationBudget(request, templateVars);
            try {
                notificationsClient.createNotification(notification);
                log.info("Notificación enviada al proveedor con id {}", request.getSupplierId());
            } catch (Exception e) {
                log.error("Error al enviar notificación al proveedor con id {}: {}", request.getSupplierId(), e.getMessage());
            }
        }
    }

    @Override
    public void notifyApplicantOfContract(WorkContractResponse response) {
        try {
            Map<String, String> templateVars = buildTemplateVarsContract(response);
            NotificationCreateRequest notification = buildNotificationContract(response, templateVars);
            notificationsClient.createNotification(notification);
            log.info("Notificación de contrato enviada al proveedor con id {}", response.getSupplierId());
        } catch (Exception e) {
            log.error("Error al enviar notificación de contrato al proveedor con id {}: {}", response.getSupplierId(), e.getMessage());
        }
    }

    private Map<String, String> buildTemplateVarsBudget(BudgetRequest budgetRequest, BudgetDataRequest request) {
        Map<String, String> templateVars = new HashMap<>();
        templateVars.put("supplierName", request.getSupplierName());
        templateVars.put("applicantName", budgetRequest.getApplicantName());
        templateVars.put("resume", budgetRequest.getWorkResume());
        templateVars.put("detail", budgetRequest.getWorkDetail());
        templateVars.put("url", notificationUrl);
        return templateVars;
    }

    private NotificationCreateRequest buildNotificationBudget(BudgetDataRequest request, Map<String, String> templateVars) {
        return NotificationCreateRequest.builder()
                .userId(request.getSupplierId())
                .userType(AccountTypeEnum.SUPPLIER.getValue().toUpperCase())
                .inMail(true)
                .content("Nueva solicitud de presupuesto recibida")
                .emailCreateRequest(
                                EmailCreateRequest.builder()
                                .type("BUDGET")
                                .subject("Nueva solicitud de presupuesto recibida")
                                .templateVariables(templateVars)
                                .build()
                )
                .build();
    }

    private Map<String, String> buildTemplateVarsContract(WorkContractResponse response) {
        Map<String, String> templateVars = new HashMap<>();
        templateVars.put("supplierName", response.getSupplierName());
        templateVars.put("applicantName", response.getApplicantName());
        templateVars.put("price", String.valueOf(response.getPrice()));
        templateVars.put("start", response.getDateFrom());
        templateVars.put("budgetId", response.getCodeNumber());
        templateVars.put("end", response.getDateTo());
        templateVars.put("url", notificationUrl);
        return templateVars;
    }

    private NotificationCreateRequest buildNotificationContract(WorkContractResponse response, Map<String, String> templateVars) {
        return NotificationCreateRequest.builder()
                .userId(response.getApplicantId())
                .userType(AccountTypeEnum.APPLICANT.getValue().toUpperCase())
                .inMail(true)
                .content("Se ha agendando un nuevo trabajo.")
                .emailCreateRequest(
                                EmailCreateRequest.builder()
                                .type(NotificationType.CONTRACT_EMAIL.toString())
                                .subject("Nuevo trabajo confirmado")
                                .templateVariables(templateVars)
                                .build()
                )
                .build();
    }
}