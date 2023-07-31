package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.dto.AuthDto;
import edu.fudanselab.trainticket.entity.AuthUser;
import edu.fudanselab.trainticket.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * @author fdse
 */
public interface AuthUserService {

    /**
     * save user
     *
     * @param user user
     * @return user
     */
    AuthUser saveUser(AuthUser user);

    /**
     * get all users
     *
     * @param headers headers
     * @return List<User>
     */
    List<AuthUser> getAllUser(HttpHeaders headers);

    /**
     * create default auth user
     *
     * @param dto dto
     * @return user
     */
    AuthUser createDefaultAuthUser(AuthDto dto);

    /**
     * delete by user id
     *
     * @param userId user id
     * @param headers headers
     * @return Response
     */
    Response deleteByUserId(String userId, HttpHeaders headers);

}
