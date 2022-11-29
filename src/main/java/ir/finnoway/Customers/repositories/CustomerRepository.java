package ir.finnoway.Customers.repositories;

import ir.finnoway.Customers.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends CrudRepository<Customer, Long> {
    Customer findByName(String name);
}