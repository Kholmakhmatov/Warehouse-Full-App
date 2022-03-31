package uz.kholmakhmatov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.kholmakhmatov.warehouseapp.entity.Supplier;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;
import uz.kholmakhmatov.warehouseapp.service.SupplierService;

@RestController
@RequestMapping("api/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping
    public Page<Supplier> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return supplierService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseData findOne(@PathVariable Long id) {
        return supplierService.findOne(id);
    }

    @PostMapping
    public ResponseData add(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @PutMapping("/{id}")
    public ResponseData edit(@PathVariable Long id, @RequestBody Supplier supplier) {
        return supplierService.edit(id, supplier);
    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Long id) {
        return supplierService.delete(id);
    }

}
