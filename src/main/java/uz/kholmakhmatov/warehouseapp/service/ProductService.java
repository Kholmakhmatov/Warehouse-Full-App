package uz.kholmakhmatov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.kholmakhmatov.warehouseapp.dto.ProductDto;
import uz.kholmakhmatov.warehouseapp.entity.Attachment;
import uz.kholmakhmatov.warehouseapp.entity.Category;
import uz.kholmakhmatov.warehouseapp.entity.Measurement;
import uz.kholmakhmatov.warehouseapp.entity.Product;
import uz.kholmakhmatov.warehouseapp.repository.AttachmentRepository;
import uz.kholmakhmatov.warehouseapp.repository.CategoryRepository;
import uz.kholmakhmatov.warehouseapp.repository.MeasurementRepository;
import uz.kholmakhmatov.warehouseapp.repository.ProductRepository;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    MeasurementRepository measurementRepository;


    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAllByActiveTrue(pageable);
    }

    public ResponseData save(ProductDto productDto) {
        boolean exist = productRepository.existsByNameAndCategoryIdAndPhotoIdAndMeasurementIdAndActiveTrue(
                productDto.getName(),
                productDto.getCategoryId(),
                productDto.getPhotoId(),
                productDto.getMeasurementId()
        );
        if (exist)
            return new ResponseData("Product already exist", false);

        Optional<Category> categoryOptional = categoryRepository.findByIdAndActiveTrue(productDto.getCategoryId());
        if (categoryOptional.isEmpty())
            return new ResponseData("Category does not exist", false);

        Optional<Attachment> attachmentOptional = attachmentRepository.findById(productDto.getPhotoId());
        if (attachmentOptional.isEmpty())
            return new ResponseData("Photo does not exist", false);

        Optional<Measurement> measurementOptional = measurementRepository.findById(productDto.getMeasurementId());
        if (measurementOptional.isEmpty())
            return new ResponseData("Measurement does not exist", false);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(categoryOptional.get());
        product.setMeasurement(measurementOptional.get());
        product.setPhoto(attachmentOptional.get());
        productRepository.save(product);

        return new ResponseData("Successfully added", true);
    }

    public ResponseData findOne(Long id) {
        Optional<Product> productOptional = productRepository.findByIdAndActiveTrue(id);
        return productOptional.map(product -> new ResponseData("Success", true, product))
                .orElseGet(() -> new ResponseData("Product does not exist", false));
    }

    public ResponseData delete(Long id) {
        Optional<Product> productOptional = productRepository.findByIdAndActiveTrue(id);

        if (productOptional.isEmpty())
            return new ResponseData("Not found", false);

        Product product = productOptional.get();
        product.setActive(false);
        productRepository.save(product);

        return new ResponseData("Successfully deleted", true);
    }

    public ResponseData edit(Long id, ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findByIdAndActiveTrue(id);
        if (productOptional.isEmpty()) {
            return new ResponseData("Product does not exist", false);
        }

        boolean exist = productRepository.existsByNameAndCategoryIdAndPhotoIdAndMeasurementIdAndActiveTrue(
                productDto.getName(),
                productDto.getCategoryId(),
                productDto.getPhotoId(),
                productDto.getMeasurementId()
        );
        if (exist)
            return new ResponseData("Product already exist", false);

        Optional<Category> categoryOptional = categoryRepository.findById(productDto.getCategoryId());
        if (categoryOptional.isEmpty())
            return new ResponseData("Category does not exist", false);

        Optional<Attachment> attachmentOptional = attachmentRepository.findById(productDto.getPhotoId());
        if (attachmentOptional.isEmpty())
            return new ResponseData("Photo does not exist", false);

        Optional<Measurement> measurementOptional = measurementRepository.findById(productDto.getMeasurementId());
        if (measurementOptional.isEmpty())
            return new ResponseData("Measurement does not exist", false);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(categoryOptional.get());
        product.setMeasurement(measurementOptional.get());
        product.setPhoto(attachmentOptional.get());
        productRepository.save(product);


        return new ResponseData("Successfully edited", true);
    }


    public void deleteByCategoryId(Long id) {
        productRepository.deActiveQueryNative(id);
    }
}
