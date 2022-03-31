package uz.kholmakhmatov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.kholmakhmatov.warehouseapp.dto.OutputDto;
import uz.kholmakhmatov.warehouseapp.entity.Client;
import uz.kholmakhmatov.warehouseapp.entity.Currency;
import uz.kholmakhmatov.warehouseapp.entity.Output;
import uz.kholmakhmatov.warehouseapp.entity.Warehouse;
import uz.kholmakhmatov.warehouseapp.repository.ClientRepository;
import uz.kholmakhmatov.warehouseapp.repository.CurrencyRepository;
import uz.kholmakhmatov.warehouseapp.repository.OutputRepository;
import uz.kholmakhmatov.warehouseapp.repository.WarehouseRepository;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OutputService {
    @Autowired
    OutputRepository outputRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    ClientRepository clientRepository;

    public Page<Output> getAll(Pageable pageable) {
        return outputRepository.findAll(pageable);
    }

    public ResponseData findOne(Long id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        return optionalOutput.map(output -> new ResponseData("success", true, output)).orElseGet(() -> new ResponseData("not found", false));
    }

    public ResponseData save(OutputDto outputDto) {
        Optional<Client> byClientId = clientRepository.findById(outputDto.getClientId());
        if (byClientId.isEmpty())
            return new ResponseData("client not found", false);

        Optional<Warehouse> byWarehouseId = warehouseRepository.findById(outputDto.getWarehouseId());
        if (byWarehouseId.isEmpty())
            return new ResponseData("warehouse not found", false);

        Optional<Currency> byCurrencyId = currencyRepository.findById(outputDto.getCurrencyId());
        if (byCurrencyId.isEmpty())
            return new ResponseData("currency not found", false);

        Output output = new Output();
        output.setDate(LocalDateTime.now());
        output.setCurrency(byCurrencyId.get());
        output.setClient(byClientId.get());
        output.setWarehouse(byWarehouseId.get());
        output.setFactureNumber(outputDto.getFactureNumber());
        outputRepository.save(output);

        return new ResponseData("saved",true);

    }

    public ResponseData edit(Long id, OutputDto outputDto) {

        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isEmpty()){
            return new ResponseData("output not found", false);
        }

        Optional<Client> byClientId = clientRepository.findById(outputDto.getClientId());
        if (byClientId.isEmpty())
            return new ResponseData("client not found", false);

        Optional<Warehouse> byWarehouseId = warehouseRepository.findById(outputDto.getWarehouseId());
        if (byWarehouseId.isEmpty())
            return new ResponseData("warehouse not found", false);

        Optional<Currency> byCurrencyId = currencyRepository.findById(outputDto.getCurrencyId());
        if (byCurrencyId.isEmpty())
            return new ResponseData("currency not found", false);

        Output output = new Output();
        output.setId(id);
        output.setCurrency(byCurrencyId.get());
        output.setClient(byClientId.get());
        output.setWarehouse(byWarehouseId.get());
        output.setFactureNumber(outputDto.getFactureNumber());
        outputRepository.save(output);

        return new ResponseData("saved",true);
    }

    public ResponseData delete(Long id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isPresent()){
            outputRepository.deleteById(id);
            return new ResponseData("successfully deleted", true);
        }
        return new ResponseData("not found", false);
    }
}
