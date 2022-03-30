package uz.kholmakhmatov.warehouseapp.dto;

import lombok.Data;

import java.util.Set;

@Data
public class WorkerDto {
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String password;
    private Set<Long> warehousesId;
}
