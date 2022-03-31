package uz.kholmakhmatov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.kholmakhmatov.warehouseapp.dto.InputDto;
import uz.kholmakhmatov.warehouseapp.entity.Input;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;
import uz.kholmakhmatov.warehouseapp.service.InputService;


@RestController
@RequestMapping("api/input")
public class InputController {
    @Autowired
    InputService inputService;

    @GetMapping
    public Page<Input> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return inputService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseData findOne(@PathVariable Long id) {
        return inputService.findOne(id);
    }

    @PostMapping
    public ResponseData save(@RequestBody InputDto inputDto) {
        return inputService.save(inputDto);
    }

    @PutMapping("/{id}")
    public ResponseData edit(@PathVariable Long id, @RequestBody InputDto inputDto) {
        return inputService.edit(id, inputDto);
    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Long id) {
        return inputService.delete(id);
    }


}
