package ir.finnoway.Customers.mapper;

import ir.finnoway.Customers.dtos.PurchaseDto;
import ir.finnoway.Customers.entities.Purchase;
import ir.finnoway.Customers.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseMapper {

    @Autowired
    CustomerRepository customerRepository;

    public Purchase toEntity(PurchaseDto dto){
        Purchase purchase = new Purchase();
        purchase.setDate(dto.getDate());
        purchase.setPrice(dto.getPrice());
        purchase.setProductName(dto.getProductName());
        purchase.setCustomer(customerRepository.findById(dto.getCustomerId()).get());
        return purchase;
    }
}
