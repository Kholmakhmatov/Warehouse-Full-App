package uz.kholmakhmatov.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kholmakhmatov.warehouseapp.entity.Output;


@Repository
public interface OutputRepository extends JpaRepository<Output, Long> {
}
