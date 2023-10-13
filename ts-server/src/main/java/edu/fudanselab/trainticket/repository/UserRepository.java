package edu.fudanselab.trainticket.repository;

import edu.fudanselab.trainticket.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findByUserName(String userName);

    User findByUserId(String userId);

    void deleteByUserId(String userId);

    @Override
    List<User> findAll();
}
