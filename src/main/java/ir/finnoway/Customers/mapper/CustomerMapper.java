package ir.finnoway.Customers.mapper;

import ir.finnoway.Customers.dtos.CustomerDto;
import ir.finnoway.Customers.entities.Customer;

public class CustomerMapper {
    public static Customer toEntity(CustomerDto dto){
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setLastname(dto.getLastname());
        customer.setEmail(dto.getEmail());
        customer.setBirthDate(dto.getBirthDate());
        customer.setTimeZoneId(dto.getTimeZoneId());
        customer.setPhoneNumber(dto.getPhoneNumber());
        return customer;
    }
}
