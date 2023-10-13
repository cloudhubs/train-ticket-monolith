package edu.fudanselab.trainticket.repository;

import edu.fudanselab.trainticket.entity.Money;
import org.springframework.data.repository.CrudRepository;

/**
 * @author fdse
 */
public interface AddMoneyRepository extends CrudRepository<Money,String> {
}
