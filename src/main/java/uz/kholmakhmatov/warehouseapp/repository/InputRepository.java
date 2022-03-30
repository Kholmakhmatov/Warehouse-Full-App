package uz.kholmakhmatov.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kholmakhmatov.warehouseapp.entity.Input;


@Repository
public interface InputRepository extends JpaRepository<Input, Long> {
}
