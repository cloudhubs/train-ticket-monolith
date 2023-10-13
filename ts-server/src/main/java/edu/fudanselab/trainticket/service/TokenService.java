package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.dto.BasicAuthDto;
import edu.fudanselab.trainticket.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface TokenService {

    /**
     * get token by dto
     *
     * @param dto dto
     * @param  headers headers
     * @return Response
     */
    Response getToken(BasicAuthDto dto, HttpHeaders headers);


}
