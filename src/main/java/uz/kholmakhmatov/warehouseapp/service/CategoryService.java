package uz.kholmakhmatov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.kholmakhmatov.warehouseapp.dto.CategoryDto;
import uz.kholmakhmatov.warehouseapp.entity.Category;
import uz.kholmakhmatov.warehouseapp.repository.CategoryRepository;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;


import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAllByActiveTrue(pageable);
    }

    public ResponseData save(CategoryDto categoryDto) {

        Optional<Category> categoryOptional = categoryRepository.findByNameAndActiveTrue(categoryDto.getName());

        if (categoryOptional.isPresent())
            return new ResponseData("Already exist", false);

        if (categoryDto.getCategoryId() == null) {
            Category category = new Category();
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
            return new ResponseData("Successfully saved", true);
        }

        Optional<Category> parentCategory = categoryRepository.findByIdAndActiveTrue(categoryDto.getCategoryId());

        if (parentCategory.isEmpty())
            return new ResponseData("Category does not exist with id: " + categoryDto.getCategoryId(), false);

        if (categoryDto.getName().equals(parentCategory.get().getName()))
            return new ResponseData("Error", false);

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setParentCategory(parentCategory.get());

        categoryRepository.save(category);
        return new ResponseData("Successfully saved", true);
    }

    public ResponseData findOne(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndActiveTrue(id);
        return optionalCategory.map(category -> new ResponseData("Success", true, optionalCategory.get()))
                .orElseGet(() -> new ResponseData("Category does not exist", false));

    }

    public ResponseData delete(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndActiveTrue(id);

        if (optionalCategory.isEmpty())
            return new ResponseData("Not found", false);

        productService.deleteByCategoryId(id);

        Category category = optionalCategory.get();
        category.setActive(false);
        categoryRepository.save(category);

        return new ResponseData("Successfully deleted", true);
    }

    public ResponseData edit(Long id, CategoryDto categoryDto) {

        Optional<Category> optionalCategory = categoryRepository.findByIdAndActiveTrue(id);
        if (optionalCategory.isEmpty()) {
            return new ResponseData("Category does not exist", false);
        }
        Optional<Category> byName = categoryRepository.findByNameAndActiveFalse(categoryDto.getName());
        byName.ifPresent(value -> categoryRepository.delete(value));


        Optional<Category> categoryOptional = categoryRepository.findByNameAndActiveTrue(categoryDto.getName());

        if (categoryOptional.isPresent() && !id.equals(categoryOptional.get().getId()))
            return new ResponseData("Already exist", false);

        if (categoryDto.getCategoryId() == null) {
            Category category = new Category();
            category.setId(id);
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
            return new ResponseData("Successfully saved", true);
        }

        Optional<Category> parentCategory = categoryRepository.findByIdAndActiveTrue(categoryDto.getCategoryId());

        if (parentCategory.isEmpty())
            return new ResponseData("Category does not exist with id: " + categoryDto.getCategoryId(), false);

        if (categoryDto.getName().equals(parentCategory.get().getName()))
            return new ResponseData("Error", false);

        Category category = new Category();
        category.setId(id);
        category.setName(categoryDto.getName());
        category.setParentCategory(parentCategory.get());

        categoryRepository.save(category);
        return new ResponseData("Successfully saved", true);
    }
}
