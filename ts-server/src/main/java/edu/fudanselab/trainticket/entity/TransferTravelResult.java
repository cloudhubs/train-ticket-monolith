package edu.fudanselab.trainticket.entity;

import edu.fudanselab.trainticket.entity.TripResponse;
import lombok.Data;
import edu.fudanselab.trainticket.entity.TripResponse;

import java.util.List;

/**
 * @author fdse
 */
@Data
public class TransferTravelResult {

    private List<TripResponse> firstSectionResult;

    private List<TripResponse> secondSectionResult;

    public TransferTravelResult() {
        //Default Constructor
    }

    public TransferTravelResult(List<TripResponse> firstSectionResult, List<TripResponse> secondSectionResult) {

        this.firstSectionResult = firstSectionResult;
        this.secondSectionResult = secondSectionResult;
    }

}
