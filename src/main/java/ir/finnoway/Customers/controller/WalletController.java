package ir.finnoway.Customers.controller;

import ir.finnoway.Customers.dtos.PurchaseDto;
import ir.finnoway.Customers.dtos.TransactionDto;
import ir.finnoway.Customers.entities.Purchase;
import ir.finnoway.Customers.services.PurchaseService;
import ir.finnoway.Customers.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PostMapping
    public String doTransaction(@RequestBody TransactionDto transactionDto) {
        if(transactionDto.getAmount()> 0 ){
            walletService.deposit(transactionDto.getCustomerId() , transactionDto.getAmount());
        }else{
            walletService.withdraw(transactionDto.getCustomerId() , transactionDto.getAmount());
        }
        return String.format("Customer-%s balance is %s" , transactionDto.getCustomerId() , walletService.getBalance(transactionDto.getCustomerId()));
    }
}