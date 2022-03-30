package uz.kholmakhmatov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.kholmakhmatov.warehouseapp.entity.Client;
import uz.kholmakhmatov.warehouseapp.repository.ClientRepository;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;


import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAllByActiveTrue(pageable);
    }

    public ResponseData save(Client client) {
        Optional<Client> clientOptional = clientRepository.findByPhone(client.getPhone());

        if (clientOptional.isPresent()) {
            if (clientOptional.get().isActive()) {
                return new ResponseData("Supplier already exist", false);
            }
            client.setId(clientOptional.get().getId());
            client.setActive(true);
        }
        clientRepository.save(client);

        return new ResponseData("Successfully saved", true, client);
    }

    public ResponseData findOne(Long id) {
        Optional<Client> optionalClient = clientRepository.findByIdAndActiveTrue(id);
        return optionalClient.map(client -> new ResponseData("Success", true, client))
                .orElseGet(() -> new ResponseData("Supplier does not exist", false));
    }

    public ResponseData delete(Long id) {
        Optional<Client> optionalClient = clientRepository.findByIdAndActiveTrue(id);

        if (optionalClient.isEmpty())
            return new ResponseData("Not found", false);

        Client client = optionalClient.get();
        client.setActive(false);
        clientRepository.save(client);

        return new ResponseData("Successfully deleted", true);
    }

    public ResponseData edit(Long id, Client client) {
        Optional<Client> optionalClient = clientRepository.findByIdAndActiveTrue(id);
        if (optionalClient.isEmpty()) {
            return new ResponseData("Client does not exist", false);
        }
        Optional<Client> byPhoneNumber = clientRepository.findByPhoneAndActiveFalse(client.getPhone());
        byPhoneNumber.ifPresent(value -> clientRepository.delete(value));

        client.setId(id);
        clientRepository.save(client);

        return new ResponseData("Successfully saved", true, client);
    }
}
