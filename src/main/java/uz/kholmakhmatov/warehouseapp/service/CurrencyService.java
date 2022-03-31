package uz.kholmakhmatov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.kholmakhmatov.warehouseapp.entity.Currency;
import uz.kholmakhmatov.warehouseapp.repository.CurrencyRepository;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;


import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    public Page<Currency> getAll(Pageable pageable) {
        return currencyRepository.findAllByActiveTrue(pageable);
    }

    public ResponseData findOne(Long id) {
        Optional<Currency> optionalWarehouse = currencyRepository.findByIdAndActiveTrue(id);
        return optionalWarehouse.map(warehouse -> new ResponseData("Success", true, warehouse))
                .orElseGet(() -> new ResponseData("Warehouse does not exist", false));

    }

    public ResponseData save(Currency currency) {
        Optional<Currency> optionalCurrency = currencyRepository.findByName(currency.getName());
        if (optionalCurrency.isEmpty()){
            currencyRepository.save(currency);
            return new ResponseData("Successfully saved", true);
        }

        if (optionalCurrency.get().isActive())
            return new ResponseData("Warehouse already exist", false);

        currency.setId(optionalCurrency.get().getId());
        currency.setActive(true);
        currencyRepository.save(currency);
        return new ResponseData("Successfully saved", true);
    }

    public ResponseData edit(Long id, Currency currency) {
        Optional<Currency> optionalCurrency = currencyRepository.findByIdAndActiveTrue(id);
        if (optionalCurrency.isEmpty()){
            return new ResponseData("Warehouse does not exist", false);
        }
        Optional<Currency> byName = currencyRepository.findByNameAndActiveFalse(currency.getName());
        byName.ifPresent(value -> currencyRepository.delete(value));

        currency.setId(id);
        currencyRepository.save(currency);
        return new ResponseData("Successfully saved", true);
    }

    public ResponseData delete(Long id) {
        Optional<Currency> optionalCurrency = currencyRepository.findByIdAndActiveTrue(id);

        if (optionalCurrency.isEmpty())
            return new ResponseData("Not found", false);

        Currency currency = optionalCurrency.get();
        currency.setActive(false);
        currencyRepository.save(currency);

        return new ResponseData("Successfully deleted", true);
    }
}
