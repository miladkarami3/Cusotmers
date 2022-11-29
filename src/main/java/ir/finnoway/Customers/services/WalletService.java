package ir.finnoway.Customers.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

@Service
public class WalletService {
    private Jedis jedis;

    @Value("${redis.url}")
    private String url;

    @Value("${redis.port}")
    private int port;

    @PostConstruct
    public void init(){
         jedis = new Jedis(url,port);
    }
    public void withdraw(Long customerId , Long price){
        String key = "Customer:"+customerId;
        Long balance = Long.parseLong(jedis.get(key));
        balance-=price;
        jedis.set(key,balance.toString());
    }
    public void deposit(Long customerId, Long price){
        String key = "Customer:"+customerId;
        Long balance = Long.parseLong(jedis.get(key));
        balance+=price;
        jedis.set(key,balance.toString());
    }

    public void firstCharge(Long customerId, Long price){
        String key = "Customer:"+customerId;
        if(jedis.get(key)==null) {
            jedis.set(key, price.toString());
        }
    }
    public Long getBalance(Long customerId){
        String key = "Customer:"+customerId;
        if(jedis.get(key)!=null) {
            return Long.parseLong(jedis.get(key));
        }else {
            return 0l;
        }
    }
}
