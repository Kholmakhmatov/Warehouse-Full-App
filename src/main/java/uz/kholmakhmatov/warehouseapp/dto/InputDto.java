package uz.kholmakhmatov.warehouseapp.dto;

import lombok.Data;

@Data
public class InputDto {
    private Long currencyId;
    private Long warehouseId;
    private Long supplierId;
    private String factureNumber;
}
