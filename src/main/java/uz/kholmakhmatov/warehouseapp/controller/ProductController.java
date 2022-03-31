package uz.kholmakhmatov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.kholmakhmatov.warehouseapp.dto.ProductDto;
import uz.kholmakhmatov.warehouseapp.entity.Product;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;
import uz.kholmakhmatov.warehouseapp.service.ProductService;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public Page<Product> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return productService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseData findOne(@PathVariable Long id) {
        return productService.findOne(id);
    }

    @PostMapping
    public ResponseData add(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @PutMapping("/{id}")
    public ResponseData edit(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productService.edit(id, productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Long id) {
        return productService.delete(id);
    }

}
