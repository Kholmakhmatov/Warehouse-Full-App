package uz.kholmakhmatov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.kholmakhmatov.warehouseapp.dto.OutputDto;
import uz.kholmakhmatov.warehouseapp.entity.Output;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;
import uz.kholmakhmatov.warehouseapp.service.OutputService;


@RestController
@RequestMapping("api/output")
public class OutputController {
    @Autowired
    OutputService outputService;

    @GetMapping
    public Page<Output> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return outputService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseData findOne(@PathVariable Long id) {
        return outputService.findOne(id);
    }

    @PostMapping
    public ResponseData save(@RequestBody OutputDto outputDto) {
        return outputService.save(outputDto);
    }

    @PutMapping("/{id}")
    public ResponseData edit(@PathVariable Long id, @RequestBody OutputDto outputDto) {
        return outputService.edit(id, outputDto);
    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Long id) {
        return outputService.delete(id);
    }


}
