package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface CancelService {

    /**
     * cancel order by order id, login id
     *
     * @param orderId order id
     * @param loginId login id
     * @param headers headers
     * @throws  Exception
     * @return Response
     */
    Response cancelOrder(String orderId, String loginId, HttpHeaders headers);

    /**
     * calculate refund by login id
     *
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response calculateRefund(String orderId, HttpHeaders headers);

}
