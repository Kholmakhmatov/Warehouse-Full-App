package uz.kholmakhmatov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.kholmakhmatov.warehouseapp.dto.WorkerDto;
import uz.kholmakhmatov.warehouseapp.entity.Warehouse;
import uz.kholmakhmatov.warehouseapp.entity.Worker;
import uz.kholmakhmatov.warehouseapp.repository.WarehouseRepository;
import uz.kholmakhmatov.warehouseapp.repository.WorkerRepository;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    public Page<Worker> getAll(Pageable pageable) {
        return workerRepository.findAllByActiveTrue(pageable);
    }

    public ResponseData findOne(Long id) {
        Optional<Worker> optionalWorker = workerRepository.findByIdAndActiveTrue(id);
        return optionalWorker.map(worker -> new ResponseData("Success", true, worker))
                .orElseGet(() -> new ResponseData("Worker does not exist", false));
    }

    public Page<Worker> getByWarehouse(Long id, Pageable pageable) {
        return workerRepository.findAllByWarehouseIdAndActiveTrue(id, pageable);
    }

    public ResponseData save(WorkerDto workerDto) {
        Optional<Worker> workerOptional = workerRepository.findByPhoneNumber(workerDto.getPhoneNumber());

        if (workerOptional.isPresent()) {
            if (workerOptional.get().isActive()) {
                return new ResponseData("Worker already exist", false);
            } else {
                workerRepository.deleteById(workerOptional.get().getId());
            }
        }
        workerRepository.save(toEntity(workerDto));

        return new ResponseData("Successfully saved", true, workerDto);
    }

    public ResponseData edit(Long id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findByIdAndActiveTrue(id);
        if (optionalWorker.isEmpty()) {
            return new ResponseData("Worker does not exist", false);
        }
        Optional<Worker> byPhoneNumber = workerRepository.findByPhoneNumber(workerDto.getPhoneNumber());
        if (byPhoneNumber.isPresent()) {
            if (byPhoneNumber.get().isActive())
                return new ResponseData("Phone number already exist", false);
            workerRepository.delete(byPhoneNumber.get());
        }
        Worker worker = toEntity(workerDto);
        worker.setId(id);
        workerRepository.save(worker);

        return new ResponseData("Successfully saved", true, workerDto);
    }

    public ResponseData delete(Long id) {
        Optional<Worker> optionalWorker = workerRepository.findByIdAndActiveTrue(id);

        if (optionalWorker.isEmpty())
            return new ResponseData("Not found", false);

        Worker worker = optionalWorker.get();
        worker.setActive(false);
        workerRepository.save(worker);

        return new ResponseData("Successfully deleted", true);
    }

    private Worker toEntity(WorkerDto workerDto) {

        Worker worker = new Worker();

        worker.setFirstName(workerDto.getFirstName());
        worker.setLastName(workerDto.getLastName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setPassword(workerDto.getPassword());

        if (workerDto.getWarehousesId() != null) {
            Set<Warehouse> warehouses = new HashSet<>();

            for (Long warehouseId : workerDto.getWarehousesId()) {
                Optional<Warehouse> byId = warehouseRepository.findById(warehouseId);
                byId.ifPresent(warehouses::add);
            }
            worker.setWarehouse(warehouses);
        }
        return worker;
    }
}
