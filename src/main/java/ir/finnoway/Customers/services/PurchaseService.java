package ir.finnoway.Customers.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.finnoway.Customers.dtos.PurchaseDto;
import ir.finnoway.Customers.entities.Purchase;
import ir.finnoway.Customers.mapper.PurchaseMapper;
import ir.finnoway.Customers.repositories.CustomerRepository;
import ir.finnoway.Customers.repositories.PurchaseRepository;
import ir.finnoway.Customers.util.DateConverter;
import ir.finnoway.Customers.util.JalaliDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private NotificationService notificationService;

    @PostConstruct
    public void init(){
        try(InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("purchases.json")){
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
            String jsonString = mapper.writeValueAsString(jsonNode);
            List<PurchaseDto> purchaseList = new ArrayList<>();
            purchaseList = Arrays.asList(mapper.readValue(jsonString, PurchaseDto[].class));
            int counter = 3;
            for(PurchaseDto purchaseDto:purchaseList){
                Purchase purchase = purchaseMapper.toEntity(purchaseDto);
                purchase.setId(counter++);
                purchaseRepository.save(purchase);
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public Purchase add(PurchaseDto purchaseDto) throws Exception{
        Long balance = walletService.getBalance(purchaseDto.getCustomerId());
        if(balance<purchaseDto.getPrice()){
            throw new Exception("Your balance is less than your purchase");
        }
        Purchase purchase = purchaseMapper.toEntity(purchaseDto);
        walletService.withdraw(purchaseDto.getCustomerId() ,purchaseDto.getPrice());
        purchaseRepository.save(purchase);
        notificationService.sendIfNeed(purchaseDto.getCustomerId());
        return purchase;
    }

}
