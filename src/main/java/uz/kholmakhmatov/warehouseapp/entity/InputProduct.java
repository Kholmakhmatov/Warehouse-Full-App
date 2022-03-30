package uz.kholmakhmatov.warehouseapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InputProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Product product;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate expireDate;

    @Column(nullable = false)
    private Double amount;

    private Double price;

    @ManyToOne
    private Input input;

}
