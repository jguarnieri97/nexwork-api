package ar.edu.unlam.tpi.nexwork_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationCreateRequest {
    private Long userId;
    private String userType;
    private Boolean inMail;
    private String content;
    private EmailCreateRequest emailCreateRequest;
}
