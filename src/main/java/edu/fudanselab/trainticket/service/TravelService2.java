package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.entity.TripInfo;
import edu.fudanselab.trainticket.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;

/**
 * @author  Chenjie Xu
 * @date  2017/6/7.
 */
public interface TravelService2 {
    Response create(edu.fudanselab.trainticket.entity.TravelInfo info, HttpHeaders headers);

    Response retrieve(String tripId, HttpHeaders headers);

    Response update(edu.fudanselab.trainticket.entity.TravelInfo info, HttpHeaders headers);

    Response delete(String tripId, HttpHeaders headers);

    Response query(TripInfo info, HttpHeaders headers);

    Response queryByBatch(TripInfo info, HttpHeaders headers);

    Response getTripAllDetailInfo(edu.fudanselab.trainticket.entity.TripAllDetailInfo gtdi, HttpHeaders headers);

    Response getRouteByTripId(String tripId, HttpHeaders headers);

    Response getTrainTypeByTripId(String tripId, HttpHeaders headers);

    Response queryAll(HttpHeaders headers);

    Response getTripByRoute(ArrayList<String> routeIds, HttpHeaders headers);

    Response adminQueryAll(HttpHeaders headers);
}
