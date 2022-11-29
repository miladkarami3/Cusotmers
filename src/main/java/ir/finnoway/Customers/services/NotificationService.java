package ir.finnoway.Customers.services;

import ir.finnoway.Customers.entities.Purchase;
import ir.finnoway.Customers.repositories.CustomerRepository;
import ir.finnoway.Customers.repositories.PurchaseRepository;
import ir.finnoway.Customers.util.DateConverter;
import ir.finnoway.Customers.util.JalaliDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class NotificationService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SendToClients sendToClients;

    @Autowired
    private WalletService walletService;


    public void sendIfNeed(Long customerId) throws Exception {
        Long lastYearSameMonthBalance = totalPurchasesInSameMonthInLastYear(customerId);
        Long currentBalance = walletService.getBalance(customerId);
        if(currentBalance<=(lastYearSameMonthBalance/100)) {
            sendToClients.send("Customer"+customerId , "موجودی کیف پول شما یک درصد مصدرف شما در مدت مشابه سال گذشته اشت.");
        }else if(currentBalance<=(lastYearSameMonthBalance/20)){
            sendToClients.send("Customer"+customerId , "موجودی کیف پول شما پنج درصد مصدرف شما در مدت مشابه سال گذشته اشت.");
        } else if(currentBalance<=(lastYearSameMonthBalance/10)){
            sendToClients.send("Customer"+customerId , "موجودی کیف پول شما ده درصد مصدرف شما در مدت مشابه سال گذشته اشت.");
        }

    }


    //مقدار مصرفی در ماه مشابه سال قبل
    private Long totalPurchasesInSameMonthInLastYear(Long customerId) throws Exception {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        JalaliDate jalaliDate = (new DateConverter()).gregorianToJalali(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        int lastYar = jalaliDate.getYear() - 1;
        int month = jalaliDate.getMonthPersian().getValue();
        int monthDuration = 29;
        if (month <= 6)
            monthDuration = 31;
        if (month <= 11)
            monthDuration = 30;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dateInString1 = (new DateConverter()).jalaliToGregorian(lastYar, month, 1).toString();
        String dateInString2 = (new DateConverter()).jalaliToGregorian(lastYar, month, monthDuration).toString();
        Date date1 = formatter.parse(dateInString1);
        Date date2 = formatter.parse(dateInString2);
        List<Purchase> lastYearCurrentMonthPurchaselist = purchaseRepository.findAllByDateBetweenAndCustomer(date1, date2, customerRepository.findById(customerId).get());
        Long lastYearCurrentMonthPurchaseTotalPrice = lastYearCurrentMonthPurchaselist.stream().mapToLong(o -> o.getPrice()).sum();
        return lastYearCurrentMonthPurchaseTotalPrice;
    }

}
