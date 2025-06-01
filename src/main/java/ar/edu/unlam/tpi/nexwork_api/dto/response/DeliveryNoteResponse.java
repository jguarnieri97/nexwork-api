package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Data;

@Data
public class DeliveryNoteResponse {
    private Long id;
    private byte[] data;
    private String txHash;
    private String dataHash;
    private String blockNumber;
    private String createdAt;
}