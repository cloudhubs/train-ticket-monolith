package edu.fudanselab.trainticket.repository;


import edu.fudanselab.trainticket.entity.WaitListOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaitListOrderRepository extends CrudRepository<WaitListOrder,String> {

    @Override
    Optional<WaitListOrder> findById(String id);

    @Override
    List<WaitListOrder> findAll();

    ArrayList<WaitListOrder> findByAccountId(String accountId);

    @Override
    void deleteById(String id);
}
