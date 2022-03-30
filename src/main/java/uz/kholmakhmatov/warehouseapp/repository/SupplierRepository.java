package uz.kholmakhmatov.warehouseapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kholmakhmatov.warehouseapp.entity.Supplier;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Page<Supplier> findAllByActiveTrue(Pageable pageable);

    Optional<Supplier> findByPhone(String phone);

    Optional<Supplier> findByIdAndActiveTrue(Long id);

    Optional<Supplier> findByPhoneAndActiveFalse(String phone);
}
