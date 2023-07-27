package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.util.Response;
import edu.fudanselab.trainticket.entity.DeliveryInfo;
import edu.fudanselab.trainticket.entity.FoodDeliveryOrder;
import edu.fudanselab.trainticket.entity.SeatInfo;
import edu.fudanselab.trainticket.entity.TripOrderInfo;
import org.springframework.http.HttpHeaders;

public interface FoodDeliveryService {

    Response createFoodDeliveryOrder(FoodDeliveryOrder fd, HttpHeaders headers);

    Response deleteFoodDeliveryOrder(String id, HttpHeaders headers);

    Response getFoodDeliveryOrderById(String id, HttpHeaders headers);

    Response getAllFoodDeliveryOrders(HttpHeaders headers);

    Response getFoodDeliveryOrderByStoreId(String storeId, HttpHeaders headers);

    Response updateTripId(TripOrderInfo tripOrderInfo, HttpHeaders headers);

    Response updateSeatNo(SeatInfo seatInfo, HttpHeaders headers);

    Response updateDeliveryTime(DeliveryInfo deliveryInfo, HttpHeaders headers);
}
