package uz.kholmakhmatov.warehouseapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private String password;

    @JsonIgnore
    private String code = UUID.randomUUID().toString();

    @JsonIgnore
    private boolean active = true;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Warehouse> warehouse;
}
