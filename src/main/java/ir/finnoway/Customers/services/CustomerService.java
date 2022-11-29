package ir.finnoway.Customers.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.finnoway.Customers.dtos.CustomerDto;
import ir.finnoway.Customers.entities.Customer;
import ir.finnoway.Customers.mapper.CustomerMapper;
import ir.finnoway.Customers.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletService walletService;

    @PostConstruct
    public void createDefaultCustomers(){
        try(InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("customers.json")){
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
            String jsonString = mapper.writeValueAsString(jsonNode);
            List<CustomerDto> customerDtoList = new ArrayList<>();
            customerDtoList = Arrays.asList(mapper.readValue(jsonString, CustomerDto[].class));
            int counter = 1;
            for(CustomerDto customerDto:customerDtoList){
                Customer customer = CustomerMapper.toEntity(customerDto);
                customer.setId(counter++);
                customerRepository.save(customer);
                walletService.firstCharge(customer.getId(),2000l);
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    public Customer save(CustomerDto customerDto){
        Customer customer = CustomerMapper.toEntity(customerDto);
        return customerRepository.save(customer);
    }

    public Customer edit(Long id, CustomerDto customerDto) throws Exception {
        Customer customer = customerRepository.findById(id).get();
        if (customer == null) {
            throw new Exception("no customer found with id = " + id);
        }
        customer = CustomerMapper.toEntity(customerDto);
        customer.setId(id);
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Customer findByName(String name) {
        return customerRepository.findByName(name);
    }

    public Customer remove(Long id) throws Exception {
        Customer customer = customerRepository.findById(id).get();
        if (customer == null) {
            throw new Exception("no customer found with id = " + id);
        }
        customerRepository.delete(customer);
        return customer;
    }
}
