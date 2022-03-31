package uz.kholmakhmatov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import uz.kholmakhmatov.warehouseapp.entity.Currency;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;
import uz.kholmakhmatov.warehouseapp.service.CurrencyService;

@RestController
@RequestMapping("api/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping
    public Page<Currency> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return currencyService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseData findOne(@PathVariable Long id) {
        return currencyService.findOne(id);
    }

    @PostMapping
    public ResponseData add(@RequestBody Currency currency) {
        return currencyService.save(currency);
    }

    @PutMapping("/{id}")
    public ResponseData edit(@PathVariable Long id, @RequestBody Currency currency) {
        return currencyService.edit(id, currency);
    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Long id) {
        return currencyService.delete(id);
    }

}
