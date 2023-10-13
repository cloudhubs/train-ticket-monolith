package edu.fudanselab.trainticket.entity;

import edu.fudanselab.trainticket.entity.TripId;
import edu.fudanselab.trainticket.util.StringUtils;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fdse
 */
@Data
@Entity
@Table(name = "trip2")
@GenericGenerator(name = "trip-jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
public class
Trip {
    @Valid
    @Id
    @GeneratedValue(generator = "trip-jpa-uuid")
    @Column(length = 36)
    private String id;

    @Embedded
    private TripId tripId;

    @Valid
    @NotNull
    private String trainTypeName;

    private String routeId;


    @Valid
    @NotNull
    private String startStationName;

    @Valid
    private String stationsName;

    @Valid
    @NotNull
    private String terminalStationName;

    @Valid
    @NotNull
    private String startTime;

    @Valid
    @NotNull
    private String endTime;

    public Trip(TripId tripId, String trainTypeName, String startStationName, String stationsName, String terminalStationName, String startTime, String endTime) {
        this.tripId = tripId;
        this.trainTypeName = trainTypeName;
        this.startStationName = StringUtils.String2Lower(startStationName);
        this.stationsName = StringUtils.String2Lower(stationsName);
        this.terminalStationName = StringUtils.String2Lower(terminalStationName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Trip(TripId tripId, String trainTypeName, String routeId) {
        this.tripId = tripId;
        this.trainTypeName = trainTypeName;
        this.routeId = routeId;
        this.startStationName = "";
        this.terminalStationName = "";
        this.startTime = "";
        this.endTime = "";
    }

    public Trip(){
        //Default Constructor
        this.trainTypeName = "";
        this.startStationName = "";
        this.terminalStationName = "";
        this.startTime = "";
        this.endTime = "";
    }

}