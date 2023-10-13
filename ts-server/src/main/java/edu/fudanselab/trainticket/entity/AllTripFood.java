package edu.fudanselab.trainticket.entity;

import edu.fudanselab.trainticket.entity.Food;
import edu.fudanselab.trainticket.entity.StationFoodStore;
import edu.fudanselab.trainticket.entity.TrainFood;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AllTripFood {

    private List<Food> trainFoodList;

    private Map<String, List<StationFoodStore>>  foodStoreListMap;

    public AllTripFood(){
        //Default Constructor
    }

}
