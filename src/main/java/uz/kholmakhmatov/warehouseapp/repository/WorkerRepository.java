package uz.kholmakhmatov.warehouseapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kholmakhmatov.warehouseapp.entity.Worker;


import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByPhoneNumber(String phoneNumber);

    Page<Worker> findAllByActiveTrue(Pageable pageable);

    Optional<Worker> findByIdAndActiveTrue(Long id);

    Optional<Worker> findByPhoneNumberAndActiveFalse(String phoneNumber);

    Page<Worker> findAllByWarehouseIdAndActiveTrue(Long warehouse_id, Pageable pageable);
}
