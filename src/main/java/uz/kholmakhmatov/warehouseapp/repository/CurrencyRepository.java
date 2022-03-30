package uz.kholmakhmatov.warehouseapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kholmakhmatov.warehouseapp.entity.Currency;


import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Page<Currency> findAllByActiveTrue(Pageable pageable);

    Optional<Currency> findByName(String name);

    Optional<Currency> findByIdAndActiveTrue(Long id);

    Optional<Currency> findByNameAndActiveFalse(String name);
}
