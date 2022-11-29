package ir.finnoway.Customers.controller;

import ir.finnoway.Customers.dtos.CustomerDto;
import ir.finnoway.Customers.dtos.PurchaseDto;
import ir.finnoway.Customers.entities.Customer;
import ir.finnoway.Customers.entities.Purchase;
import ir.finnoway.Customers.services.CustomerService;
import ir.finnoway.Customers.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping
    public Purchase save(@RequestBody PurchaseDto purchaseDto) throws Exception {
        return purchaseService.add(purchaseDto);
    }
}