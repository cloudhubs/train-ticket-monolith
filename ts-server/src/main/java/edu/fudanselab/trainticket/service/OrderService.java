package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.entity.Seat;
import edu.fudanselab.trainticket.util.Response;
import edu.fudanselab.trainticket.entity.Order;
import edu.fudanselab.trainticket.entity.OrderAlterInfo;
import edu.fudanselab.trainticket.entity.OrderInfo;
import org.springframework.http.HttpHeaders;

import java.util.Date;

/**
 * @author fdse
 */
public interface OrderService {

    Response findOrderById(String id, HttpHeaders headers);

    Response create(Order newOrder, HttpHeaders headers);

    Response saveChanges(Order order, HttpHeaders headers);

    Response cancelOrder(String accountId, String orderId, HttpHeaders headers);

    Response queryOrders(OrderInfo qi, String accountId, HttpHeaders headers);

    Response queryOrdersForRefresh(OrderInfo qi, String accountId, HttpHeaders headers);

    Response alterOrder(OrderAlterInfo oai, HttpHeaders headers);

    Response queryAlreadySoldOrders(Date travelDate, String trainNumber, HttpHeaders headers);

    Response getAllOrders(HttpHeaders headers);

    Response modifyOrder(String orderId, int status, HttpHeaders headers);

    Response getOrderPrice(String orderId, HttpHeaders headers);

    Response payOrder(String orderId, HttpHeaders headers);

    Response getOrderById(String orderId , HttpHeaders headers);

    Response checkSecurityAboutOrder(Date checkDate, String accountId, HttpHeaders headers);

    void initOrder(Order order, HttpHeaders headers);

    Response deleteOrder(String orderId, HttpHeaders headers);

    Response getSoldTickets(Seat seatRequest, HttpHeaders headers);

    Response addNewOrder(Order order, HttpHeaders headers);

    Response updateOrder(Order order, HttpHeaders headers);
}
