package edu.fudanselab.trainticket.repository;

import edu.fudanselab.trainticket.entity.InsidePaymentMoney;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author fdse
 */
public interface InsidePaymentAddMoneyRepository extends CrudRepository<InsidePaymentMoney,String> {

    /**
     * find by user id
     *
     * @param userId user id
     * @return List<Money>
     */
    List<InsidePaymentMoney> findByUserId(String userId);

    /**
     * find all
     *
     * @return List<Money>
     */
    @Override
    List<InsidePaymentMoney> findAll();
}
