package uz.kholmakhmatov.warehouseapp.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private Long categoryId;
    private Long photoId;
    private Long measurementId;
}
