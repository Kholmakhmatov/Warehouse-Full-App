package uz.kholmakhmatov.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kholmakhmatov.warehouseapp.entity.OutputProduct;


@Repository
public interface OutputProductRepository extends JpaRepository<OutputProduct, Long> {
}
