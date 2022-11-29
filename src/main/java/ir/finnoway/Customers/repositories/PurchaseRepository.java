package ir.finnoway.Customers.repositories;

import ir.finnoway.Customers.entities.Customer;
import ir.finnoway.Customers.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findAllByDateBetweenAndCustomer(Date from, Date to , Customer customer);
}