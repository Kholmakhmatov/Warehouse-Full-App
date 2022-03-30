package uz.kholmakhmatov.warehouseapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kholmakhmatov.warehouseapp.entity.Warehouse;


import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Page<Warehouse> findAllByActiveTrue(Pageable pageable);

    Optional<Warehouse> findByName(String name);

    Optional<Warehouse> findByIdAndActiveTrue(Long id);

    Optional<Warehouse> findByNameAndActiveFalse(String name);
}
