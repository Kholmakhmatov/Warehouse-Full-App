package uz.kholmakhmatov.warehouseapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kholmakhmatov.warehouseapp.entity.Category;


import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByActiveTrue(Pageable pageable);

    Optional<Category> findByName(String name);

    Optional<Category> findByIdAndActiveTrue(Long id);

    Optional<Category> findByNameAndActiveFalse(String name);

    Optional<Category> findByNameAndActiveTrue(String name);
}
