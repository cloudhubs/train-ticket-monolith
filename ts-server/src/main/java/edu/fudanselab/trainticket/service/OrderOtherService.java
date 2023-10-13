package edu.fudanselab.trainticket.service;


import edu.fudanselab.trainticket.entity.Order;
import edu.fudanselab.trainticket.entity.Seat;
import edu.fudanselab.trainticket.util.Response;
import org.springframework.http.HttpHeaders;
import edu.fudanselab.trainticket.entity.OrderAlterInfo;
import edu.fudanselab.trainticket.entity.QueryInfo;

import java.util.Date;

/**
 * @author fdse
 */
public interface OrderOtherService {

    Response findOrderById(String id, HttpHeaders headers);

    Response create(Order newOrder, HttpHeaders headers);

    Response updateOrder(Order order, HttpHeaders headers);

    Response saveChanges(Order order, HttpHeaders headers);

    Response cancelOrder(String accountId, String orderId, HttpHeaders headers);

    Response addNewOrder(Order order, HttpHeaders headers);

    Response deleteOrder(String orderId, HttpHeaders headers);

    Response getOrderById(String orderId, HttpHeaders headers);

    Response payOrder(String orderId, HttpHeaders headers);

    Response getOrderPrice(String orderId, HttpHeaders headers);

    Response modifyOrder(String orderId, int status, HttpHeaders headers);

    Response getAllOrders(HttpHeaders headers);

    Response getSoldTickets(Seat seatRequest, HttpHeaders headers);

    Response queryOrders(QueryInfo qi, String accountId, HttpHeaders headers);

    Response queryOrdersForRefresh(QueryInfo qi, String accountId, HttpHeaders headers);

    Response alterOrder(OrderAlterInfo oai, HttpHeaders headers);

    Response queryAlreadySoldOrders(Date travelDate, String trainNumber, HttpHeaders headers);

    Response checkSecurityAboutOrder(Date checkDate, String accountId, HttpHeaders headers);

    void initOrder(Order order, HttpHeaders headers);
}
