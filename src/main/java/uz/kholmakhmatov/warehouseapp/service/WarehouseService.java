package uz.kholmakhmatov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.kholmakhmatov.warehouseapp.entity.Warehouse;
import uz.kholmakhmatov.warehouseapp.repository.WarehouseRepository;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;

import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public Page<Warehouse> getAll(Pageable pageable) {
        return warehouseRepository.findAllByActiveTrue(pageable);
    }

    public ResponseData save(Warehouse warehouse) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByName(warehouse.getName());
        if (optionalWarehouse.isEmpty()){
            warehouseRepository.save(warehouse);
            return new ResponseData("Successfully saved", true);
        }

        if (optionalWarehouse.get().isActive())
            return new ResponseData("Warehouse already exist", false);

        warehouse.setId(optionalWarehouse.get().getId());
        warehouse.setActive(true);
        warehouseRepository.save(warehouse);
        return new ResponseData("Successfully saved", true);
    }

    public ResponseData findOne(Long id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByIdAndActiveTrue(id);
        return optionalWarehouse.map(warehouse -> new ResponseData("Success", true, warehouse))
                .orElseGet(() -> new ResponseData("Warehouse does not exist", false));

    }

    public ResponseData delete(Long id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByIdAndActiveTrue(id);

        if (optionalWarehouse.isEmpty())
            return new ResponseData("Not found", false);

        Warehouse warehouse = optionalWarehouse.get();
        warehouse.setActive(false);
        warehouseRepository.save(warehouse);

        return new ResponseData("Successfully deleted", true);
    }

    public ResponseData edit(Long id, Warehouse warehouse) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByIdAndActiveTrue(id);
        if (optionalWarehouse.isEmpty()){
            return new ResponseData("Warehouse does not exist", false);
        }
        Optional<Warehouse> byName = warehouseRepository.findByNameAndActiveFalse(warehouse.getName());
        byName.ifPresent(value -> warehouseRepository.delete(value));

        warehouse.setId(id);
        warehouseRepository.save(warehouse);
        return new ResponseData("Successfully saved", true);
    }
}
