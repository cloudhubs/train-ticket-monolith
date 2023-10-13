package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.entity.RoutePlanInfo;
import edu.fudanselab.trainticket.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface RoutePlanService {

    /**
     * search the cheapest result with route plan info
     *
     * @param info route plan info
     * @param headers headers
     * @return Response
     */
    Response searchCheapestResult(RoutePlanInfo info, HttpHeaders headers);

    /**
     * search the quickest result with route plan info
     *
     * @param info route plan info
     * @param headers headers
     * @return Response
     */
    Response searchQuickestResult(RoutePlanInfo info, HttpHeaders headers);

    /**
     * search min stop-station with route plan info
     *
     * @param info route plan info
     * @param headers headers
     * @return Response
     */
    Response searchMinStopStations(RoutePlanInfo info, HttpHeaders headers);

}
