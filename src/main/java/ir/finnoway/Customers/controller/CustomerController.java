package ir.finnoway.Customers.controller;

import ir.finnoway.Customers.dtos.CustomerDto;
import ir.finnoway.Customers.entities.Customer;
import ir.finnoway.Customers.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public Customer save(@RequestBody CustomerDto customerDto) {
        return customerService.save(customerDto);
    }


    @PutMapping("/{id}")
    public Customer edit(@PathVariable Long id, @RequestBody CustomerDto customerDto) throws Exception {
        return customerService.edit(id ,customerDto);
    }

    @GetMapping
    public List<Customer> list(){
        return customerService.findAll();
    }
    @GetMapping("/{name}")
    public Customer findByName(@PathVariable String name){
        return customerService.findByName(name);
    }

    @DeleteMapping("/{id}")
    public Customer delete(@PathVariable Long id) throws Exception{
        return customerService.remove(id);
    }
}