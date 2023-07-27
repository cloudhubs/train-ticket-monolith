package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.util.Response;
import org.springframework.http.HttpHeaders;
import edu.fudanselab.trainticket.entity.*;

public interface TrainFoodService {
    TrainFood createTrainFood(TrainFood tf, HttpHeaders headers);

    Response listTrainFood(HttpHeaders headers);

    Response listTrainFoodByTripId(String tripId, HttpHeaders headers);
}
