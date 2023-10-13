package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.entity.RouteInfoCommon;
import edu.fudanselab.trainticket.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface AdminRouteService {

    /**
     * get all routes with headers
     *
     * @param headers headers
     * @return Response
     */
    Response getAllRoutes(HttpHeaders headers);

    /**
     * get all routes with headers
     *
     * @param request route information
     * @param headers headers
     * @return Response
     */
    Response createAndModifyRoute(RouteInfoCommon request, HttpHeaders headers);

    /**
     * get all routes with headers
     *
     * @param routeId route id
     * @param headers headers
     * @return Response
     */
    Response deleteRoute(String routeId, HttpHeaders headers);
}
