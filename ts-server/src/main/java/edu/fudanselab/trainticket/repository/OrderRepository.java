package edu.fudanselab.trainticket.repository;

import edu.fudanselab.trainticket.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author fdse
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Override
    Optional<Order> findById(String id);

    @Override
    ArrayList<Order> findAll();

    ArrayList<Order> findByAccountId(String accountId);

    ArrayList<Order> findByTravelDateAndTrainNumber(String travelDate,String trainNumber);

    @Override
    void deleteById(String id);
}
