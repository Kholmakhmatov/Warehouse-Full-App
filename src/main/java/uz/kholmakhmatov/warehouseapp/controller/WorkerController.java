package uz.kholmakhmatov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.kholmakhmatov.warehouseapp.dto.WorkerDto;
import uz.kholmakhmatov.warehouseapp.entity.Worker;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;
import uz.kholmakhmatov.warehouseapp.service.WorkerService;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @GetMapping
    public Page<Worker> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return workerService.getAll(pageable);
    }

    @GetMapping("/getByWarehouse/{id}")
    public Page<Worker> workerPage(@PathVariable Long id, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return workerService.getByWarehouse(id, pageable);
    }

    @PostMapping
    public ResponseData add(@RequestBody WorkerDto workerDto) {
        return workerService.save(workerDto);
    }

    @GetMapping("/{id}")
    public ResponseData findOne(@PathVariable Long id) {
        return workerService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Long id) {
        return workerService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseData edit(@PathVariable Long id, @RequestBody WorkerDto workerDto) {
        return workerService.edit(id, workerDto);
    }
}
