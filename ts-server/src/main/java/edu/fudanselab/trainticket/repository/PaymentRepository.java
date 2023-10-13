package edu.fudanselab.trainticket.repository;

import edu.fudanselab.trainticket.entity.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author fdse
 */
public interface PaymentRepository extends CrudRepository<Payment,String> {

    Optional<Payment> findById(String id);

    Payment findByOrderId(String orderId);

    @Override
    List<Payment> findAll();

    List<Payment> findByUserId(String userId);
}
